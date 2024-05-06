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
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class NewItem : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        val currentUserId = auth.currentUser
        val userId = currentUserId?.uid.toString()
        val nombreLista = intent.getStringExtra("nombreLista").toString()

        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        btnAgregar.setOnClickListener{
            val itemTitle = findViewById<EditText>(R.id.eTItem).getText().toString()
            val description = findViewById<EditText>(R.id.eTDescription).getText().toString()
            if(itemTitle.isNotBlank()){
                if(description.isNotBlank()){
                    val itemData = ItemData(userId, nombreLista, itemTitle, description)
                    val db = DBLists(this).writableDatabase
                    val values = ContentValues().apply {
                        put("user_id", itemData.userId)
                        put("name_list", itemData.nameList)
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
                    val itemData = ItemData(userId, nombreLista, itemTitle, emptyDescription)
                    val db = DBLists(this).writableDatabase
                    val values = ContentValues().apply {
                        put("user_id", itemData.userId)
                        put("name_list", itemData.nameList)
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
        finish()
    }
}
data class ItemData(val userId: String, val nameList: String, val textTitle: String, val description: String)