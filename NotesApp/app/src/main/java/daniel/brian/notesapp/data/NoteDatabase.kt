package daniel.brian.notesapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import daniel.brian.notesapp.model.Note
import daniel.brian.notesapp.utils.UUIDConverter

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(UUIDConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDatabaseDao
}