package tech.riseofdevelopers.kas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import tech.riseofdevelopers.kas.model.User

class RegisterActivity : AppCompatActivity() {
    lateinit var sPassword:String
    lateinit var sNama:String
    lateinit var sUsername:String
    lateinit var sEmail:String
    lateinit var sProfesi:String

    private lateinit var mFirebaseReference: DatabaseReference
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister: Button = findViewById(R.id.bt_register)
        val et_nama: EditText = findViewById(R.id.et_nama_regis)
        val et_email: EditText = findViewById(R.id.et_email_regis)
        val et_profesi: EditText = findViewById(R.id.et_profesi_regis)
        val et_password: EditText = findViewById(R.id.et_password_regis)
        val et_username: EditText = findViewById(R.id.et_username_regis)
        val tv_masuk: TextView = findViewById(R.id.tv_masuk)

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseReference = mFirebaseDatabase.getReference("User")
        preferences = Preferences(this)

        tv_masuk.setOnClickListener {
            val intent = Intent(this@RegisterActivity,
                LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnRegister.setOnClickListener {
            sPassword = et_password.text.toString()
            sNama = et_nama.text.toString()
            sEmail = et_email.text.toString()
            sUsername = et_username.text.toString()
            sProfesi = et_profesi.text.toString()

            if (sPassword.equals("")) {
                et_password.error = "Silahkan isi Password"
                et_password.requestFocus()
            } else if (sNama.equals("")) {
                et_nama.error = "Silahkan isi Nama"
                et_nama.requestFocus()
            } else if (sProfesi.equals("")) {
                et_profesi.error = "Silahkan isi Profesi"
                et_profesi.requestFocus()
            }  else if (sEmail.equals("")) {
                et_email.error = "Silahkan isi Email"
                et_email.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()){
                et_email.setError("Email tidak valid");
                et_email.requestFocus();
            }else {

                var statusUsername = sUsername.indexOf(".")
                if (statusUsername >= 0) {
                    et_username.error = "Silahkan tulis Username Anda tanpa ."
                    et_username.requestFocus()
                } else {
                    saveUser(sUsername, sProfesi, sPassword, sNama, sEmail)
                }
            }
        }
    }

    private fun saveUser(sUsername: String,sProfesi: String, sPassword: String, sNama: String, sEmail: String) {
        val user = User()
        user.email = sEmail
        user.username = sUsername
        user.profesi = sProfesi
        user.nama = sNama
        user.password = sPassword

        checkingUsername(sUsername, user)
    }

    private fun checkingUsername(iUsername: String, data: User) {
        mFirebaseReference.child(iUsername).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    mFirebaseReference.child(iUsername).setValue(data)
                    preferences.createSession()
                    preferences.setValues("nama", data.nama.toString())
                    preferences.setValues("user", data.username.toString())
                    preferences.setValues("profesi", data.profesi.toString())
//                    preferences.setValues("url", "")
                    preferences.setValues("email", data.email.toString())

                    val intent = Intent(this@RegisterActivity,
                        HomeActivity::class.java).putExtra("data", data)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@RegisterActivity, "User sudah digunakan", Toast.LENGTH_LONG).show()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@RegisterActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}