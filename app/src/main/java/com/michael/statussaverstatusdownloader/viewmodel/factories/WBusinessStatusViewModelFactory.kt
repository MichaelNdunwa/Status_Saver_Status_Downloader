package com.michael.statussaverstatusdownloader.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.michael.statussaverstatusdownloader.model.repository.WBusinessStatusRepository
import com.michael.statussaverstatusdownloader.model.repository.WhatsappStatusRepository
import com.michael.statussaverstatusdownloader.viewmodel.WBusinessStatusViewModel
import com.michael.statussaverstatusdownloader.viewmodel.WhatsappStatusViewModel

class WBusinessStatusViewModelFactory(private val repository: WBusinessStatusRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return WBusinessStatusViewModel(repository) as T
    }
}