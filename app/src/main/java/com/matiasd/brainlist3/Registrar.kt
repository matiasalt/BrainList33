package com.matiasd.brainlist3

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


@Suppress("DEPRECATION")
class Registrar : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Auth
        auth = Firebase.auth

        val button = findViewById<Button>(R.id.btnRegister)
        button.setOnClickListener {
            val email = findViewById<EditText>(R.id.eTEmail).getText().toString()
            val password = findViewById<EditText>(R.id.eTPass).getText().toString()
            val passwordConfirm = findViewById<EditText>(R.id.eTPassConfirm).getText().toString()

            if(email.isNotBlank() and password.isNotBlank() and passwordConfirm.isNotBlank()){
                    if(password == passwordConfirm){
                        registerUser(email, password)
                    }else{
                        Toast.makeText(this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    }
            }else{
                Toast.makeText(this, "Completar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d(TAG, "currentUser: not null")
        }
    }

    private fun registerUser (email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Correo ya registrado", Toast.LENGTH_SHORT,).show()
                }
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish() // Opcional, dependiendo de si deseas conservar o no la pila de actividades
    }
}
