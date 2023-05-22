package com.diegocalero.todoapplication.presentation.ui.createtask

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.diegocalero.todoapplication.R
import com.diegocalero.todoapplication.databinding.FragmentCreateTaskBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Fragment that represents the screen where the user will create the tasks
 */
@AndroidEntryPoint
class CreateTaskFragment : Fragment() {

    private lateinit var binding: FragmentCreateTaskBinding
    private val viewModel: CreateTaskViewModel by viewModels()
    private var currentPhotoPath: String? = null

    /**
     * Activity result where the app's config opened is processed
     */
    private var openAppSettingsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    /**
     * Activity result where the camera launch result is processed
     */
    private var takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.imagePath.value = currentPhotoPath
        }
    }

    /**
     * Activity result where the camera permission is processed
     */
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCamera()
        } else {
            showSnackBarCameraPermissionNotAllowed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTaskBinding.inflate(inflater)
        binding.editTextTaskTitle.addTextChangedListener {
            viewModel.taskTitle.value = it.toString()
        }
        binding.editTextTaskDescription.addTextChangedListener {
            viewModel.taskDescription.value = it.toString()
        }
        initClicks()
        initObservers()
        return binding.root
    }

    /**
     * Function that creates a file that represents where the image must be stored
     */
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = requireActivity().filesDir
        val imageFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
        currentPhotoPath = imageFile.absolutePath
        return imageFile
    }

    /**
     * Function that initializes the clicks for
     * view components
     */
    private fun initClicks() {
        binding.imageViewTakePicture.setOnClickListener {
            takePicture()
        }
        binding.imageButtonDeletePicture.setOnClickListener {
            removeCurrentImage()
        }
        binding.buttonCreateTask.setOnClickListener {
            if(viewModel.taskTitle.value.isNullOrBlank() || viewModel.taskDescription.value.isNullOrBlank()) {
                val snackBar = Snackbar.make(binding.root, resources.getString(R.string.title_and_description_required), Snackbar.LENGTH_LONG)
                snackBar.show()
            } else {
                viewModel.createTask()
            }

        }
    }

    /**
     * Function that initialize the mutable data from viewModel
     */
    private fun initObservers() {
        viewModel.imagePath.observe(viewLifecycleOwner) {
            if(it != null) {
                val imageFile = File(it)
                binding.imageViewPreview.setImageURI(Uri.fromFile(imageFile))
                binding.imageViewPreview.visibility = View.VISIBLE
                binding.imageViewTakePicture.visibility = View.INVISIBLE
                binding.imageButtonDeletePicture.visibility = View.VISIBLE
            } else {
                binding.imageViewPreview.visibility = View.GONE
                binding.imageViewTakePicture.visibility = View.VISIBLE
                binding.imageButtonDeletePicture.visibility = View.GONE
            }
        }
        viewModel.createTaskResult.observe(viewLifecycleOwner) {
            it?.let {
                val snackBar = Snackbar.make(binding.root, resources.getString(R.string.task_created), Snackbar.LENGTH_LONG)
                snackBar.show()
                viewModel.createTaskResult.postValue(null)
                findNavController().popBackStack()
            }
        }
    }

    /**
     * Function that opens the app setting
     */
    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", "com.diegocalero.todoapplication", null)
        intent.data = uri
        openAppSettingsLauncher.launch(intent)
    }

    /**
     * Function that tries to open the device camera
     */
    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                ex.printStackTrace()
                null
            }
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(requireContext(), "com.diegocalero.todoapplication.fileprovider", it)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                takePictureLauncher.launch(takePictureIntent)
            }
        }
    }

    /**
     * Function that creates a snack bar when user doesn't have
     * camera permissions
     */
    private fun showSnackBarCameraPermissionNotAllowed() {
        val snackBar = Snackbar.make(binding.root, resources.getString(R.string.camera_permission_denied), Snackbar.LENGTH_LONG)
        snackBar.setAction(resources.getString(R.string.grant_permission)) {
            takePicture()
        }
        snackBar.show()
    }

    /**
     * Function that check if the camera permission is granted
     * if granted open the camera
     * else requests the permission
     */
    private fun takePicture() {
        val permissionStatus: Int = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        if (permissionStatus
            == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else if (permissionStatus == PackageManager.PERMISSION_DENIED) {
            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle(resources.getString(R.string.camera_permission_required))
                builder.setMessage(resources.getString(R.string.this_feature_requires_camera_permission))
                builder.setPositiveButton(resources.getString(R.string.configuration)) { _, _ ->
                    openAppSettings()
                }
                builder.setNegativeButton(resources.getString(R.string.cancel), null)
                builder.show()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    /**
     * Function that removes the current taken image
     */
    private fun removeCurrentImage() {
        currentPhotoPath = null
        viewModel.imagePath.value = null
    }
}