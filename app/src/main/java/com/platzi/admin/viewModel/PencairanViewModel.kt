package com.platzi.admin.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.platzi.admin.model.Pencairan
import com.platzi.admin.network.FirestoreService

class PencairanViewModel : ViewModel() {

    val firestoreService = FirestoreService()
    var uploadResponse: MutableLiveData<String> = MutableLiveData()
    var dataPencairanResponse: MutableLiveData<List<Pencairan>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()

    fun onRefresh() {

    }


    fun processFinished() {
        this.isLoading.value = true
    }
}