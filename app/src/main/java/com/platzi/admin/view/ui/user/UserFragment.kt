package com.platzi.admin.view.ui.user

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
import com.platzi.admin.model.User
import com.platzi.admin.view.adapter.UserAdapter
import com.platzi.admin.viewModel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {
    val userList: MutableList<User> = mutableListOf()

    private lateinit var viewModel: UserViewModel
    private lateinit var rvAdapter: UserAdapter

    val itemListener = object: UserAdapter.UserViewHolderListener {
        override fun onItemClicked(user: User) {
            var bundle = bundleOf("user" to user)
            findNavController().navigate(R.id.action_userFragment_to_userDetailFragment, bundle)
        }
    }

    override fun onResume() {
        super.onResume()
        userList.clear()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userList.clear()
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.bnvMenu?.visibility = View.VISIBLE

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.onRefresh()

        recyclerView.layoutManager = LinearLayoutManager(context)
        rvAdapter = UserAdapter(userList, itemListener)
        recyclerView.adapter = rvAdapter

        viewModel.getListDataUser()

        observerViewModel()
    }

    fun observerViewModel() {

        viewModel.dataResponse.observe(
            viewLifecycleOwner,
            Observer<List<User>> { resp ->

                if (resp != null) {
                    resp.forEach { userList.add(it) }
                    rvAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(activity, "Gagal Memuat", Toast.LENGTH_SHORT).show()
                }
            })
    }
}