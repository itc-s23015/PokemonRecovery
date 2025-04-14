package jp.ac.it_college.std.s23015.android.pokequiz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import jp.ac.it_college.std.s23015.android.pokequiz.ui.generation.GenerationScreen
import jp.ac.it_college.std.s23015.android.pokequiz.ui.home.HomeScreen
import jp.ac.it_college.std.s23015.android.pokequiz.ui.navigation.PokeQuizDestinationArgs.CORRECT_ANSWER_COUNT_ARG
import jp.ac.it_college.std.s23015.android.pokequiz.ui.navigation.PokeQuizDestinationArgs.GENERATION_ID_ARG
import jp.ac.it_college.std.s23015.android.pokequiz.ui.quiz.QuizScreen
import jp.ac.it_college.std.s23015.android.pokequiz.ui.result.ResultScreen

/**
 * NavHost を使用したこのアプリ用のナビゲーションを定義するコンポーザブル関数
 *
 * @see PokeQuizDestinations
 * @see PokeQuizDestinationArgs
 * @see PokeQuizNavigationActions
 */
@Composable
fun PokeQuizNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    navActions: PokeQuizNavigationActions = remember(navController) {
        PokeQuizNavigationActions(navController)
    }
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = PokeQuizDestinations.HOME_ROUTE
    ) {
        // タイトル画面
        composable(
            route = PokeQuizDestinations.HOME_ROUTE
        ) {
            HomeScreen(
                onStartClick = {
                    // スタートボタンがクリックされたら世代選択画面へ移動
                    navActions.navigateToGenerationSelect()
                }
            )
        }

        // 世代選択画面
        composable(
            route = PokeQuizDestinations.GENERATION_ROUTE
        ) {
            GenerationScreen(
                onGenerationSelected = {
                    // 世代選択ボタンがクリックされたらクイズ画面へ移動
                    navActions.navigateToQuiz(it)
                }
            )
        }

        // クイズ画面
        composable(
            route = PokeQuizDestinations.QUIZ_ROUTE,
            arguments = listOf(
                navArgument(GENERATION_ID_ARG) {
                    type = NavType.IntType
                }
            )
        ) {
            QuizScreen(
                onQuizEnded = { gen, count ->
                    // クイズが終了したらリザルト画面へ移動
                    navActions.navigateToResult(
                        generationId = gen,
                        count = count
                    )
                }
            )
        }

        // リザルト画面
        composable(
            route = PokeQuizDestinations.RESULT_ROUTE,
            arguments = listOf(
                navArgument(GENERATION_ID_ARG) {
                    type = NavType.IntType
                },
                navArgument(CORRECT_ANSWER_COUNT_ARG) {
                    type = NavType.IntType
                }
            )
        ) {
            ResultScreen(
                onRetryClick = {
                    // リトライボタンがクリックされたら同じ世代でクイズ画面へ移動
                    navActions.navigateToQuiz(it)
                },
                onGenerationClick = {
                    // 世代選択ボタンがクリックされたら世代選択画面へ移動
                    navActions.navigateToGenerationSelect()
                }
            )
        }
    }
}