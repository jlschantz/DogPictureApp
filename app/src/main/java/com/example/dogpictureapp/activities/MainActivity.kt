package com.example.dogpictureapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogpictureapp.R
import com.example.dogpictureapp.adapters.DogPictureListAdapter
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

            setupMenu()
        }
    }

    private fun setupMenu(){
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)

                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem?.actionView as SearchView

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

    private fun searchForType(type: String){
        lifecycleScope.launch {
            dogViewModel.getDogPicturesByType(type).collect { list ->
                withContext(Dispatchers.Main) {
                    dogPictureListAdapter.submitList(list)

                    binding?.apply {
                        pictureRecyclerView.visibility = if (list.isNotEmpty()) VISIBLE else GONE
                        textRecyclerView.visibility = if (list.isNotEmpty()) GONE else VISIBLE
                    }
                }
            }
        }
    }
}