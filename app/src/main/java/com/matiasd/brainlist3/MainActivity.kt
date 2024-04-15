package com.matiasd.brainlist3

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.EditText
import android.widget.Toast

class   MainActivity : AppCompatActivity() {
    private lateinit var userDBHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userDBHelper = DBHelper(this)

        val buttonlog = findViewById<Button>(R.id.btnLogin)
        buttonlog.setOnClickListener {
            val name = findViewById<EditText>(R.id.eTuser).getText().toString()
            val password = findViewById<EditText>(R.id.eTpass).getText().toString()

            if(name=="admin"){
                if(password=="admin"){
                    userDBHelper.dropTable()
                    Toast.makeText(this, "Base de datos borrada", Toast.LENGTH_SHORT).show()
                }
            }else{
                val userExists = userDBHelper.readUser(name, password)
                if (userExists) {
                    val intent = Intent(this, MainMenu::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val buttonreg = findViewById<Button>(R.id.btnRegister)
        buttonreg.setOnClickListener {
            val intent = Intent(this, Registrar::class.java)
            startActivity(intent)
        }
    }
}