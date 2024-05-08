package com.matiasd.brainlist3


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Space
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginStart
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Math.random
import kotlin.random.Random

@Suppress("DEPRECATION")
class MainMenu : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var listdb: DBListButton

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

        //
        //Menu despegable
        //

        val drawerLayout = findViewById<DrawerLayout>(R.id.main)
        val navView = findViewById<NavigationView>(R.id.navView)
        val toolBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)

        setSupportActionBar(toolBar)
        navView.bringToFront()

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        toolBar.setNavigationIcon(R.drawable.menu_icon)

        //
        //Crear botones de listas
        //

        auth = FirebaseAuth.getInstance()
        val currentUserId = auth.currentUser
        val userId = currentUserId?.uid.toString()

        // Suponiendo que tienes una lista de objetos con nombre de imagen y ID de imagen obtenidos de la base de datos
        listdb = DBListButton(this)
        val dataList = listdb.getDataFromSQLite(userId) // Función que obtiene los datos de la base de datos

        val scrollView = findViewById<ScrollView>(R.id.sVBtn) // Reference to your ScrollView

        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL

        for (item in dataList) {
            val horizontalLayout = LinearLayout(this)
            horizontalLayout.orientation = LinearLayout.HORIZONTAL

            val button = Button(this)
            button.text = item.second
            button.gravity = Gravity.START // Alinear el texto verticalmente en el centro
            button.layoutParams = LinearLayout.LayoutParams(
                580,
                300
            )
            button.setTextSize(25f)
            button.setTextColor(Color.BLACK)
            // Cargar la fuente personalizada
            val customFont = ResourcesCompat.getFont(this, R.font.poppins_medium)
            // Aplicar la fuente al Button
            button.typeface = customFont
            button.setBackgroundColor(Color.TRANSPARENT)
            button.setPadding(45,70,0,0) //Mover texto

            val image = ImageButton(this)
            image.setImageResource(item.third)
            image.setBackgroundColor(Color.TRANSPARENT)
            val imageLayoutParams = LinearLayout.LayoutParams(
                300,
                300
            )
            imageLayoutParams.gravity = Gravity.END // Alinear la imagen al extremo derecho del layout
            image.layoutParams = imageLayoutParams
            image.scaleType = ImageView.ScaleType.FIT_CENTER

            // Listener de clic para el botón de texto
            button.setOnClickListener {
                // Iniciar la actividad deseada aquí
                val intent = Intent(this, Lists::class.java)
                // Adjuntar el valor de item.first como un extra al Intent
                intent.putExtra("nombreLista", item.second)
                startActivity(intent)
            }

            // Listener de clic para la imagen
            image.setOnClickListener {
                // Iniciar la actividad deseada aquí
                val intent = Intent(this, Lists::class.java)
                // Adjuntar el valor de item.first como un extra al Intent
                intent.putExtra("nombreLista", item.second)
                startActivity(intent)
            }

            horizontalLayout.addView(button)
            horizontalLayout.addView(image)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 0, 0, 30) // Establecer márgenes izquierdo, superior, derecho e inferior

            horizontalLayout.layoutParams = layoutParams

            // Seleccionar color de la lista para el horizontalLayout

            val colorInt = Color.parseColor(item.fourth)
            // Aplicar el fondo con bordes redondeados al horizontalLayout
            val randomColorDrawable = GradientDrawable().apply {
                setColor(colorInt)
                cornerRadius = 25F
            }
            horizontalLayout.background = randomColorDrawable


            linearLayout.addView(horizontalLayout)
        }

        scrollView.addView(linearLayout)

        val btnAdd = findViewById<ImageButton>(R.id.btnAdd)

        btnAdd.setOnClickListener {
            val intent = Intent(this, AddList::class.java)
            startActivity(intent)
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.main)
        if(drawerLayout.isDrawerOpen(Gravity.START)){
            drawerLayout.closeDrawer(Gravity.START)
        }else{
            super.onBackPressed()
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish() // Opcional, dependiendo de si deseas conservar o no la pila de actividades
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val drawerLayout = findViewById<DrawerLayout>(R.id.main)
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


