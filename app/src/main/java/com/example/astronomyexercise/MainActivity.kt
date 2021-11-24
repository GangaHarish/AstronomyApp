package com.example.astronomyexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.astronomyexercise.data.models.NasaItem
import com.example.astronomyexercise.databinding.ActivityMainBinding
import com.example.astronomyexercise.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeViewModel()
    }

    private fun initializeViewModel() {
        viewModel.getAllImages()
        /*If data is empty call api else fetch it from db*/

        viewModel.loading.observe(this, { loadingValue ->
            if (loadingValue) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.tvHeader.visibility = View.VISIBLE
                binding.tvDescription.visibility = View.VISIBLE
                binding.ivImage.visibility = View.VISIBLE
            }
        })
        viewModel.nasaSuccess.observe(this, { response ->
            binding.tvHeader.text = response.data?.title
            binding.tvDescription.text = response.data?.explanation
            Glide.with(this).load(response.data?.url).into(binding.ivImage)
        })
        viewModel.allUsers.observe(this, { usersList ->
            if (usersList.isNullOrEmpty()) {
                viewModel.callApi()
            } else {
                setUi(usersList[0])
            }
        })
        viewModel.nasaError.observe(this, {
            showAlertDialog()
        })

    }

    private fun showAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage(R.string.dialog_description)
            .setPositiveButton("Ok") { dialog, id ->
                finish()
            }
            .setCancelable(false)

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle(R.string.dialog_header)
        // show alert dialog
        alert.show()
    }

    private fun setUi(item: NasaItem) {
        if(!item.name.isNullOrEmpty()) {
            binding.tvHeader.visibility = View.VISIBLE
            binding.tvDescription.visibility = View.VISIBLE
            binding.ivImage.visibility = View.VISIBLE
            binding.tvHeader.text = item.name
            binding.tvDescription.text = item.description
            Glide.with(this).load(item.url).into(binding.ivImage)
        }
    }
}