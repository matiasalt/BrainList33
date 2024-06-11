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
import com.google.firebase.auth.FirebaseAuth
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Suppress("NAME_SHADOWING", "DEPRECATION")
class Lists : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var listdb: DBLists
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lists)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lists)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //
        //Menu despegable
        //

        val drawerLayout = findViewById<DrawerLayout>(R.id.lists)
        val navView = findViewById<NavigationView>(R.id.navView)
        val toolBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)

        setSupportActionBar(toolBar)
        navView.bringToFront()

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        toolBar.setNavigationIcon(R.drawable.menu_icon)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //Items Lista

        auth = FirebaseAuth.getInstance()
        val currentUserId = auth.currentUser
        val userId = currentUserId?.uid.toString()

        // Recuperar el valor de item.first del Intent
        val nombreLista = intent.getStringExtra("nombreLista").toString()

        // Usar el valor de itemFirst como sea necesario
        // Por ejemplo, mostrarlo en un TextView
        val textView = findViewById<TextView>(R.id.tVTitle)
        textView.text = nombreLista

        listdb = DBLists(this)
        val dataList = listdb.getDataFromSQLite(userId, nombreLista) // Función que obtiene los datos de la base de datos

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

            //COLOR DEL CHECKBOX

            val colorStateList = ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf(-android.R.attr.state_checked)),
                intArrayOf(Color.RED, Color.GREEN) // Cambia Color.RED y Color.GREEN por los colores deseados
            )
            checkBox.buttonTintList = colorStateList

            val textView = TextView(this)
            textView.text = item.first
            textView.textSize = 30f
            textView.gravity = Gravity.CENTER_VERTICAL
            textView.setTextColor(Color.BLACK)

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

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val drawerLayout = findViewById<DrawerLayout>(R.id.lists)
        when (menuItem.itemId){
            R.id.inicio -> {
                val intent = Intent(this, MainMenu::class.java)
                startActivity(intent)
            }
            R.id.logout -> {
                Firebase.auth.signOut()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}