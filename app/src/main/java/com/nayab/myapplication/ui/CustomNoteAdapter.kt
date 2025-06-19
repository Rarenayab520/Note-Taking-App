package com.nayab.myapplication.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.nayab.myapplication.R

class CustomNoteAdapter(
    context: Context,
    private val notes: List<String>
) : ArrayAdapter<String>(context, 0, notes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.custom_list_item, parent, false)

        val noteText = view.findViewById<TextView>(R.id.noteText)
        val icon = view.findViewById<ImageView>(R.id.icon)

        noteText.text = notes[position]
        icon.setImageResource(R.drawable.note_icon) // Use your icon

        return view
    }
}
