package com.platzi.admin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Pembayaran (
    var pembayaranId: String? = null,
    var userId: String? = null,
    var nominal: String? = null,
    var tanggal: String? = null,
    var status: String? = null,
    var buktiUrl: String? = null
): Parcelable
