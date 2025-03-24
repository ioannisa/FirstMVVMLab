package eu.anifantakis.firstmvvmlab.screens.movies_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.compose.rememberAsyncImagePainter

@Composable
fun MoviesListScreen(
    modifier: Modifier = Modifier,
    viewModel: MoviesListViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                MoviesListViewModel(
                    repository = MoviesRepository()
                )
            }
        }
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effectChannel.collect { effect ->
            when (effect) {
                is MoviesListEffect.GotoMovieDetailsScreen -> {
                    println("From SCREEN ${effect.movie.title}")
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }
        else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                ,
                contentAlignment = Alignment.TopStart
            ) {

                Column {

                    Button(
                        onClick = {
                            viewModel.processIntent(MoviesListIntent.LoadMovies)
                        }
                    ) {
                        Text(
                            text = "Refresh",
                            fontSize = 20.sp
                        )
                    }

                    LazyColumn {
                        items(
                            items = state.movies,
                            key = { movie -> movie.id }
                        ) { movie ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.processIntent(MoviesListIntent.SelectMovie(movie))
                                    }
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(movie.img),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(120.dp)
                                )

                                Text("Movie Title: ${movie.title}")
                            }

                        }
                    }
                }
            }


        }
    }

}