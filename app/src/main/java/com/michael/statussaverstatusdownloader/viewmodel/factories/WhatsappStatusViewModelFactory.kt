package com.michael.statussaverstatusdownloader.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.michael.statussaverstatusdownloader.model.repository.WhatsappStatusRepository
import com.michael.statussaverstatusdownloader.viewmodel.WhatsappStatusViewModel

class WhatsappStatusViewModelFactory(private val repository: WhatsappStatusRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return WhatsappStatusViewModel(repository) as T
    }
}