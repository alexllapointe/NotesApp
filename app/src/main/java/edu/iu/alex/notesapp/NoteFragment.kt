package edu.iu.alex.notesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import java.util.concurrent.Executors

class NoteFragment : Fragment() {

    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.note_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteId = arguments?.getLong("noteId")
        val noteDao = NoteDatabase.getInstance(requireContext()).noteDao

        val noteTitle : EditText = view.findViewById(R.id.editTextTitle)
        val noteDescription : EditText = view.findViewById(R.id.editTextDescription)


        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        if (noteId != null) {
            viewModel.getNoteById(requireContext(), noteId).observe(viewLifecycleOwner) { note ->
                if (note != null) {
                    noteTitle.setText(note.title)
                    noteDescription.setText(note.description)
                }
            }
        } else {
            noteTitle.text.clear()
            noteDescription.text.clear()
        }


        val saveButton: Button = view.findViewById(R.id.save_button)

        val navController = findNavController()
        saveButton.setOnClickListener {

            val titleText = noteTitle.text.toString().trim()
            val descriptionText = noteDescription.text.toString().trim()


            if (titleText.isNotEmpty()) {
                val newNote = Note(title = titleText, description = descriptionText)

                saveNoteToDb(newNote)
                navController.navigate(R.id.noteFragment_to_mainFragment)
            } else {
                Toast.makeText(requireContext(), "Title cannot be empty!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun saveNoteToDb(note: Note) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val noteDao = NoteDatabase.getInstance(requireContext()).noteDao
            noteDao.insert(note)
        }
    }

}