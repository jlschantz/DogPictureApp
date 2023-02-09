package com.example.dogpictureapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogpictureapp.R
import com.example.dogpictureapp.adapters.DogPictureListAdapter
import com.example.dogpictureapp.databinding.ActivityMainBinding
import com.example.dogpictureapp.viewmodels.DogViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val dogViewModel: DogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        title = getString(R.string.app_name)

        val dogPictureListAdapter = DogPictureListAdapter()

        binding?.apply {
            pictureRecyclerView.apply {
                adapter = dogPictureListAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
                setHasFixedSize(true)
            }

            lifecycleScope.launchWhenStarted {
                dogViewModel.picturesList.collect { list ->
                    dogPictureListAdapter.submitList(list)
                }
            }
        }
    }
}