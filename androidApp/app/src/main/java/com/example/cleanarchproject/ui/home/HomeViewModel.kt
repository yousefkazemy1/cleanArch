package com.example.cleanarchproject.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contentdomain.usecase.GetHomePostsUseCase
import com.example.core.model.PostItemUI
import com.example.core.model.Result
import com.example.core.model.mapToPostItemUIs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: GetHomePostsUseCase,
) : ViewModel() {
    val videos = MutableStateFlow<List<PostItemUI>>(emptyList())

    fun getHomePosts() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase().let { result ->
                if (result is Result.Success) {
                    videos.value = result.data.mapToPostItemUIs()
                }
            }
        }
    }
}