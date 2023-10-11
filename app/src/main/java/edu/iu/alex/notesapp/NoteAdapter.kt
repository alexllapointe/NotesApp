package edu.iu.alex.notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(val notes: MutableList<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.note_title)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_note_button)
    }

    /**
     * Interface for keeping track of when the image button of the note is pressed.
     */
    interface OnNoteDeletedListener {
        fun onNoteDeleted(note: Note)
    }

    /**
     * Interface for keeping track of when the textview of the note is clicked.
     */

    interface OnNoteClickListener {
        fun onNoteClick(note: Note)
    }

    var onDeleteListener: OnNoteDeletedListener? = null
    var onNoteClickListener: OnNoteClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }


    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.title.text = note.title

        holder.deleteButton.setOnClickListener {
            // handle deletion logic here
            onDeleteListener?.onNoteDeleted(note)
            notes.removeAt(position)
            notifyDataSetChanged()
        }

        holder.title.setOnClickListener {
            val note = notes[position]
            onNoteClickListener?.onNoteClick(note)
        }
    }

    fun setNotes(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

}
