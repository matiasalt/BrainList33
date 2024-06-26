package com.matiasd.brainlist3


import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth


@Suppress("DEPRECATION")
class AddList : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var idSeleccionado = 1
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        val currentUserId = auth.currentUser
        val userId = currentUserId?.uid.toString()

        val spinner: Spinner = findViewById(R.id.icon_spinner)
        val items = listOf(R.drawable.casa_icon, R.drawable.fiesta_icon, R.drawable.trabajo_icon)
        val adapter = object : ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, items) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val imageView = ImageView(context)
                imageView.setImageResource(getItem(position) ?: 0)
                imageView.adjustViewBounds = true
                imageView.layoutParams = AbsListView.LayoutParams(250, 250)
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

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val idImagen = when (position) {
                    0 -> R.drawable.casa_icon
                    1 -> R.drawable.fiesta_icon
                    2 -> R.drawable.trabajo_icon

                    else -> R.drawable.casa_icon
                }
                idSeleccionado = idImagen
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val colors = listOf("#FF99C8", "#FAF3DD", "#B8F2E6", "#AED9E0", "#5E6472", "#cbc8b5", "#bda0a2", "#ffe6db", "#d1eaee", "#efb0a9")
        val colorSelected = colors.random()
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        btnAgregar.setOnClickListener{
            val nombreLista = findViewById<EditText>(R.id.eTNameList).getText().toString()

            if(nombreLista.isNotBlank()){
                val buttonItem = ButtonItem(userId, nombreLista, idSeleccionado, colorSelected)
                val db = DBListButton(this).writableDatabase
                val values = ContentValues().apply {
                    put("user_id", buttonItem.userId)
                    put("list_name", buttonItem.text)
                    put("image_id", buttonItem.imageResId)
                    put("list_color", buttonItem.color)
                }
                db.insert("btnList", null, values)
                db.close()

                val intent = Intent(this, MainMenu::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Completar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainMenu::class.java)
        startActivity(intent)
        finish() // Opcional, dependiendo de si deseas conservar o no la pila de actividades
    }
}
data class ButtonItem(val userId: String, val text: String, val imageResId: Int, val color: String)


