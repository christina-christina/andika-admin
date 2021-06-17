package com.platzi.admin.view.ui.pembayaran

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.platzi.admin.R
import com.platzi.admin.model.Pembayaran
import com.platzi.admin.model.User
import com.platzi.admin.viewModel.PembayaranViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail_pembayaran.*
import kotlinx.android.synthetic.main.fragment_detail_pembayaran.btn_lihat_profile
import kotlinx.android.synthetic.main.fragment_detail_pembayaran.btn_setujui
import kotlinx.android.synthetic.main.fragment_detail_pembayaran.btn_tolak
import kotlinx.android.synthetic.main.fragment_detail_pembayaran.et_nominal
import kotlinx.android.synthetic.main.fragment_detail_pembayaran.et_tanggal
import kotlinx.android.synthetic.main.fragment_detail_pembayaran.tv_nama
import kotlinx.android.synthetic.main.fragment_detail_pembayaran.tv_no_hp
import java.text.NumberFormat

class PembayaranDetailFragment : Fragment() {

    private lateinit var viewModel: PembayaranViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_pembayaran, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.bnvMenu?.visibility = View.GONE

        viewModel = ViewModelProvider(this).get(PembayaranViewModel::class.java)
        viewModel.onRefresh()

        var pembayaran: Pembayaran = PembayaranDetailFragmentArgs.fromBundle(requireArguments()).pembayaran

        et_nominal.text = "Rp" + NumberFormat.getInstance().format(pembayaran.nominal!!.toInt())
        et_tanggal.text = pembayaran.tanggal

        btn_setujui.setOnClickListener {
            pembayaran.status = "Setujui"
            viewModel.setujuiPembayaran(pembayaran)
        }

        btn_tolak.setOnClickListener {
            pembayaran.status = "Ditolak"
            viewModel.tolakPembayaran(pembayaran)
        }

        if (pembayaran.buktiUrl != null) {
            Glide.with(this)
                .load(pembayaran.buktiUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(iv_bukti)
        } else {
            iv_bukti.setImageResource(R.drawable.placeholder)
        }

        viewModel.getDataUserWithId(pembayaran.userId!!)

        observerViewModel()
    }

    fun observerViewModel() {
        viewModel.dataUserResponse.observe(viewLifecycleOwner, Observer<User> { resp ->

            btn_lihat_profile.setOnClickListener {
                var bundle = bundleOf("user" to resp)
                findNavController().navigate(R.id.action_pembayaranDetailFragment_to_userDetailFragment, bundle)
            }

            tv_nama.text = resp.namaLengkap
            tv_no_hp.text = resp.noHp


        })


        viewModel.actionResponse.observe(viewLifecycleOwner, Observer<String> { resp ->

            Toast.makeText(activity, resp, Toast.LENGTH_SHORT).show()
            if (resp.contains("Berhasil")) {
                findNavController().navigate(R.id.action_pembayaranDetailFragment_to_pembayaranFragment)
            }
        })
    }
}