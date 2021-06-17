package com.platzi.admin.view.ui.pembayaran

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.platzi.admin.R
import com.platzi.admin.model.Pembayaran
import com.platzi.admin.view.adapter.PembayaranAdapter
import com.platzi.admin.viewModel.PembayaranViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_pembayaran2.*

class PembayaranFragment : Fragment() {
    val pembayaranList: MutableList<Pembayaran> = mutableListOf()

    private lateinit var viewModel: PembayaranViewModel
    private lateinit var rvAdapter: PembayaranAdapter

    val itemListener = object : PembayaranAdapter.PembayaranViewHolderListener {
        override fun onItemClicked(pembayaran: Pembayaran) {
            var bundle = bundleOf("pembayaran" to pembayaran)
            findNavController().navigate(R.id.action_pembayaranFragment_to_pembayaranDetailFragment, bundle)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        pembayaranList.clear()
        return inflater.inflate(R.layout.fragment_pembayaran2, container, false)
    }

    override fun onResume() {
        super.onResume()
        pembayaranList.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.bnvMenu?.visibility = View.VISIBLE

        viewModel = ViewModelProvider(this).get(PembayaranViewModel::class.java)
        viewModel.onRefresh()

        recyclerView.layoutManager = LinearLayoutManager(context)
        rvAdapter = PembayaranAdapter(pembayaranList, itemListener)
        recyclerView.adapter = rvAdapter

        viewModel.getPermintaanPembayaran()

        observerViewModel()
    }

    fun observerViewModel() {

        viewModel.dataPembayaranResponse.observe(
            viewLifecycleOwner,
            Observer<List<Pembayaran>> { resp ->

                if (resp != null) {
                    resp.forEach { pembayaranList.add(it) }
                    rvAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(activity, "Gagal Memuat", Toast.LENGTH_SHORT).show()
                }
            })
    }
}