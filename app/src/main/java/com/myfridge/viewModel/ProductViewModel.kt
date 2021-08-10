package com.myfridge.viewModel

import androidx.lifecycle.LiveData
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

    fun getAllByName(name: String): LiveData<List<Product>> {
       return Repositories.productsRepository.getAllByName(name)
    }

    fun insertProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        Repositories.productsRepository.insert(product)
    }

    fun updateProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        Repositories.productsRepository.update(product)
    }

    fun removeProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        Repositories.productsRepository.delete(product)
    }
}