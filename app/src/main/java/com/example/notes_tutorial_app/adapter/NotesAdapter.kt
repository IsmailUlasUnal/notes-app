package com.example.notes_tutorial_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes_tutorial_app.R
import com.example.notes_tutorial_app.models.Note
import java.util.LinkedList
import java.util.Queue
import kotlin.random.Random

class NotesAdapter (private val context : Context, val listener: NotesClickListener) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val notesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notesList[position]
        holder.title.text = currentNote.title
        holder.title.isSelected = true

        holder.note_tv.text = currentNote.note

        holder.date.text = currentNote.date
        holder.date.isSelected = true

        currentNote.color = currentNote.color ?: randomColor()
        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(currentNote.color!!, null))

        holder.notes_layout.setOnClickListener() {
            listener.onItemClicked(notesList[holder.absoluteAdapterPosition])
        }

        holder.notes_layout.setOnLongClickListener() {
            listener.onLongItemClicked(notesList[holder.absoluteAdapterPosition], holder.notes_layout)
            true
        }
    }

    fun updateList(newList: List<Note>) {
        fullList.clear()
        fullList.addAll(newList)

        notesList.clear()
        notesList.addAll(fullList)
        notifyDataSetChanged()

    }

    fun filterList(search: String) {
        notesList.clear()

        for (item in fullList) {
            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                    item.note?.lowercase()?.contains(search.lowercase()) == true) {

                notesList.add(item)

            }
        }
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notes_layout = itemView.findViewById<CardView>(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val note_tv = itemView.findViewById<TextView>(R.id.tv_note)
        val date = itemView.findViewById<TextView>(R.id.tv_date)
    }


    interface NotesClickListener {
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }

    companion object {

        private val usedQueue: Queue<Int> = LinkedList()

        private val usableList = mutableListOf(R.color.note_color_1,
            R.color.note_color_2,
            R.color.note_color_3,
            R.color.note_color_4,
            R.color.note_color_5,
            R.color.note_color_6,
            R.color.note_color_7,
            R.color.note_color_8,
            R.color.note_color_9
        )

         fun randomColor() : Int{
             val seed = System.currentTimeMillis().toInt()
             val randomIndex = Random(seed).nextInt(usableList.size)

             if (usableList.size < 4) {
                 usableList.add(usedQueue.poll()!!)
             }
             usedQueue.add(usableList[randomIndex])
             return usableList.removeAt(randomIndex)
         }
    }
}