package jp.ac.it_college.std.s23015.android.pokequiz.ui.navigation

import androidx.navigation.NavHostController
import jp.ac.it_college.std.s23015.android.pokequiz.ui.navigation.PokeQuizDestinationArgs.CORRECT_ANSWER_COUNT_ARG
import jp.ac.it_college.std.s23015.android.pokequiz.ui.navigation.PokeQuizDestinationArgs.GENERATION_ID_ARG
import jp.ac.it_college.std.s23015.android.pokequiz.ui.navigation.PokeQuizScreens.GENERATION_SCREEN
import jp.ac.it_college.std.s23015.android.pokequiz.ui.navigation.PokeQuizScreens.HOME_SCREEN
import jp.ac.it_college.std.s23015.android.pokequiz.ui.navigation.PokeQuizScreens.QUIZ_SCREEN
import jp.ac.it_college.std.s23015.android.pokequiz.ui.navigation.PokeQuizScreens.RESULT_SCREEN

/**
 * 各画面を定義するオブジェクト
 */
private object PokeQuizScreens {
    const val HOME_SCREEN = "home"
    const val GENERATION_SCREEN = "generation"
    const val QUIZ_SCREEN = "quiz"
    const val RESULT_SCREEN = "result"
}

/**
 * ナビゲーションパラメータを定義するオブジェクト
 */
object PokeQuizDestinationArgs {
    const val GENERATION_ID_ARG = "generationId"
    const val CORRECT_ANSWER_COUNT_ARG = "correctCount"
}

/**
 * ナビゲーションの宛先パスを定義するオブジェクト
 */
object PokeQuizDestinations {
    const val HOME_ROUTE = HOME_SCREEN
    const val GENERATION_ROUTE = GENERATION_SCREEN
    const val QUIZ_ROUTE = "$QUIZ_SCREEN/{$GENERATION_ID_ARG}"
    const val RESULT_ROUTE = "$RESULT_SCREEN/{$GENERATION_ID_ARG}/{$CORRECT_ANSWER_COUNT_ARG}"
}

/**
 * 実際にナビゲーションの処理をまとめて実装するクラス
 */
class PokeQuizNavigationActions(private val navController: NavHostController) {
    fun navigateToHome() {
        navController.navigate(PokeQuizDestinations.HOME_ROUTE)
    }

    fun navigateToGenerationSelect() {
        navController.navigate(PokeQuizDestinations.GENERATION_ROUTE)
    }

    fun navigateToQuiz(generationId: Int) {
        navController.navigate("$QUIZ_SCREEN/$generationId")
    }

    fun navigateToResult(generationId: Int, count: Int) {
        navController.navigate("$RESULT_SCREEN/$generationId/$count")
    }
}