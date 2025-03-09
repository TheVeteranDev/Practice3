package com.example.recyclerdb.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerdb.R
import kotlinx.coroutines.launch
import kotlin.text.format

class ProductAdapter(
    private val products: List<Product>,
    private val productDao: ProductDoa,
    private val lifeCycleScope: LifecycleCoroutineScope
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>()  {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val description: TextView = view.findViewById(R.id.description)
        val seller: TextView = view.findViewById(R.id.seller)
        val price: TextView = view.findViewById(R.id.price)
        val picture: ImageView = view.findViewById(R.id.picture)
        val button: Button = view.findViewById(R.id.cart_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_holder, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.name.text = product.name
        holder.description.text = product.description
        holder.seller.text = product.seller
        holder.price.text = "$ ${String.format("%.2f", product.price)}"

        Glide.with(holder.itemView.context)
            .load(product.picture)
            .placeholder(R.drawable.macallan_12) // Show placeholder while loading
            .error(R.drawable.macallan_12) // Show default if URL fails
            .into(holder.picture)

        if (product.checkoutId == 0L) {
            holder.button.text = "Add to Cart"
            holder.button.setBackgroundColor(holder.itemView.context.getColor(R.color.purple_700))
        } else {
            holder.button.text = "Remove from Cart"
            holder.button.setBackgroundColor(holder.itemView.context.getColor(R.color.black))
        }

        holder.button.setOnClickListener {
            if (product.checkoutId == 0L) {
                lifeCycleScope.launch {
                    productDao.updateCheckoutId(1L, product.id)
                }
            } else {
                lifeCycleScope.launch {
                    productDao.updateCheckoutId(0L, product.id)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

}