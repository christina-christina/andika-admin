package com.platzi.admin.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.platzi.admin.model.Pinjaman
import com.platzi.admin.model.User
import com.platzi.admin.network.Callback
import com.platzi.admin.network.FirestoreService
import java.lang.Exception

class PeminjamanViewModel:ViewModel() {

    val firestoreService = FirestoreService()
    var dataPinjamanResponse: MutableLiveData<List<Pinjaman>> = MutableLiveData()
    var dataUserResponse: MutableLiveData<User> = MutableLiveData()
    var actionResponse: MutableLiveData<String> = MutableLiveData()
    var uploadResponse: MutableLiveData<String> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()

    fun onRefresh(){

    }

    fun getPermintaanPeminjaman() {
        firestoreService.getPermintaanPeminjaman(object:Callback<List<Pinjaman>> {
            override fun onSuccess(result: List<Pinjaman>?) {
                dataPinjamanResponse.value = result
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

    fun tolakPeminjaman(pinjaman: Pinjaman) {
        firestoreService.simpanDataPeminjaman(object:Callback<String> {
            override fun onSuccess(result: String?) {
                actionResponse.value = "Berhasil"
                processFinished()
            }

            override fun onFailed(exception: Exception) {
                actionResponse.value = exception.message
                processFinished()
            }
        }, pinjaman)
    }

    fun setujuiPeminjaman(pinjaman: Pinjaman) {
        firestoreService.simpanDataPeminjaman(object:Callback<String> {
            override fun onSuccess(result: String?) {

                firestoreService.getDataUserWithId(object:Callback<User> {
                    override fun onSuccess(result: User?) {
                        if (result != null) {

                            var saldoLama = 0
                            if (result.jummlahSaldo != null) {
                                saldoLama = result.jummlahSaldo!!.toInt()
                            }

                            var tunggakan = 0
                            if (result.tunggakan != null) {
                                tunggakan = result.tunggakan!!.toInt()
                            }

                            result.jummlahSaldo = (saldoLama + pinjaman.nominalPengajuan!!.toInt()).toString()
                            result.tunggakan = (tunggakan + pinjaman.nominalPengajuan!!.toInt()).toString()

                            firestoreService.simpanDataDiri(object : Callback<String> {
                                override fun onSuccess(result: String?) {
                                    actionResponse.value = "Berhasil"
                                    processFinished()
                                }

                                override fun onFailed(exception: Exception) {
                                    actionResponse.value = exception.message
                                    processFinished()
                                }
                            }, result, pinjaman.userId!!)
                        }
                        processFinished()
                    }

                    override fun onFailed(exception: Exception) {
                        actionResponse.value = exception.message
                        processFinished()
                    }
                }, pinjaman.userId!!)

                processFinished()
            }

            override fun onFailed(exception: Exception) {
                actionResponse.value = exception.message
                processFinished()
            }
        }, pinjaman)
    }


    fun processFinished(){
        this.isLoading.value=true
    }
}