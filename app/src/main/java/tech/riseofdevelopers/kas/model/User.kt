package tech.riseofdevelopers.kas.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var email: String ?="",
    var nama: String ?="",
    var username: String ?="",
    var password: String ?="",
    var profesi: String ?="",
) : Parcelable
