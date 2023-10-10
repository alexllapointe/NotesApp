package edu.iu.alex.notesapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note): Long
    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): LiveData<List<Note>>
    @Update
    suspend fun update(note: Note)
    @Delete
    suspend fun delete(note: Note)
    @Query("SELECT * FROM notes_table WHERE id = :key")
    fun get(key: Long): LiveData<Note>
    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun getAll(): LiveData<List<Note>>
}