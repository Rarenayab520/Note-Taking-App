package com.nayab.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nayab.myapplication.R
import com.nayab.myapplication.data.Note
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.content.ContextCompat
import com.nayab.myapplication.data.NoteDatabase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var emptyText: TextView
    private lateinit var adapter: CustomNoteAdapter
    private val notesList = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)


        listView = findViewById(R.id.listView)
        emptyText = findViewById(R.id.emptyText)
        val addNoteBtn: Button = findViewById(R.id.goToAddNote)

        val db = NoteDatabase.getDatabase(this)
        val noteDao = db.noteDao()

        // Temporary empty list, will be updated with livedata
        adapter = CustomNoteAdapter(this, listOf())
        listView.adapter = adapter

        noteDao.getAllNotes().observe(this) { notes ->
            notesList.clear()
            notesList.addAll(notes)
            val noteStrings = notes.map { it.text }
            listView.adapter = CustomNoteAdapter(this, noteStrings)
            emptyText.visibility = if (notes.isEmpty()) TextView.VISIBLE else TextView.GONE
        }


        listView.setOnItemClickListener { _, _, position, _ ->
            AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes") { _, _ ->
                    lifecycleScope.launch {
                        noteDao.delete(notesList[position])
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        addNoteBtn.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }
    }
}
