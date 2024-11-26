package jp.ac.it_college.std.nakasone.android.pokequiz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import jp.ac.it_college.std.nakasone.android.pokequiz.ui.result.ResultScreen
import jp.ac.it_college.std.nakasone.android.pokequiz.ui.generation.GenerationScreen
import jp.ac.it_college.std.nakasone.android.pokequiz.ui.home.HomeScreen
import jp.ac.it_college.std.nakasone.android.pokequiz.ui.navigation.PokeQuizDestinationArgs.CORRECT_ANSWER_COUNT_ARG
import jp.ac.it_college.std.nakasone.android.pokequiz.ui.navigation.PokeQuizDestinationArgs.GENERATION_ID_ARG
import jp.ac.it_college.std.nakasone.android.pokequiz.ui.quiz.QuizScreen

/**
 * NavHost を使用したナビゲーション
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
                toResult = { gen, count ->
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
                    navActions.navigateToQuiz(1)
                },
                onGenerationClick = {
                    navActions.navigateToGenerationSelect()
                }
            )
        }
    }
}