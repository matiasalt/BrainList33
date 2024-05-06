package com.matiasd.brainlist3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View

@Suppress("NAME_SHADOWING", "DEPRECATION")
class Lists : AppCompatActivity() {
    private lateinit var listdb: DBLists
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lists)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Recuperar el valor de item.first del Intent
        val nombreLista = intent.getStringExtra("nombreLista").toString()

        // Usar el valor de itemFirst como sea necesario
        // Por ejemplo, mostrarlo en un TextView
        val textView = findViewById<TextView>(R.id.tVTitle)
        textView.text = nombreLista

        listdb = DBLists(this)
        val dataList = listdb.getDataFromSQLite(nombreLista) // Función que obtiene los datos de la base de datos

        val scrollView = findViewById<ScrollView>(R.id.sVItem) // Usa el ScrollView existente desde el layout XML
        val linearLayout = scrollView.findViewById<LinearLayout>(R.id.lLItems) // Obtén el LinearLayout dentro del ScrollView

        for (item in dataList) {
            val horizontalLayout = LinearLayout(this)
            horizontalLayout.orientation = LinearLayout.HORIZONTAL

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            val checkBox = CheckBox(this)
            checkBox.gravity = Gravity.CENTER_VERTICAL

            checkBox.layoutParams = params // Establecer el ancho de CheckBox como WRAP_CONTENT

            val textView = TextView(this)
            textView.text = item.first
            textView.textSize = 30f
            textView.gravity = Gravity.CENTER_VERTICAL

            // Configurar los parámetros de diseño para que el TextView ocupe el resto del espacio disponible
            val weightParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            textView.layoutParams = weightParams

            // Agregar CheckBox y TextView al diseño horizontal
            horizontalLayout.addView(checkBox)
            horizontalLayout.addView(textView)

            // Agregar el diseño horizontal al diseño lineal principal
            linearLayout.addView(horizontalLayout)
        }

        val btnAdd = findViewById<ImageButton>(R.id.btnAddItem)

        btnAdd.setOnClickListener {
            val intent = Intent(this, NewItem::class.java)
            intent.putExtra("nombreLista", nombreLista)
            startActivity(intent)
        }
        //background
        findViewById<View>(android.R.id.content).setBackgroundResource(R.drawable.listas)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainMenu::class.java)
        startActivity(intent)
        finish() // Opcional, dependiendo de si deseas conservar o no la pila de actividades
    }
}