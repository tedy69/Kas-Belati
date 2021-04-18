package tech.riseofdevelopers.kas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import tech.riseofdevelopers.kas.model.User

class LoginActivity : AppCompatActivity() {
    lateinit var tv_daftar:TextView
    lateinit var iUsername:String
    lateinit var iPassword:String

    lateinit var mDatabase: DatabaseReference
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_daftar = findViewById(R.id.tv_daftar)
        val btnLogin: Button = findViewById(R.id.button)
        val username: EditText = findViewById(R.id.editText)
        val password:EditText = findViewById(R.id.editText2)

        tv_daftar.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preferences = Preferences(this)
//        preferences.setValues("login", "1")
        if (preferences.getValues("isLogin").equals("1")) {
            finishAffinity()

            val intent = Intent(this@LoginActivity,
                    HomeActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            iUsername = username.text.toString()
            iPassword = password.text.toString()

            if (iUsername.equals("")){
                username.error = "Silahkan Isi Username Anda"
                username.requestFocus()
            } else if (iPassword.equals("")){
                password.error = "Silahkan Isi Password Anda"
                password.requestFocus()
            } else {
                pushLogin(iUsername,iPassword)
            }

        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    Toast.makeText(this@LoginActivity, "User tidak ditemukan", Toast.LENGTH_LONG).show()

                } else {
                    if (user.password.equals(iPassword)){
                        Toast.makeText(this@LoginActivity, "Selamat Datang", Toast.LENGTH_LONG).show()
                        preferences.createSession()
                        preferences.setValues("nama", user.nama.toString())
                        preferences.setValues("user", user.username.toString())
                        preferences.setValues("profesi", user.profesi.toString())
                        preferences.setValues("email", user.email.toString())
                        preferences.setValues("saldo", user.saldo.toString())
                        preferences.setValues("pemasukan", user.pemasukan.toString())
                        preferences.setValues("pengeluaran", user.pengeluaran.toString())


                        finishAffinity()

                        val intent = Intent(this@LoginActivity,
                                HomeActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this@LoginActivity, "Password Anda Salah", Toast.LENGTH_LONG).show()
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LoginActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}