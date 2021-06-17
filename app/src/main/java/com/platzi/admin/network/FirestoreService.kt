package com.platzi.admin.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.platzi.admin.model.*
import java.lang.Exception

class FirestoreService {
    val firebaseFirestore = FirebaseFirestore.getInstance()
    val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()

    init {
        this.firebaseFirestore.firestoreSettings = settings
    }


    fun simpanDataPembayaran(callback: Callback<String>, pembayaran: Pembayaran) {
        this.firebaseFirestore.collection("pembayaran").document(pembayaran.pembayaranId!!)
            .set(pembayaran)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback.onSuccess("Berhasil")
                } else if (task.exception != null) {
                    callback.onFailed(task.exception!!)
                } else {
                    callback.onFailed(Exception("Gagal!"))
                }
            }

    }


    fun simpanDataPeminjaman(callback: Callback<String>, pinjaman: Pinjaman) {
        this.firebaseFirestore.collection("pinjaman").document(pinjaman.pinjamanId!!).set(pinjaman)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback.onSuccess("Berhasil")
                } else if (task.exception != null) {
                    callback.onFailed(task.exception!!)
                } else {
                    callback.onFailed(Exception("Gagal!"))
                }
            }

    }

    fun getPermintaanPembayaran(callback: Callback<List<Pembayaran>>) {
        this.firebaseFirestore.collection("pembayaran")
            .get()
            .addOnSuccessListener { result ->

                val listPermintaan = mutableListOf<Pembayaran>()

                for (doc in result) {
                    val item = doc.toObject(Pembayaran::class.java)
                    item.pembayaranId = doc.id

                    if (item.status == "Menunggu konfirmasi") {
                        listPermintaan.add(item)
                    }
                }
                callback.onSuccess(listPermintaan)
            }
    }

    fun getPermintaanPeminjaman(callback: Callback<List<Pinjaman>>) {
        this.firebaseFirestore.collection("pinjaman")
            .get()
            .addOnSuccessListener { result ->

                val listPinjaman = mutableListOf<Pinjaman>()

                for (doc in result) {
                    val item = doc.toObject(Pinjaman::class.java)
                    item.pinjamanId = doc.id

                    if (item.status == "Menunggu konfirmasi") {
                        listPinjaman.add(item)
                    }
                }
                callback.onSuccess(listPinjaman)
            }
    }

    fun simpanDataDiri(callback: Callback<String>, user: User, userId: String) {
        this.firebaseFirestore.collection("user").document(userId).set(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback.onSuccess("Berhasil")
                } else if (task.exception != null) {
                    callback.onFailed(task.exception!!)
                } else {
                    callback.onFailed(Exception("Gagal!"))
                }
            }

    }

    fun getDataUserWithId(callback: Callback<User>, userId: String) {
        this.firebaseFirestore.collection("user").document(userId)
            .get()
            .addOnSuccessListener { result ->
                callback.onSuccess(result.toObject(User::class.java))
            }
    }

    fun getListDataUser(callback: Callback<List<User>>) {
        this.firebaseFirestore.collection("user")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val list = result.toObjects(User::class.java)
                    callback.onSuccess(list)
                    break
                }
            }
    }
}