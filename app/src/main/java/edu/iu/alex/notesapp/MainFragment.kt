package edu.iu.alex.notesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class MainFragment : Fragment() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)

        noteAdapter = NoteAdapter(mutableListOf())
        recyclerView.adapter = noteAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getAllNotes(requireContext())?.observe(viewLifecycleOwner) { notes ->
            notes?.let {
                noteAdapter.setNotes(it)
            }
        }

        noteAdapter.onDeleteListener = object : NoteAdapter.OnNoteDeletedListener {
            override fun onNoteDeleted(note: Note) {
                viewLifecycleOwner.lifecycleScope.launch {
                    val noteDao = NoteDatabase.getInstance(requireContext()).noteDao
                    noteDao.delete(note)
                }
            }
        }

        noteAdapter.onNoteClickListener = object : NoteAdapter.OnNoteClickListener {
            override fun onNoteClick(note: Note) {
                val bundle = Bundle()
                bundle.putLong("noteId", note.id)
                findNavController().navigate(R.id.mainFragment_to_noteFragment, bundle)
            }
        }

        val addButton: Button = view.findViewById(R.id.add_note_button)
        val navController = findNavController()
        addButton.setOnClickListener {
            navController.navigate(R.id.mainFragment_to_noteFragment)
        }
    }
}
