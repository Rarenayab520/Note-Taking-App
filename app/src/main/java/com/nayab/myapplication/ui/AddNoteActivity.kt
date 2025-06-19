package com.nayab.myapplication.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nayab.myapplication.R
import com.nayab.myapplication.data.Note
import com.nayab.myapplication.data.NoteDatabase
import kotlinx.coroutines.launch

class AddNoteActivity : AppCompatActivity() {
    private lateinit var input: EditText
    private lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        input = findViewById(R.id.noteInput)
        saveBtn = findViewById(R.id.saveNoteButton)

        val noteDao = NoteDatabase.getDatabase(this).noteDao()

        saveBtn.setOnClickListener {
            val noteText = input.text.toString()
            if (noteText.isNotEmpty()) {
                lifecycleScope.launch {
                    noteDao.insert(Note(text = noteText))
                    finish()
                }
            } else {
                Toast.makeText(this, "Note cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
