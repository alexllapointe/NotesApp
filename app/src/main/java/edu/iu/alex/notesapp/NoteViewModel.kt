package edu.iu.alex.notesapp


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class NoteViewModel : ViewModel() {


    /**
     * Method gets all Notes in the database (for the purpose of continuous updates).
     *
     * @param context Allows for easier accessibility.
     */
    fun getAllNotes(context: Context): LiveData<List<Note>> {
        val noteDao = NoteDatabase.getInstance(context).noteDao
        return noteDao.getAllNotes()
    }


    /**
     * Method gets note item by its ID.
     *
     * @param context allows for easier accessibility
     * @param id ID of the note.
     */

    fun getNoteById(context: Context, id: Long): LiveData<Note> {
        val noteDao = NoteDatabase.getInstance(context).noteDao
        return noteDao.get(id)
    }

}




