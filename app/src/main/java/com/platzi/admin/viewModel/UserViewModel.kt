package com.platzi.admin.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.platzi.admin.model.User
import com.platzi.admin.network.Callback
import com.platzi.admin.network.FirestoreService
import java.lang.Exception

class UserViewModel : ViewModel() {

    val firestoreService = FirestoreService()
    var dataResponse: MutableLiveData<List<User>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()

    fun onRefresh() {

    }

    fun getListDataUser() {
        firestoreService.getListDataUser(object : Callback<List<User>> {
            override fun onSuccess(result: List<User>?) {
                dataResponse.value = result
                processFinished()
            }

            override fun onFailed(exception: Exception) {
                processFinished()
            }
        })
    }

    fun processFinished() {
        this.isLoading.value = true
    }
}