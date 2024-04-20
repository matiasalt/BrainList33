package com.matiasd.brainlist3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class ButtonsOnClickListener(private val context: Context) : View.OnClickListener {
    override fun onClick(v: View) {
        val b = v as Button
        Toast.makeText(context, b.text, Toast.LENGTH_SHORT).show()
    }
}
class MainMenu : AppCompatActivity() {
    private val numButtons = 4
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnAdd = findViewById<ImageButton>(R.id.btnAdd)
        val llBotonera = findViewById<LinearLayout>(R.id.lLBotonera)

        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        btnAdd.setOnClickListener {
            val intent = Intent(this, AddList::class.java)
            startActivity(intent)
            }
        }


}


