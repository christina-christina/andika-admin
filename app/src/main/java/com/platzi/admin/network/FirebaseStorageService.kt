package com.platzi.admin.network

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.lang.Exception

class FirebaseStorageService {

    val firebaseStorage = FirebaseStorage.getInstance()

    fun uploadProposal(callback:Callback<String>, uri: Uri) {
        firebaseStorage.getReference("proposal").child(uri.toString()).putFile(uri)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        callback.onSuccess("Berhasil upload")
                    } else if (task.exception != null) {
                        callback.onFailed(task.exception!!)
                    } else {
                        callback.onFailed(Exception("Gagal"))
                    }
                }
    }

    fun uploadKtm(callback:Callback<String>, uri: Uri) {
        firebaseStorage.getReference("ktm").child(uri.toString()).putFile(uri)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    callback.onSuccess("Berhasil upload")
                } else if (task.exception != null) {
                    callback.onFailed(task.exception!!)
                } else {
                    callback.onFailed(Exception("Gagal"))
                }
            }
    }

    fun uploadKtp(callback:Callback<String>, uri: Uri) {
        firebaseStorage.getReference("ktp").child(uri.toString()).putFile(uri)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    callback.onSuccess("Berhasil upload")
                } else if (task.exception != null) {
                    callback.onFailed(task.exception!!)
                } else {
                    callback.onFailed(Exception("Gagal"))
                }
            }
    }

    fun uploadBuktiPembayaran(callback:Callback<String>, uri: Uri) {
        firebaseStorage.getReference("pembayaran").child(uri.toString()).putFile(uri)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    callback.onSuccess("Berhasil upload")
                } else if (task.exception != null) {
                    callback.onFailed(task.exception!!)
                } else {
                    callback.onFailed(Exception("Gagal"))
                }
            }
    }

}