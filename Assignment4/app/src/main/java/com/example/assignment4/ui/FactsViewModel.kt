package com.example.assignment4.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.assignment4.data.FactsRepository
import com.example.assignment4.data.FunFact
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FactsViewModel(private val repo: FactsRepository) : ViewModel() {

    val uiState: StateFlow<UiState> =
        repo.facts()
            .map { UiState(facts = it, loading = false, error = null) }
            .stateIn(viewModelScope, SharingStarted.Lazily, UiState())

    fun fetchNew() {
        viewModelScope.launch {
            try {
                repo.fetchAndSave()
            } catch (e: Exception) {
                // in a real app we'd expose this; skipping to keep code short
                e.printStackTrace()
            }
        }
    }

    data class UiState(
        val facts: List<FunFact> = emptyList(),
        val loading: Boolean = false,
        val error: String? = null
    )

    class Factory(private val repo: FactsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FactsViewModel(repo) as T
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repo.clearAll()
        }
    }

}