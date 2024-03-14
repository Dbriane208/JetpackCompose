package daniel.brian.movieapp.navigation

enum class MovieScreens {
    HomeScreen,
    DetailsScreen;
    // companion object can be compared with static keyword in other programming languages eg java
    companion object {
        fun fromRoute(route: String?): MovieScreens
        = when(route?.substringBefore("/")){
            HomeScreen.name -> HomeScreen
            DetailsScreen.name -> DetailsScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}