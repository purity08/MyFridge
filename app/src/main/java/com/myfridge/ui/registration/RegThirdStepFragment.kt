package com.myfridge.ui.registration

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.myfridge.R
import com.myfridge.auth.FirebaseInstance.auth
import com.myfridge.storage.entity.Account
import com.myfridge.util.UserSettings
import com.myfridge.util.Utils
import com.myfridge.util.Utils.gone
import com.myfridge.viewModel.AddAccountViewModel
import kotlinx.android.synthetic.main.dialog_pick_photo.view.*
import kotlinx.android.synthetic.main.fragment_reg_third_step.*
import com.myfridge.util.Utils.setVisibility

class RegThirdStepFragment : Fragment(R.layout.fragment_reg_third_step) {

    private val addAccountViewModel: AddAccountViewModel by viewModels()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        textInfoPhotoTextView.setOnClickListener {
            dialogPhoto()
        }

        completeButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString()
            val lastName = secondNameEditText.text.toString()
            val photo = photoUserImageView.drawable
            val email = emailEditText.text.toString()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && photo != null) {
                UserSettings.saveRegUser(requireContext(), true)

                val account = assembleAccount(firstName, lastName, email, photo)
                addAccountViewModel.insert(account)

                UserSettings.saveFirstStart(requireContext(), true)
                navigateToMain()
            } else {
                Toast.makeText(requireContext(), "Fill in all the fields!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        skipButton.setOnClickListener {

            val account = assembleAccount(
                "firstName", "lastName", "email", resources.getDrawable(R.drawable.default_profile_photo)
            )
            addAccountViewModel.insert(account)
            navigateToMain()
        }
    }


    private fun assembleAccount(firstName: String, lastName: String, email: String, photo: Drawable): Account {
        UserSettings.saveRegUser(requireContext(), true)

        val photoBitmap = photo.toBitmap()

        val filePath = Utils.saveToGallery(requireContext(),
            photoBitmap, "user_${auth.currentUser!!.uid}")

        return Account(
            id = auth.currentUser!!.uid,
            name = firstName,
            lastName = lastName,
            email = email,
            phoneNumber = auth.currentUser!!.phoneNumber,
            imagePath = filePath
        )
    }

    private fun dialogPhoto() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_pick_photo, null)

        val builder = MaterialAlertDialogBuilder(requireContext()).setView(dialogView)
        val alertDialog = builder.create()

        alertDialog.window?.setDimAmount(0.7F)
        alertDialog.show()

        dialogView.chooseExistingTextView.setOnClickListener {
            openGallery()
            alertDialog.dismiss()
        }

        dialogView.takePhotoTextView.setOnClickListener {
            openCamera()
            alertDialog.dismiss()
        }

        dialogView.cancelTextView.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val imageBitmap = data?.extras?.get("data") as Bitmap
                photoUserImageView.setImageBitmap(imageBitmap)

                photoUserImageView.setVisibility(true)
                textInfoPhotoTextView.gone()

            } else if (requestCode == REQUEST_PICK_IMAGE) {
                val uri = data?.data
                photoUserImageView.setImageURI(uri)

                photoUserImageView.setVisibility(true)
                textInfoPhotoTextView.gone()
            }
        }
    }

    private fun openCamera() {
        if (hasCameraPermission()) {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        } else {
            requestCameraPermission()
        }
    }

    private fun openGallery() {
        Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type = "image/*"
            intent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(intent, REQUEST_PICK_IMAGE)
            }
        }
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                }
            }
        }
    }

    private fun requestCameraPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA),
            MY_CAMERA_REQUEST_CODE
        )
    }

    private fun navigateToMain() {
        (activity as RegistrationActivity).navigateToMainActivity()
    }

    companion object {
        private const val MY_CAMERA_REQUEST_CODE = 100
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_PICK_IMAGE = 2
    }
}