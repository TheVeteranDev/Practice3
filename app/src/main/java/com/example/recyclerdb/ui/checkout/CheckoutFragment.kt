package com.example.recyclerdb.ui.checkout

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.recyclerdb.R
import com.example.recyclerdb.databases.SqLiteDatabase
import com.example.recyclerdb.databinding.FragmentCheckoutBinding
import androidx.lifecycle.lifecycleScope
import com.example.recyclerdb.checkout.CheckoutAdapter
import com.example.recyclerdb.products.Product
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import androidx.room.util.newStringBuilder

class CheckoutFragment : Fragment(R.layout.fragment_checkout) {
    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val context = requireContext()

        val db = SqLiteDatabase.Companion.getDatabase(context)
        val productDao = db.productDao()

        val listView: ListView = root.findViewById(R.id.list_view)
        var products: List<Product> = listOf()

        lifecycleScope.launch {
            productDao.listByCheckoutId(1L).collect { p ->
                products = p
                listView.adapter = CheckoutAdapter(context, products)
            }
        }

        val button: Button = root.findViewById(R.id.email_button)
        button.setOnClickListener {
            if (sendEmail(context, products)) {
                lifecycleScope.launch {
                    products.forEach { p ->
                        productDao.updateCheckoutId(0L, p.id)
                    }
                }
            }
        }

        return root
    }

    fun sendEmail(context: Context, products: List<Product>): Boolean {
        val emailUri = Uri.parse("mailto:sweng888mobileapps@gmail.com")
        val emailIntent = Intent(Intent.ACTION_SEND, emailUri).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("sweng888mobileapps@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "SWENG 888 Mobile Apps ---Your order is on its way!")
            var emailBody = "The following items have been selected from the app for your order:\n\n"
            products.forEach { p ->
                emailBody += "Name: ${p.name}\nDescription: ${p.description}\nSeller: ${p.seller}\nPrice: $${String.format("%.2f",p.price)}\n\n"
            }
            putExtra(Intent.EXTRA_TEXT, emailBody)
        }

        try {
            startActivity(Intent.createChooser(emailIntent, "Choose an email client"))
            return true
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No email clients installed.", Toast.LENGTH_SHORT).show()
            return false
        }
    }
}