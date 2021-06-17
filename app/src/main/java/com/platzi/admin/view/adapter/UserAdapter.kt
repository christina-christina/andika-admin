package com.platzi.admin.view.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.platzi.admin.R
import com.platzi.admin.model.User
import kotlinx.android.synthetic.main.user_card.view.*

class UserAdapter(val users : MutableList<User>, private val listener: UserViewHolderListener) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(val card : View) : RecyclerView.ViewHolder(card)

    interface UserViewHolderListener{
        fun onItemClicked(user : User)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        if (holder != null){
            val user = users[position]

            holder.card.tv_nama.text = user.namaLengkap
            holder.card.tv_no_hp.text = user.noHp
            holder.card.setOnClickListener {
                listener.onItemClicked(user)
            }
        }
    }


    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false)
        return UserViewHolder(
            view
        )
    }

    override fun getItemCount() = users.size


}