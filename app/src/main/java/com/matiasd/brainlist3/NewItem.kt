package com.matiasd.brainlist3

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Suppress("DEPRECATION")
class NewItem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombreLista = intent.getStringExtra("nombreLista").toString()

        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        btnAgregar.setOnClickListener{
            val itemTitle = findViewById<EditText>(R.id.eTItem).getText().toString()
            val description = findViewById<EditText>(R.id.eTDescription).getText().toString()
            if(itemTitle.isNotBlank()){
                if(description.isNotBlank()){
                    val itemData = ItemData(nombreLista, itemTitle, description)
                    val db = DBLists(this).writableDatabase
                    val values = ContentValues().apply {
                        put("list_name", itemData.nameList)
                        put("item_title", itemData.textTitle)
                        put("description", itemData.description)
                    }
                    db.insert("listsDB", null, values)
                    db.close()

                    val intent = Intent(this, Lists::class.java)
                    intent.putExtra("nombreLista", nombreLista)
                    startActivity(intent)
                }else{
                    val emptyDescription = ""
                    val itemData = ItemData(nombreLista, itemTitle, emptyDescription)
                    val db = DBLists(this).writableDatabase
                    val values = ContentValues().apply {
                        put("list_name", itemData.nameList)
                        put("item_title", itemData.textTitle)
                        put("description", itemData.description)
                    }
                    db.insert("listsDB", null, values)
                    db.close()

                    val intent = Intent(this, Lists::class.java)
                    intent.putExtra("nombreLista", nombreLista)
                    startActivity(intent)
                }
            }else{
                Toast.makeText(this, "Completar campo de Título", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val nombreLista = intent.getStringExtra("nombreLista").toString()
        val intent = Intent(this, Lists::class.java)
        intent.putExtra("nombreLista", nombreLista)
        startActivity(intent)
        finish() // Opcional, dependiendo de si deseas conservar o no la pila de actividades
    }
}
data class ItemData(val nameList: String, val textTitle: String, val description: String)