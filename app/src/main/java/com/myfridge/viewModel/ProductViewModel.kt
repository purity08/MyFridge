package com.myfridge.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myfridge.storage.Repositories
import com.myfridge.storage.entity.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    val products by lazy {
        Repositories.productsRepository.getAll()
    }

    fun insertProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        Repositories.productsRepository.insert(product)
    }

    fun updateProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        Repositories.productsRepository.update(product)
    }
}