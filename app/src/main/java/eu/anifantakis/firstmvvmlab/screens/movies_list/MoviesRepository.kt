package eu.anifantakis.firstmvvmlab.screens.movies_list

import eu.anifantakis.firstmvvmlab.model.Movie
import kotlinx.coroutines.delay

class MoviesRepository {

    suspend fun getMovies(): List<Movie> {
        val baseImgUrl = "https://image.tmdb.org/t/p/w200"

        delay(4000L)

        return listOf(
            Movie(id = 1, title = "Movie 1", img = "$baseImgUrl/9Rj8l6gElLpRL7Kj17iZhrT5Zuw.jpg"),
            Movie(id = 2, title = "Movie 2", img = "$baseImgUrl/wigZBAmNrIhxp2FNGOROUAeHvdh.jpg"),
            Movie(id = 3, title = "Movie 3", img = "$baseImgUrl/bvYjhsbxOBwpm8xLE5BhdA3a8CZ.jpg"),
        )
    }

}