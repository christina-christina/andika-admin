package com.platzi.admin.view.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.platzi.admin.R
import com.platzi.admin.model.Pinjaman
import kotlinx.android.synthetic.main.permintaan_peminjaman_card.view.*
import java.text.NumberFormat

class PeminjamanAdapter(val listPeminjaman: MutableList<Pinjaman>, private val listener: PeminjamanViewHolderListener) : RecyclerView.Adapter<PeminjamanAdapter.PeminjamanViewHolder>() {

    class PeminjamanViewHolder(val card : View) : RecyclerView.ViewHolder(card)

    interface PeminjamanViewHolderListener{
        fun onItemClicked(peminjaman: Pinjaman)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PeminjamanViewHolder, position: Int) {
        if (holder != null){
            val peminjaman = listPeminjaman[position]

            holder.card.tv_nominal.text = "Rp" + NumberFormat.getInstance().format(peminjaman.nominalPengajuan!!.toInt())
            holder.card.tv_status.text = peminjaman.status
            holder.card.setOnClickListener {
                listener.onItemClicked(peminjaman)
            }
        }
    }


    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): PeminjamanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.permintaan_peminjaman_card, parent, false)
        return PeminjamanViewHolder(
            view
        )
    }

    override fun getItemCount() = listPeminjaman.size


}