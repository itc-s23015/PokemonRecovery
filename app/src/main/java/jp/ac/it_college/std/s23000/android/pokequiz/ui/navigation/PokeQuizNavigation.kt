package jp.ac.it_college.std.s23000.android.pokequiz.ui.navigation

import androidx.navigation.NavHostController
import jp.ac.it_college.std.s23000.android.pokequiz.ui.navigation.PokeQuizScreens.HOME_SCREEN

/**
 * 各画面を定義するオブジェクト
 */
private object PokeQuizScreens {
    const val HOME_SCREEN = "home"
}

/**
 * ナビゲーションパラメータを定義するオブジェクト
 */
object PokeQuizDestinationArgs {

}

/**
 * ナビゲーションの宛先パスを定義するオブジェクト
 */
object PokeQuizDestinations {
    const val HOME_ROUTE = HOME_SCREEN
}

/**
 * 実際にナビゲーションの処理をまとめて実装するクラス
 */
class PokeQuizNavigationActions(private val navController: NavHostController) {
    fun navigateToHome() {
        navController.navigate(PokeQuizDestinations.HOME_ROUTE)
    }
}