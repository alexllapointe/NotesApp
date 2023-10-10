package edu.iu.alex.notesapp


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class NoteViewModel : ViewModel() {


    fun getAllNotes(context: Context): LiveData<List<Note>> {
        val noteDao = NoteDatabase.getInstance(context).noteDao
        return noteDao.getAllNotes()
    }

    fun getNoteById(context: Context, id: Long): LiveData<Note> {
        val noteDao = NoteDatabase.getInstance(context).noteDao
        return noteDao.get(id)
    }

}




