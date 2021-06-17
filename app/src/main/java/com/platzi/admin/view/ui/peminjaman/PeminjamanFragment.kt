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
import androidx.recyclerview.widget.LinearLayoutManager
import com.platzi.admin.R
import com.platzi.admin.model.Pinjaman
import com.platzi.admin.view.adapter.PeminjamanAdapter
import com.platzi.admin.viewModel.PeminjamanViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_peminjaman.*

class PeminjamanFragment : Fragment() {
    val peminjamanList: MutableList<Pinjaman> = mutableListOf()

    private lateinit var viewModel: PeminjamanViewModel
    private lateinit var rvAdapter: PeminjamanAdapter

    val itemListener = object : PeminjamanAdapter.PeminjamanViewHolderListener {
        override fun onItemClicked(pinjaman: Pinjaman) {
            var bundle = bundleOf("pinjaman" to pinjaman)
            findNavController().navigate(R.id.action_peminjamanFragment_to_peminjamanDetailFragment, bundle)
        }
    }

    override fun onResume() {
        super.onResume()
        peminjamanList.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.bnvMenu?.visibility = View.VISIBLE

        // Inflate the layout for this fragment
        peminjamanList.clear()
        return inflater.inflate(R.layout.fragment_peminjaman, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PeminjamanViewModel::class.java)
        viewModel.onRefresh()

        recyclerView.layoutManager = LinearLayoutManager(context)
        rvAdapter = PeminjamanAdapter(peminjamanList, itemListener)
        recyclerView.adapter = rvAdapter

        viewModel.getPermintaanPeminjaman()

        observerViewModel()
    }

    fun observerViewModel() {

        viewModel.dataPinjamanResponse.observe(
            viewLifecycleOwner,
            Observer<List<Pinjaman>> { resp ->

                if (resp != null) {
                    resp.forEach { peminjamanList.add(it) }
                    rvAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(activity, "Gagal Memuat", Toast.LENGTH_SHORT).show()
                }
            })


    }
}