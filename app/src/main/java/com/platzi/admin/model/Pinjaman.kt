package com.platzi.admin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pinjaman (
    var pinjamanId: String? = null,
    var userId: String? = null,
    var namaUniversitas: String? = null,
    var fakultas: String? = null,
    var prodi: String? = null,
    var alamatUniversitas: String? = null,
    var nominalPengajuan: String? = null,
    var tanggalPengajuan: String? = null,
    var jenis: String? = null,
    var status: String? = null
) : Parcelable