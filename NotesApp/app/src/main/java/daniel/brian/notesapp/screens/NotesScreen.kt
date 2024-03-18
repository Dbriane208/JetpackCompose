package daniel.brian.notesapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import daniel.brian.notesapp.R
import daniel.brian.notesapp.components.NewButton
import daniel.brian.notesapp.components.NoteInputText
import daniel.brian.notesapp.components.NoteRow
import daniel.brian.notesapp.model.Note
import daniel.brian.notesapp.data.NoteDataSource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit,
){
    // we create two states for title and description
    var title by remember {
        mutableStateOf("")
    }
    
    var description by remember {
        mutableStateOf("")
    }

    // creating a context
    val context = LocalContext.current

    Column(modifier = Modifier.padding(6.dp)) {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.app_name))
        },
            actions = {
                Icon(imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications")
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFDADFE3)
            ))

        // content
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            NoteInputText(
                modifier = Modifier
                    .padding(
                        top = 9.dp,
                        bottom = 9.dp),
                text = title,
                label = "Title",
                onTextChange = {
                    if (it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        }) title = it
                })
            NoteInputText(
                modifier = Modifier
                    .padding(
                        top = 9.dp,
                        bottom = 9.dp),
                text = description,
                label = "Add a note",
                onTextChange = {
                    if (it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        }) description = it
                } )
            NewButton(
                text = "Save",
                onClick = {
                    // Adding the functionality to save
                    if (title.isNotEmpty() && description.isNotEmpty()){
                        // we want to add notes to the screen
                        onAddNote(Note(title = title, description = description))
                        // we clear the title and description when we save
                        title = ""
                        description = ""
                        // make a toast after we add a note on the screen
                        Toast.makeText(context,"Note Added",Toast.LENGTH_SHORT).show()
                    }
                })

            // creating a separation line
            Divider(modifier = Modifier.padding(10.dp))
            LazyColumn {
                items(notes){ note ->
                    NoteRow(note = note, onNoteClicked = {
                        // we want to remove the note when it's clicked
                        onRemoveNote(note)
                    })
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotesScreenPreview(){
    NotesScreen(notes = NoteDataSource().loadNotes(), onAddNote = {}, onRemoveNote = {})
}