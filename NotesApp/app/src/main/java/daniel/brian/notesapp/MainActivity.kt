package daniel.brian.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import daniel.brian.notesapp.screens.NotesScreen
import daniel.brian.notesapp.screens.NotesViewModel
import daniel.brian.notesapp.ui.theme.NotesAppTheme
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotesApp()
                }
            }
        }
    }
}

@Composable
fun NotesApp(notesViewModel: NotesViewModel = viewModel()){
    val notesList = notesViewModel.noteList.collectAsState().value
    NotesScreen(
        notes = notesList,
        onAddNote = {
            notesViewModel.addNote(it)
        },
        onRemoveNote = {
            notesViewModel.removeNote(it)
        })


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesAppTheme {

    }
}