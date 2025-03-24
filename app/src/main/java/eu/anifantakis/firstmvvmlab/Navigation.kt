package eu.anifantakis.firstmvvmlab

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import eu.anifantakis.firstmvvmlab.screens.movies_list.MoviesListScreenRoot
import kotlinx.serialization.Serializable

sealed interface MoviesNavRoute {
    @Serializable data object MoviesList: MoviesNavRoute
    @Serializable data class SelectedMovie(val title: String, val img: String): MoviesNavRoute
}

@Composable
fun NavigationRoot(innerPadding: PaddingValues) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MoviesNavRoute.MoviesList,

    ) {
        composable<MoviesNavRoute.MoviesList> {
            MoviesListScreenRoot(
                modifier = Modifier.padding(innerPadding),
                onGotoSelectedMovie = { movie ->
                    navController.navigate(MoviesNavRoute.SelectedMovie(movie.title, movie.img))
                }
            )
        }

        composable<MoviesNavRoute.SelectedMovie> {
            val args = it.toRoute<MoviesNavRoute.SelectedMovie>()
            val title = args.title
            val image = args.img


            Text(
                "I Lookin at $title"
            )
        }

    }
}
