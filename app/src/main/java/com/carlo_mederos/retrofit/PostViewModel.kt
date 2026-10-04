package com.carlo_mederos.retrofit

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    var postsState by mutableStateOf<List<DataPost>>(value = emptyList())
        private set

    var isLoading by mutableStateOf(value = false)
        private set

    var errorMessage by mutableStateOf<String?>(value = null)
        private set

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                postsState = ApiService.instance.getPosts()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                errorMessage = "No se pudieron cargar los posts. Comprueba la conexión e inténtalo de nuevo."
            } finally {
                isLoading = false
            }
        }
    }

}
