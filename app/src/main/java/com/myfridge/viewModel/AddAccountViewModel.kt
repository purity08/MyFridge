package com.myfridge.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myfridge.storage.Repositories
import com.myfridge.storage.entity.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddAccountViewModel: ViewModel() {

    fun insert(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        Repositories.accountRepository.insert(account)
    }

    fun get(): LiveData<Account> = Repositories.accountRepository.get()
}