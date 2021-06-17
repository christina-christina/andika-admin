package com.platzi.admin.view.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.platzi.admin.R
import com.platzi.admin.model.Pembayaran
import kotlinx.android.synthetic.main.permintaan_pembayaran_card.view.*
import java.text.NumberFormat

class PembayaranAdapter(val listPembayaran: MutableList<Pembayaran>, private val listener: PembayaranViewHolderListener) : RecyclerView.Adapter<PembayaranAdapter.PembayaranViewHolder>() {

    class PembayaranViewHolder(val card : View) : RecyclerView.ViewHolder(card)

    interface PembayaranViewHolderListener{
        fun onItemClicked(pembayaran: Pembayaran)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PembayaranViewHolder, position: Int) {
        if (holder != null){
            val pembayaran = listPembayaran[position]

            holder.card.tv_nominal.text = "Rp" + NumberFormat.getInstance().format(pembayaran.nominal!!.toInt())
            holder.card.tv_status.text = pembayaran.status
            holder.card.setOnClickListener {
                listener.onItemClicked(pembayaran)
            }
        }
    }


    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): PembayaranViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.permintaan_pembayaran_card, parent, false)
        return PembayaranViewHolder(
            view
        )
    }

    override fun getItemCount() = listPembayaran.size


}