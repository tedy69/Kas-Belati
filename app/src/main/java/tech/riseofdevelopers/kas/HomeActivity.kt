package tech.riseofdevelopers.kas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fab = findViewById<FloatingActionButton>(R.id.btn_fab)

        fab.setOnClickListener {
            startActivity(Intent(this,DetailTransaksiActivity::class.java))
        }
    }
}