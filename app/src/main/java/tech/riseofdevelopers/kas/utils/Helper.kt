package tech.riseofdevelopers.catatuang.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    // fungsi untuk mengambil waktu saat ini
    fun getCurrentDate(): String {
        val currentTime = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("d MMMM yyyy")
        val date = dateFormat.format(currentTime.time)
        return date
    }

    //fungsi untuk mengubah number ke format uang
    fun convertToRupiah(number: Long): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.maximumFractionDigits = 0
        return numberFormat.format(number).toString()
    }
}