package com.dicoding.appgithub.ui

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.appgithub.ListFavActivity
import com.dicoding.appgithub.R
import com.dicoding.appgithub.RecyclerViewOnClick
import com.dicoding.appgithub.ThemeActivity
import com.dicoding.appgithub.adapter.Result
import com.dicoding.appgithub.adapter.UserAdapter
import com.dicoding.appgithub.data.Item
import com.dicoding.appgithub.data.ResponsUserGithub
import com.dicoding.appgithub.databinding.ActivityMainBinding
import com.dicoding.appgithub.detail.DetailActivity
import com.dicoding.appgithub.shared.Theme

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter {
            Intent(this, DetailActivity::class.java).apply {
                putExtra("username", it.login)
                startActivity(this)
            }
        }
    }

    private var theme: Theme? = null

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        theme = Theme(this)

        val constraintLayout = findViewById<ConstraintLayout>(R.id.activityMain)

        if(theme!!.choose.equals("White")){
            constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            val searchText = binding.searchView.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
            searchText.setTextColor(ContextCompat.getColor(this, R.color.black))
            val searchIcon = binding.searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
            searchIcon.setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.SRC_ATOP)
        }else{
            constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            val searchText = binding.searchView.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
            searchText.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getUser(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })

        binding.buttonFav.setOnClickListener {
            Intent(this, ListFavActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.buttonTheme.setOnClickListener {
            Intent(this, ThemeActivity::class.java).apply {
                startActivity(this)
            }
        }

        viewModel.resultUser.observe(this) {
            when (it) {
                is Result.Success<*> -> {
                    adapter.setData(it.data as MutableList<ResponsUserGithub.Item>)
                }

                is Result.Error -> {
                    Toast.makeText(this, "it", Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }


        viewModel.getUser()


    }
}