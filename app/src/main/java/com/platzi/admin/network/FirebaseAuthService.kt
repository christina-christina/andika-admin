package com.platzi.admin.network

import com.google.firebase.auth.FirebaseAuth
import com.platzi.admin.model.User
import java.lang.Exception

class FirebaseAuthService {

    val firebaseAuth = FirebaseAuth.getInstance()

    fun login(callback:Callback<String>, email: String, password: String){

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        callback.onSuccess("Berhasil login")
                    } else if (signIn.exception != null) {
                        callback.onFailed(signIn.exception!!)
                    } else {
                        callback.onFailed(Exception("Gagal!"))
                    }
                }
    }

    fun register(callback:Callback<String>, email: String, password: String, user: User){
        this.firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback.onSuccess("Berhasil register")
                    } else if (task.exception != null) {
                        callback.onFailed(task.exception!!)
                    } else {
                        callback.onFailed(Exception("Gagal!"))
                    }
                }
    }

    fun logout(callback:Callback<String>){
        this.firebaseAuth.signOut()
    }
}