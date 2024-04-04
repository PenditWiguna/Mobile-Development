package com.dicoding.appgithub.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.dicoding.appgithub.R
import com.dicoding.appgithub.adapter.Result
import com.dicoding.appgithub.adapter.UserAdapter
import com.dicoding.appgithub.data.ResponsUserGithub
import com.dicoding.appgithub.databinding.FragmentFollowBinding
import com.dicoding.appgithub.ui.DetailViewModel


class FollowFragment : Fragment() {

    private var binding: FragmentFollowBinding? = null
    private val adapter by lazy {
        UserAdapter{

        }
    }

    private val viewModel by activityViewModels<DetailViewModel>()
    var type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.rvFollow?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowFragment.adapter
        }

        when(type){
            FOLLOWERS -> {
                viewModel.resultFollwerslUser.observe(viewLifecycleOwner, this::managerResultFollow)
            }
            FOLLOWIMG -> {
                viewModel.resultFollwinglUser.observe(viewLifecycleOwner, this::managerResultFollow)
            }
        }


    }

    private fun managerResultFollow(state: Result){
        when (state) {
            is Result.Success<*> -> {
                adapter.setData(state.data as MutableList<ResponsUserGithub.Item>)
            }

            is Result.Error -> {
                Toast.makeText(requireActivity(), "state", Toast.LENGTH_SHORT).show()
            }

            is Result.Loading -> {
                binding?.progressBar?.isVisible = state.isLoading
            }
        }

    }

    companion object {

        const val FOLLOWIMG = 100
        const val FOLLOWERS = 101
        fun newInstance(type: Int) = FollowFragment()
            .apply {
                this.type = type
            }

    }
}