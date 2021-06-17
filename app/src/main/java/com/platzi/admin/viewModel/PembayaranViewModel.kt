package com.platzi.admin.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.platzi.admin.model.Pembayaran
import com.platzi.admin.model.User
import com.platzi.admin.network.Callback
import com.platzi.admin.network.FirebaseStorageService
import com.platzi.admin.network.FirestoreService
import java.lang.Exception

class PembayaranViewModel:ViewModel() {

    val firebaseStorageService = FirebaseStorageService()
    val firestoreService = FirestoreService()
    var uploadDataResponse: MutableLiveData<String> = MutableLiveData()
    var dataPembayaranResponse: MutableLiveData<List<Pembayaran>> = MutableLiveData()
    var dataUserResponse: MutableLiveData<User> = MutableLiveData()
    var actionResponse: MutableLiveData<String> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()

    fun onRefresh(){

    }

    fun getPermintaanPembayaran() {
        firestoreService.getPermintaanPembayaran(object:Callback<List<Pembayaran>> {
            override fun onSuccess(result: List<Pembayaran>?) {
                dataPembayaranResponse.value = result
                processFinished()
            }
            override fun onFailed(exception: Exception) {
                processFinished()
            }
        })
    }

    fun getDataUserWithId(userId: String) {
        firestoreService.getDataUserWithId(object:Callback<User> {
            override fun onSuccess(result: User?) {
                dataUserResponse.value = result
                processFinished()
            }
            override fun onFailed(exception: Exception) {
                processFinished()
            }
        }, userId)
    }

    fun tolakPembayaran(pembayaran: Pembayaran) {
        firestoreService.simpanDataPembayaran(object:Callback<String> {
            override fun onSuccess(result: String?) {
                actionResponse.value = "Berhasil"
                processFinished()
            }

            override fun onFailed(exception: Exception) {
                actionResponse.value = exception.message
                processFinished()
            }
        }, pembayaran)
    }

    fun setujuiPembayaran(pembayaran: Pembayaran) {

        firestoreService.getDataUserWithId(object : Callback<User> {
            override fun onSuccess(result: User?) {
                if (result != null) {
                    var tunggakan = 0
                    if (result.tunggakan != null) {
                        tunggakan = result.tunggakan!!.toInt()
                    }
                    var nominalPembayaran = pembayaran.nominal!!.toInt()
                    if (nominalPembayaran <= tunggakan) {
                        result.tunggakan = (tunggakan - nominalPembayaran).toString()

                        firestoreService.simpanDataDiri(object : Callback<String> {
                            override fun onSuccess(result: String?) {

                                firestoreService.simpanDataPembayaran(object:Callback<String> {
                                    override fun onSuccess(result: String?) {
                                        actionResponse.value = "Berhasil"
                                        processFinished()
                                    }
                                    override fun onFailed(exception: Exception) {
                                        actionResponse.value = exception.message
                                        processFinished()
                                    }
                                }, pembayaran)
                            }

                            override fun onFailed(exception: Exception) {
                                actionResponse.value = exception.message
                                processFinished()
                            }
                        }, result, pembayaran.userId!!)
                    }
                    processFinished()
                }
            }

            override fun onFailed(exception: Exception) {
                actionResponse.value = exception.message
                processFinished()
            }
        }, pembayaran.userId!!)
    }


    fun processFinished(){
        this.isLoading.value=true
    }
}