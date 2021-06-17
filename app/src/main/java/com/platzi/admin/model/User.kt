package com.platzi.admin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var namaLengkap: String? = null,
    var noHp: String? = null,
    var namaBank: String? = null,
    var noRekening: String? = null,
    var jummlahSaldo: String? = null,
    var tunggakan: String? = null,
    var dicairkan: String? = null,
    var ktmUrl: String? = null,
    var ktpUrl: String? = null,
    var proposalUrl: String? = null
): Parcelable