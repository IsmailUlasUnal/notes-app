package com.example.notes_tutorial_app

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.notes_tutorial_app.adapter.NotesAdapter.Companion.randomColor
import com.example.notes_tutorial_app.databinding.ActivityAddNoteBinding
import com.example.notes_tutorial_app.models.Note
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date

class AddNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var note: Note
    private lateinit var old_note: Note
    var isUpdate = false

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)

        setContentView(binding.root)

        try {

            old_note = intent.getSerializableExtra("current_note", Note::class.java) as Note
            binding.etTitle.setText(old_note.title)
            binding.etNote.setText(old_note.note)
            isUpdate = true
        } catch (e : Exception) {
            e.printStackTrace()
        }

        binding.imgCheck.setOnClickListener {

            val title = binding.etTitle.text.toString()
            val note_desc = binding.etNote.text.toString()

            if (title.isNotEmpty() || note_desc.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

                if (isUpdate) {
                    note = Note(old_note.id, title, note_desc, formatter.format(Date()), old_note.color)
                } else {
                    note = Note(null, title, note_desc, formatter.format(Date()), randomColor())
                }


                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()

            } else {

                Toast.makeText(this@AddNote, "Please enter some data", Toast.LENGTH_SHORT).show()

            }

        }
        binding.imgBackArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

}