package com.myfridge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myfridge.R
import com.myfridge.storage.entity.Product
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_product.view.*

class ProductsAdapter(
    var productsList: List<Product>,
    private val onClickAddProduct: (Product) -> Unit,
    private val onClickRemoveProduct: (Product) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>(), DataAdapter<List<Product>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = when (viewType) {
            TYPE -> inflater.inflate(R.layout.item_product, parent, false)

            else -> (throw  IllegalArgumentException("Invalid view type"))
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productsList[position]
        holder.bind(product, onClickAddProduct, onClickRemoveProduct)
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(
            product: Product,
            onClickAddProduct: (Product) -> Unit,
            onClickRemoveProduct: (Product) -> Unit
        ) {
            containerView.product_name_textView.text = product.name
            containerView.product_count_textView.text = product.count.toString()

            containerView.product_add_button.setOnClickListener {
                product.count++
                onClickAddProduct(product)
            }
            containerView.product_remove_button.setOnClickListener {
                if (product.count >= 1) {
                    product.count--
                    if (product.count <= 1) {
                        containerView.product_count_textView.text = "1"
                    }
                }
                onClickRemoveProduct(product)
            }
        }
    }

    companion object {
        const val TYPE = 0
    }

    override fun setData(data: List<Product>) {
        productsList = data
        notifyDataSetChanged()
    }
}