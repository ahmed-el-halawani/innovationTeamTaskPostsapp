package com.halawany.innovationteamtaskpostsapp.presentation.post.details

import androidx.lifecycle.ViewModel
import com.halawany.innovationteamtaskpostsapp.domain.usecase.GetPostDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(val getPostDetailsUseCase: GetPostDetailsUseCase) : ViewModel()