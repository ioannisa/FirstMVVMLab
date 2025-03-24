package eu.anifantakis.firstmvvmlab.screens.movies_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.anifantakis.firstmvvmlab.model.Movie
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MoviesListState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false
)

sealed interface MoviesListIntent {
    data object LoadMovies: MoviesListIntent
    data class SelectMovie(val movie: Movie): MoviesListIntent
}

sealed interface MoviesListEffect {
    data class GotoMovieDetailsScreen(val movie: Movie): MoviesListEffect
}



class MoviesListViewModel(
    private val repository: MoviesRepository
): ViewModel() {

    private val _state = MutableStateFlow(MoviesListState())
    val state = _state.asStateFlow()

    private val _effectChannel = Channel<MoviesListEffect>()
    val effectChannel = _effectChannel.receiveAsFlow()

    init {
        loadMovies()
    }

    fun processIntent(intent: MoviesListIntent) {
        when (intent) {
            is MoviesListIntent.LoadMovies -> loadMovies()
            is MoviesListIntent.SelectMovie -> selectMovie(intent.movie)
        }
    }



    private fun selectMovie(movie: Movie) {
        println("From VIEWMODEL ${movie.title}")
        _effectChannel.trySend(MoviesListEffect.GotoMovieDetailsScreen(movie))
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val movies = repository.getMovies()
            _state.update {
                it.copy(
                    movies = movies,
                    isLoading = false
                )
            }
        }
    }
}