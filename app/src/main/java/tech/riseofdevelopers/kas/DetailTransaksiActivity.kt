package tech.riseofdevelopers.kas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import tech.riseofdevelopers.catatuang.model.Transaksi
import tech.riseofdevelopers.catatuang.utils.Helper

class DetailTransaksiActivity : AppCompatActivity() {
    
    private  lateinit var edNameTransaksi : EditText
    private  lateinit var edNominalTransaksi : EditText
    private  lateinit var btnSubmit : Button
    private  lateinit var radioGrup : RadioGroup
    private  lateinit var database : DatabaseReference
    private  lateinit var preferences: Preferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi)

        //init firebase
        database = FirebaseDatabase.getInstance().getReference()

        //init pref
        preferences = Preferences(this)

        edNameTransaksi = findViewById(R.id.ed_nama_transaksi)
        edNominalTransaksi = findViewById(R.id.ed_nominal_transaksi)
        btnSubmit = findViewById(R.id.btn_simpan)
        radioGrup = findViewById(R.id.radioGroup2)
        
        btnSubmit.setOnClickListener {

            val transactionName = edNameTransaksi.text.toString()
            val transactionNominal = edNominalTransaksi.text.toString()
            val selectedStatus = getSelectedStatus()
            val userLoggin = preferences.getValues("user")

            val uid = database.child("Transaksi").child(userLoggin!!).push().key

            if (validation(transactionName,transactionNominal)){
                // kalo true

                val transaksiData = Transaksi(uid,selectedStatus,Helper.getCurrentDate(),transactionName,transactionNominal.toLong())

                database.child("Transaksi")
                    .child(userLoggin!!)
                    .child(uid!!)
                    .setValue(transaksiData)
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this, "Data berhasil di simpan", Toast.LENGTH_SHORT).show()
                            finish()
                        }else{
                            Toast.makeText(this, "Gagal menyimpan transaksi", Toast.LENGTH_SHORT).show()
                        }
                    }

            }

        }
    }

    private fun validation(username : String, nominal : String): Boolean {
        var isValid = true

        if (username.isEmpty()){
            isValid = false
            edNameTransaksi.setError("Masukkan Nama")
        }

        if (nominal.isEmpty()){
            isValid = false
            edNominalTransaksi.setError(("Masukkan Nominal"))
        }

        return isValid
    }

    private fun getSelectedStatus() : String{
        val selectedId = radioGrup.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(selectedId)

        val selectedStatus = radioButton.text.toString()
        return  selectedStatus
    }
}