package com.matiasd.brainlist3


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinner: Spinner = findViewById(R.id.icon_spinner)
        val items = listOf(R.drawable.casa_icon, R.drawable.fiesta_icon, R.drawable.trabajo_icon)
        val adapter = object : ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, items) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val imageView = ImageView(context)
                imageView.setImageResource(getItem(position) ?: 0)
                imageView.adjustViewBounds = true
                imageView.layoutParams = AbsListView.LayoutParams(200, 200)
                return imageView
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val imageView = ImageView(context)
                imageView.setImageResource(getItem(position) ?: 0)
                imageView.adjustViewBounds = true
                imageView.layoutParams = AbsListView.LayoutParams(200, 200)
                return imageView
            }
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.startToStart = R.id.tVIconName
        layoutParams.topToBottom = R.id.tVIconName
        spinner.layoutParams = layoutParams

        var indiceSeleccionado: Int = -1

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                indiceSeleccionado = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        if (indiceSeleccionado != -1) {
            val imagenSeleccionada = items[indiceSeleccionado]
        }

        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        btnAgregar.setOnClickListener{


            val intent = Intent(this, AddList::class.java)
            startActivity(intent)
        }
    }
}



