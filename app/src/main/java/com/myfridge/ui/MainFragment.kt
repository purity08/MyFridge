package com.myfridge.ui


import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.myfridge.R
import com.myfridge.ui.main.MainActivity
import com.myfridge.util.DownloadImage
import com.myfridge.util.UserSettings
import com.myfridge.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.io.File

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var mainActivity: MainActivity
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //mainActivity = activity as MainActivity

        //observeAccount()

    }

    private fun observeAccount() {
        mainActivityViewModel.account.observe(viewLifecycleOwner, { account ->
            if (UserSettings.getFirstGoogleLogin(requireContext()) && UserSettings.getFirstStart(
                    requireContext()
                )
            ) {
                val path = DownloadImage(account.imagePath, requireContext()).execute("").get()
                account.imagePath = path.toString()
            }
            mainActivity.profile_circle_image.setImageURI(Uri.fromFile(File(account.imagePath)))
        })
    }
}