package com.dicoding.appgithub.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.dicoding.appgithub.Database
import com.dicoding.appgithub.R
import com.dicoding.appgithub.adapter.DetailAdapter
import com.dicoding.appgithub.adapter.Result
import com.dicoding.appgithub.data.ResponsDetailUser
import com.dicoding.appgithub.databinding.ActivityDetailBinding
import com.dicoding.appgithub.shared.Theme
import com.dicoding.appgithub.ui.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()
    var DB: Database? = null

    private var theme: Theme? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        DB = Database(this)
        theme = Theme(this)

        val username = intent.getStringExtra("username") ?: ""
        val name = intent.getStringExtra("name") ?: ""

        val constraintLayout = findViewById<ConstraintLayout>(R.id.layoutDetail)

        if(theme!!.choose.equals("White")){
            constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            binding.nama.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.username.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.tab.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            binding.tab.setTabTextColors(ContextCompat.getColor(this, R.color.black), ContextCompat.getColor(this, R.color.black))
        }else{
            constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            binding.nama.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.username.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tab.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            binding.tab.setTabTextColors(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.white))
        }

        val tittleFragments = mutableListOf(
            getString(R.string.followers),getString(R.string.following),
        )

        var followers = ""
        var following = ""

        viewModel.resultDetailUser.observe(this){
            when (it) {
                is Result.Success<*> -> {
                    val user = it.data as ResponsDetailUser
                    binding.image.load(user.avatar_url){
                        transformations(CircleCropTransformation())
                    }
                    binding.nama.text = user.name
                    binding.username.text = user.login
                    println(user.followers)
                    followers += user.followers
                    println(followers)
                    following += user.following

                    TabLayoutMediator(binding.tab,binding.viewpager) { tab,posisi ->
                        if(tittleFragments[posisi] == "Followers"){
                            tab.text = tittleFragments[posisi] + "(" + followers + ")"
                        }else{
                            tab.text = tittleFragments[posisi] + "(" + following + ")"
                        }
                    }.attach()

                    binding.floatingButtonFav.setOnClickListener {
                        val check: Boolean = DB!!.insertFav(
                            user.login,
                            user.avatar_url
                        )
                        if (check) {
                            Toast.makeText(
                                this@DetailActivity,
                                "Berhasil menambahkan data fav",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                this@DetailActivity,
                                "Gagal menambahkan data fav",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }

                is Result.Error -> {
                    Toast.makeText(this, "it", Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
                    binding.progress.isVisible = it.isLoading
                }
            }
        }
        viewModel.getDetailUser(username)


        val fragments = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.FOLLOWERS),
            FollowFragment.newInstance(FollowFragment.FOLLOWIMG),
        )

        val adapter = DetailAdapter(this, fragments)
        binding.viewpager.adapter = adapter

        binding.tab.addOnTabSelectedListener(object  : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0){
                    viewModel.getFollowers(username)
                }else{
                    viewModel.getFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        viewModel.getFollowers(username)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}