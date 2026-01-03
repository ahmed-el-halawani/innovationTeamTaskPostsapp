package com.halawany.innovationteamtaskpostsapp.presentation.post.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.halawany.innovationteamtaskpostsapp.core.UiState
import com.halawany.innovationteamtaskpostsapp.databinding.FragmentListBinding
import com.halawany.innovationteamtaskpostsapp.presentation.core.ErrorMappers.toLocalizedMessage
import com.halawany.innovationteamtaskpostsapp.presentation.post.news.adapter.PostsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment() {
    private val TAG = "ListFragment"

    private val binding: FragmentListBinding by lazy {
        FragmentListBinding.inflate(layoutInflater)
    }

    private val viewModel: PostListViewModel by viewModels()
    private lateinit var adapter: PostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        binding.swipeRefresh.setOnRefreshListener { viewModel.syncNewsFromBackend() }
    }

    private fun setupRecyclerView() {
        adapter = PostsAdapter { post ->
            val action = ListFragmentDirections.actionListFragmentToDetailsFragment(post.id)
            findNavController().navigate(action)
        }
        binding.rvPosts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPosts.adapter = adapter
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiPostsState.collectLatest { pagingDataState ->
                binding.swipeRefresh.isRefreshing = false
                adapter.submitData(pagingDataState)
            }
        }

        viewModel.syncDataFromNetworkState.observe(requireActivity()) {
            when (it) {
                is UiState.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    Toast.makeText(context, it.message.toLocalizedMessage(requireContext()), Toast.LENGTH_LONG).show()
                    binding.pbSyncData.isVisible = false
                    binding.ivSyncedCheck.isVisible = true
                }

                UiState.Loading -> {
                    binding.pbSyncData.isVisible = true
                    binding.ivSyncedCheck.isVisible = false
                };
                is UiState.Success<*>, UiState.Idle -> {
                    binding.swipeRefresh.isRefreshing = false
                    binding.pbSyncData.isVisible = false
                    binding.ivSyncedCheck.isVisible = true
                }
            }
        }

    }

}