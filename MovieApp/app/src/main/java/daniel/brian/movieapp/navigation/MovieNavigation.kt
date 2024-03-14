package daniel.brian.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import daniel.brian.movieapp.screens.details.DetailsScreen
import daniel.brian.movieapp.screens.home.HomeScreen

@Composable
fun MovieNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MovieScreens.HomeScreen.name){
        composable(MovieScreens.HomeScreen.name){
            // we pass where this would lead us
            HomeScreen(navController = navController)
        }
        
        composable(MovieScreens.DetailsScreen.name + "/{movie}", arguments = listOf(navArgument(name = "movie"){
            type = NavType.StringType
        })){ backStackEntry ->
            DetailsScreen(navController = navController,backStackEntry.arguments?.getString("movie"))
        }
    }
}

