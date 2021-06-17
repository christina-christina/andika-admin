package com.platzi.admin.view.ui.user

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.platzi.admin.R
import com.platzi.admin.model.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user_detail.*
import java.io.File
import java.text.NumberFormat


class UserDetailFragment : Fragment() {

    val PERMISSION_REQUEST_WRITE = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.bnvMenu?.visibility = View.GONE

        var user: User = UserDetailFragmentArgs.fromBundle(requireArguments()).user

        requestWriteExternalStoragePermission()

        et_nama_lengkap.text = user.namaLengkap
        et_no_hp.text = user.noHp
        et_nama_bank.text = user.namaBank
        et_no_rekening.text = user.noRekening

        if (user.jummlahSaldo != null) {
            et_saldo.text = "Rp " + NumberFormat.getInstance().format(user.jummlahSaldo!!.toInt())
        } else {
            et_saldo.text = "Rp 0"
        }

        if (user.tunggakan != null) {
            et_tunggakan.text = "Rp " + NumberFormat.getInstance().format(user.tunggakan!!.toInt())
        } else {
            et_tunggakan.text = "Rp 0"
        }

        if (user.dicairkan != null) {
            et_dicairkan.text = "Rp " + NumberFormat.getInstance().format(user.dicairkan!!.toInt())
        } else {
            et_dicairkan.text = "Rp 0"
        }

        if (user.ktmUrl != null) {
            Glide.with(this)
                .load(user.ktmUrl)
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .fallback(R.drawable.placeholder)
                .into(iv_ktm)
        } else {
            iv_ktm.setImageResource(R.drawable.placeholder)
        }

        if (user.ktpUrl != null) {
            Glide.with(this)
                .load(user.ktpUrl)
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .fallback(R.drawable.placeholder)
                .into(iv_ktp)
        } else {
            iv_ktp.setImageResource(R.drawable.placeholder)
        }


        tv_lihat_proposal.setOnClickListener {
            if (user.proposalUrl != null) {
                downloadPdf(requireActivity(), user.proposalUrl, "Proposal")
            } else {
                Toast.makeText(activity, "Pengguna belum mengunggah proposal", Toast.LENGTH_SHORT).show()
            }

        }

        observerViewModel()
    }

    fun observerViewModel() {

    }

    fun downloadPdf(baseActivity: FragmentActivity, url: String?, title: String?): Long {
        val direct = File(Environment.getExternalStorageDirectory().toString() + "/download")

        if (!direct.exists()) {
            direct.mkdirs()
        }
        val extension = url?.substring(url.lastIndexOf("."))
        val downloadReference: Long
        var  dm: DownloadManager
        dm= baseActivity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setDestinationInExternalPublicDir(
            "/download",
            "pdf" + System.currentTimeMillis() + extension
        )
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setTitle(title)
        Toast.makeText(baseActivity, "Harap menunggu...", Toast.LENGTH_SHORT).show()

        downloadReference = dm?.enqueue(request) ?: 0

        return downloadReference

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_WRITE) {
            // Request for camera permission.
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.


            } else {
                // Permission request was denied.

            }
        }
    }

    private fun requestWriteExternalStoragePermission() {
        // Permission has not been granted and must be requested.
        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.

            requestPermissionsCompat(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_WRITE)

        } else {
            // Request the permission. The result will be received in onRequestPermissionResult().
            requestPermissionsCompat(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_WRITE)
        }
    }



    fun checkSelfPermissionCompat(permission: String) =
        ActivityCompat.checkSelfPermission(requireActivity(), permission)

    fun houldShowRequestPermissionRationaleCompat(permission: String) =
        ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission)

    fun requestPermissionsCompat(permissionsArray: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(requireActivity(), permissionsArray, requestCode)
    }

}