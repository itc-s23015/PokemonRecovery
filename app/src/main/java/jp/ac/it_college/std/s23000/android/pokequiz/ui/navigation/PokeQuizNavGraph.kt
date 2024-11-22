package jp.ac.it_college.std.s23000.android.pokequiz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.ac.it_college.std.s23000.android.pokequiz.ui.home.HomeScreen

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
            HomeScreen()
        }
    }
}