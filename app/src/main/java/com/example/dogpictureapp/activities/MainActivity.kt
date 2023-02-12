package com.example.dogpictureapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogpictureapp.R
import com.example.dogpictureapp.adapters.DogPictureListAdapter
import com.example.dogpictureapp.api.ResultState.*
import com.example.dogpictureapp.databinding.ActivityMainBinding
import com.example.dogpictureapp.viewmodels.DogViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val dogViewModel: DogViewModel by viewModels()
    private val dogPictureListAdapter = DogPictureListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        title = getString(R.string.app_name)

        binding?.apply {
            pictureRecyclerView.apply {
                adapter = dogPictureListAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
                setHasFixedSize(true)
            }
        }

        setupMenu()
    }

    private fun setupMenu() {
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)

                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem?.actionView as SearchView

                searchView.queryHint = getString(R.string.search_by_dog_breed)
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if(!query.isNullOrBlank()){
                            searchForType(query)
                            searchView.clearFocus()
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        })
    }

    private fun searchForType(type: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            dogViewModel.getDogPicturesByType(type).collect { resource ->
                withContext(Dispatchers.Main) {
                    binding?.apply {
                        when (resource) {
                            is Success -> {
                                progressRecyclerView.visibility = GONE
                                resource.data?.let { list ->
                                    dogPictureListAdapter.submitList(list)

                                    pictureRecyclerView.visibility = if (list.isNotEmpty()) VISIBLE else GONE
                                    textRecyclerView.visibility = if (list.isNotEmpty()) GONE else VISIBLE
                                } ?: Toast.makeText(this@MainActivity, getString(R.string.an_error_has_occurred), Toast.LENGTH_SHORT).show()
                            }
                            is Error -> {
                                progressRecyclerView.visibility = GONE
                                Toast.makeText(this@MainActivity, resource.error ?: getString(R.string.an_error_has_occurred), Toast.LENGTH_SHORT).show()
                            }
                            is Loading -> {
                                progressRecyclerView.visibility = VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }
}