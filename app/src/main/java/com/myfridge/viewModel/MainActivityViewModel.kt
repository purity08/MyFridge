package com.myfridge.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myfridge.storage.Repositories
import com.myfridge.storage.entity.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    val account by lazy {
        Repositories.accountRepository.get()
    }

    fun insertAccount(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        Repositories.accountRepository.insert(account)
    }
    fun updateAccount(account: Account) = viewModelScope.launch(Dispatchers.IO){
        Repositories.accountRepository.update(account)
    }
}