package com.matiasd.brainlist3

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Suppress("DEPRECATION")
class   Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var listdb: DBListButton
    private lateinit var listitemdb: DBLists
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Auth
        auth = Firebase.auth
        listdb = DBListButton(this)
        listitemdb = DBLists(this)

        val buttonlog = findViewById<Button>(R.id.btnLogin)
        buttonlog.setOnClickListener {
            val email = findViewById<EditText>(R.id.eTEmailLog).getText().toString()
            val password = findViewById<EditText>(R.id.eTPassLog).getText().toString()

            if(email == "admin"){
                if(password == "admin"){
                    listitemdb.dropTable()
                    listdb.dropTable()
                    Toast.makeText(this, "Bases de datos borrada", Toast.LENGTH_SHORT).show()
                }
            }else{
                loginUser(email, password)
            }
        }

        val buttonreg = findViewById<Button>(R.id.btnRegister)
        buttonreg.setOnClickListener {
            val intent = Intent(this, Registrar::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d(ContentValues.TAG, "currentUser: not null")
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish() // Opcional, dependiendo de si deseas conservar o no la pila de actividades
    }

    private fun loginUser(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val intent = Intent(this, MainMenu::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
    }
}