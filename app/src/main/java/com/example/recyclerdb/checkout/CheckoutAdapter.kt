package com.example.recyclerdb.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.recyclerdb.products.Product
import com.example.recyclerdb.R
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity

class CheckoutAdapter(
    private val context: Context,
    private val movies: List<Product>
): BaseAdapter()  {
    override fun getCount(): Int {
        return movies.size
    }

    override fun getItem(i: Int): Product {
        return movies[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(
        i: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.checkout_item_holder, parent, false)

        val name: TextView = view.findViewById(R.id.checkout_name)
        val price: TextView = view.findViewById(R.id.checkout_price)

        val product = getItem(i)

        name.text = product.name
        price.text = "$ ${String.format("%.2f", product.price)}"

        return view
    }

}