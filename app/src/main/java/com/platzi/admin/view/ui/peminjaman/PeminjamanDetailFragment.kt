package com.platzi.admin.view.ui.peminjaman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.platzi.admin.R
import com.platzi.admin.model.Pinjaman
import com.platzi.admin.model.User
import com.platzi.admin.viewModel.PeminjamanViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_peminjaman_detail.*
import java.text.NumberFormat

class PeminjamanDetailFragment : Fragment() {

    private lateinit var viewModel: PeminjamanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_peminjaman_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.bnvMenu?.visibility = View.GONE

        viewModel = ViewModelProvider(this).get(PeminjamanViewModel::class.java)
        viewModel.onRefresh()

        var pinjaman: Pinjaman = PeminjamanDetailFragmentArgs.fromBundle(requireArguments()).pinjaman

        et_jenis_pinjaman.text = pinjaman.jenis
        et_tanggal.text = pinjaman.tanggalPengajuan
        et_nominal.text = "Rp " + NumberFormat.getInstance().format(pinjaman.nominalPengajuan!!.toInt())

        if (pinjaman.jenis == "uang_kuliah") {
            et_alamat.text = pinjaman.namaUniversitas + ", " + pinjaman.fakultas + ", " + pinjaman.prodi + ", " + pinjaman.alamatUniversitas
        }

        btn_setujui.setOnClickListener {
            pinjaman.status = "Disetujui"
            viewModel.setujuiPeminjaman(pinjaman)
        }

        btn_tolak.setOnClickListener {
            pinjaman.status = "Ditolak"
            viewModel.tolakPeminjaman(pinjaman)
        }


        viewModel.getDataUserWithId(pinjaman.userId!!)

        observerViewModel()
    }

    fun observerViewModel() {
        viewModel.dataUserResponse.observe(viewLifecycleOwner, Observer<User> { resp ->

            btn_lihat_profile.setOnClickListener {
                var bundle = bundleOf("user" to resp)
                findNavController().navigate(R.id.action_peminjamanDetailFragment_to_userDetailFragment, bundle)
            }

            tv_nama.text = resp.namaLengkap
            tv_no_hp.text = resp.noHp

        })


        viewModel.actionResponse.observe(viewLifecycleOwner, Observer<String> { resp ->

            Toast.makeText(activity, resp, Toast.LENGTH_SHORT).show()
            if (resp.contains("Berhasil")) {
                findNavController().navigate(R.id.action_peminjamanDetailFragment_to_peminjamanFragment)
            }
        })
    }
}