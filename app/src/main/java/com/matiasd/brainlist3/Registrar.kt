package com.matiasd.brainlist3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class Registrar : AppCompatActivity() {
    private lateinit var userDBHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userDBHelper = DBHelper(this)

        val button = findViewById<Button>(R.id.btnRegister)
        button.setOnClickListener {
            val name = findViewById<EditText>(R.id.eTuserRegitro1).getText().toString()
            val password = findViewById<EditText>(R.id.eTpassRegistro1).getText().toString()
            val email = findViewById<EditText>(R.id.eTmailRegitro3).getText().toString()

            if(name.isNotBlank() or password.isNotBlank() or email.isNotBlank()){
                userDBHelper.addNewUser(name, password, email)
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Completar todos los campos", Toast.LENGTH_LONG).show()
            }
        }
    }
}
