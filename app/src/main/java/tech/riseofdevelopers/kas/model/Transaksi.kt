package tech.riseofdevelopers.catatuang.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaksi(
        val id : String? = null,
        val status : String? = null,
        val date : String? = null,
        val title : String? = null,
        val nominal : Long = 0
) : Parcelable
