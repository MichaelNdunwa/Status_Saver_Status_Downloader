package com.michael.statussaverstatusdownloader.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.michael.statussaverstatusdownloader.model.repository.SavedStatusRepository
import com.michael.statussaverstatusdownloader.viewmodel.SavedStatusViewModel

class SavedStatusViewModelFactory(private val repository: SavedStatusRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return SavedStatusViewModel(repository) as T
    }
}