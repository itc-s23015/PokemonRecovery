package jp.ac.it_college.std.s23015.android.pokequiz.ui.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ac.it_college.std.s23015.android.pokequiz.data.entity.PokemonEntity
import jp.ac.it_college.std.s23015.android.pokequiz.data.repository.GenerationsRepository
import jp.ac.it_college.std.s23015.android.pokequiz.data.repository.PokemonIntroducedGenerationRepository
import jp.ac.it_college.std.s23015.android.pokequiz.network.PokeApiService
import jp.ac.it_college.std.s23015.android.pokequiz.ui.navigation.PokeQuizDestinationArgs.GENERATION_ID_ARG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * クイズの進捗状況を表す列挙型
 */
enum class QuizStatus {
    /**
     * 出題されて未回答の状態
     */
    PROGRESS,

    /**
     * 回答して正解だった状態
     */
    CORRECT,

    /**
     * 回答して不正解だった状態
     */
    WRONG
}

/**
 * UI 側で直接使用するデータ
 */
data class QuizUiState(
    /**
     * データ読み込み中フラグ
     */
    val isLoading: Boolean = true,
    /**
     * 世代名として表示する文字列
     */
    val generationLabel: String = "",
    /**
     * クイズの進捗状況を表すフラグ
     */
    val status: QuizStatus = QuizStatus.PROGRESS,
    /**
     * 今の出題番号
     */
    val quizNumber: Int = 0,
    /**
     * 出題のポケモンイラストURL
     */
    val imageUrl: String = "",
    /**
     * 出題のポケモン名
     */
    val targetName: String = "",
    /**
     * 選択肢のリスト
     */
    val choices: List<String> = emptyList(),

    //ポケモンのタイプ
    val quizType: String = "",
)

/**
 * クイズ画面で使用するビューモデル
 *
 * @see GenerationsRepository
 * @see PokemonIntroducedGenerationRepository
 * @see PokeApiService
 */
@HiltViewModel
class QuizViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val generationsRepo: GenerationsRepository,
    private val pokeIntroRepo: PokemonIntroducedGenerationRepository,
    private val service: PokeApiService
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    /**
     * ナビゲーションパラメータに含まれる世代ID
     */
    private val generationId: Int = checkNotNull(
        savedStateHandle[GENERATION_ID_ARG]
    )

    /**
     * 選択された世代の全ポケモンリスト
     */
    private lateinit var pokeList: List<PokemonEntity>

    /**
     * [pokeList] のうち、クイズとして出題されるポケモンのリスト
     */
    private lateinit var quizList: List<PokemonEntity>

    /**
     * 正解数
     */
    private var correctCount = 0

    init {
        viewModelScope.launch {
            val genLabel = if (generationId == 0) {
                // 全世代対象限定の初期化
                allGenerationInitialize()
                "全世代"
            } else {
                // 単一世代対象限定の初期化
                singleGenerationInitialize()
                generationsRepo.getGenerationStream(generationId)
                    .first()?.name ?: throw IllegalStateException("なにかおかしくなった")
            }
            // pokeList からシャッフルしたリストを作って、10問分のポケモンリストデータを生成
            quizList = pokeList.shuffled().subList(0, 10)
            // 0スタートなので開始するために呼び出す
            nextQuiz()
            // UIステート更新
            _uiState.update {
                it.copy(
                    isLoading = false,
                    generationLabel = genLabel
                )
            }
        }
    }

    /**
     * 単一世代のときの初期化処理
     */
    private suspend fun singleGenerationInitialize() {
        // 件数が 0件 なら未キャッシュとみなす
        val isUncached: Boolean = pokeIntroRepo.getEntryCount(generationId) == 0
        // 未キャッシュなら PokeAPI からデータを取得して保存する
        if (isUncached) {
            retrieveGenerationPokemonSpeciesIds(generationId).collect {
                pokeIntroRepo.upsertEntry(generationId, it)
            }
        }
        // 問題の対象となる単一世代のポケモンのリストを取ってくる
        pokeList = pokeIntroRepo.getGenerationWithPokemon(generationId).first().pokemon
    }

    /**
     * 力技で全世代のときの初期化処理
     */
    private suspend fun allGenerationInitialize() {
        // まずは全世代情報を取ってくる
        val gens = generationsRepo.getAllGenerationsStream().first()
        // 件数 0件 の世代があれば未キャッシュとみなす
        val isUncached: Boolean = gens.map { pokeIntroRepo.getEntryCount(it.id) }.contains(0)
        // 未キャッシュなら PokeAPI からデータを取得して保存する
        if (isUncached) {
            withContext(Dispatchers.IO) {
                gens.map { gen ->
                    // 各世代ごとの処理を並列処理する
                    async {
                        retrieveGenerationPokemonSpeciesIds(gen.id).collect {
                            pokeIntroRepo.upsertEntry(gen.id, it)
                        }
                    }
                }.awaitAll() // 処理中でも流れて行くのを阻止する?
            }
        }
        // 問題の対象となる全世代のポケモンのリストを取ってくる
        pokeList = pokeIntroRepo.getAllGenerationWithPokemon()
            .first().map { it.pokemon }.flatten()
    }

    /**
     * 次の問題へ移動するメソッド
     */
    private fun nextQuiz(): Boolean {
        // カウントが10以上になったら処理しないで、終わったことを表すために false を返す
        if (uiState.value.quizNumber >= 10) return false

        // 次のお題となるポケモンを1体取り出す
        val target = quizList[uiState.value.quizNumber]
        // 画像の URL は PokemonEntity.officialArtwork
        val url = target.officialArtwork
        // 選択肢を作る
        // まずはターゲット以外の全ポケモンリストをシャッフルして先頭3件をとってきて、末尾にターゲットを追加。
        // 完成した4つのポケモンの名前が入ったリストを最後にシャッフルしたものを選択肢とする。
        val choices = (pokeList.filter { it.id != target.id }.shuffled().subList(0, 3)
            .map { it.name } + target.name).shuffled()
        // UIステートを更新
        viewModelScope.launch {
            val typeText = try {
                val typeResult = service.getPokemonById(target.id)
                typeResult.types.joinToString(" / ") { it.type.name }
            } catch (e: Exception) {
                println(e)
                "タイプ情報が取得できませんでした"
            }

            _uiState.update {
                it.copy(
                    status = QuizStatus.PROGRESS,
                    targetName = target.name,
                    imageUrl = url,
                    choices = choices,
                    quizNumber = uiState.value.quizNumber + 1,
                    quizType = typeText
                )
            }
        }
        // 正常に次の問題に行けたので true を返す
        return true
    }

    /**
     * 選択したポケモン名が正解かどうかを判定
     *
     * @param[selectName] 選択されたポケモンの名前
     * @param[end] 回答後に次の問題がなかったときのコールバック関数
     */
    fun checkAnswer(selectName: String, end: (generationId: Int, correctCount: Int) -> Unit) {
        // 選択された名前とターゲットの名前が一致していたら正解
        val isCollect = selectName == uiState.value.targetName
        // 正解なら正解数をカウントアップ
        correctCount += if (isCollect) 1 else 0
        // UIステートを更新
        _uiState.update {
            it.copy(
                status = if (isCollect) QuizStatus.CORRECT else QuizStatus.WRONG,
            )
        }
        // ディレイをかけて自動的に次の問題へ移行したいのでコルーチンを使用する
        viewModelScope.launch {
            // 2秒待機
            delay(2000)
            // 次の問題へ。なければコールバック関数を実行
            if (!nextQuiz()) end(generationId, correctCount)
        }
    }

    /**
     * ポケモンの名前から PokeAPI の /pokemon-species に種族情報を取ってくる
     * 処理の効率化のため flow を使用。
     */
    private fun retrieveGenerationPokemonSpeciesIds(generationId: Int) = flow {
        val gen = service.getGenerationById(generationId)
        gen.pokemonSpecies.forEach {
            emit(service.getPokemonSpeciesByName(it.name).id)
        }
    }
}