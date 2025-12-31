package com.halawany.innovationteamtaskpostsapp.presentation.post.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.halawany.innovationteamtaskpostsapp.core.DateTimeUtils
import com.halawany.innovationteamtaskpostsapp.core.UiState
import com.halawany.innovationteamtaskpostsapp.databinding.FragmentDetailsBinding
import com.halawany.innovationteamtaskpostsapp.presentation.core.ErrorMappers.toLocalizedMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private val binding: FragmentDetailsBinding by lazy { FragmentDetailsBinding.inflate(layoutInflater) }
    private val viewModel: PostDetailsViewModel by viewModels()
    val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener { findNavController().navigateUp() }
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.getPostDetailsUseCase(args.postId).collectLatest { post ->
                when (post) {
                    is UiState.Error -> Toast.makeText(context, post.message.toLocalizedMessage(requireContext()), Toast.LENGTH_SHORT).show()

                    is UiState.Success -> {
                        post.data.let { it ->
                            binding.tvTitle.text = it.title
                            binding.tvBody.text = it.title + "\n\n" + (it.title.repeat(5))
                            binding.tvSourceName.text = it.author

                            it.publishedAt?.let { binding.tvDate.text = DateTimeUtils.formatShort(it) } ?: "Unknown Date"

                            Glide.with(this@DetailsFragment).load(it.imageUrl).into(binding.ivImage)
                            it.source?.let { sourceUrl -> Glide.with(this@DetailsFragment).load(sourceUrl).into(binding.ivSourceLogo) }
                        }
                    }

                    else -> {}
                }
            }
        }
    }


}