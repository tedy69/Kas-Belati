package tech.riseofdevelopers.kas.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var email: String ?="",
    var nama: String ?="",
    var username: String ?="",
    var password: String ?="",
    var profesi: String ?="",
    var pemasukan: Double ?=0.0,
    var pengeluaran: Double ?=0.0,
    var saldo: Double ?=0.0
) : Parcelable