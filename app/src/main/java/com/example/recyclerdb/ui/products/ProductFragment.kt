package com.example.recyclerdb.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerdb.R
import com.example.recyclerdb.databases.SqLiteDatabase
import com.example.recyclerdb.databinding.FragmentProductBinding
import com.example.recyclerdb.products.Product
import com.example.recyclerdb.products.ProductAdapter
import kotlinx.coroutines.launch

class ProductFragment : Fragment(R.layout.fragment_product) {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val db = SqLiteDatabase.Companion.getDatabase(requireContext())
        val productDao = db.productDao()
        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            val newProducts = listOf(
                Product(
                    id = 0,
                    name = "Macallan 12 Year Double Cask Single Malt Scotch",
                    description = "Scotland- The Macallan Double Cask 12 yr. has been crafted to deliver a new style of Macallan. One that is the perfect balance of 100% Sherry seasoned American and European Oak, a great marriage of 2 wood types. Smooth, Sweet and balanced.",
                    seller = "Macallan",
                    price = 72.99,
                    picture = R.drawable.macallan_12,
                    checkoutId = 0
                ),
                Product(
                    id = 0,
                    name = "Woodford Reserve Burbon",
                    description = "USA - Kentucky - 45.2% - Woodford Reserve Kentucky Straight Bourbon Whiskey has a perfectly balanced taste with more than 200 detectable flavor notes. This bourbon whiskey delivers aromas of rich dried fruit with a smooth, satisfying finish that's perfect for crafting cocktails.",
                    seller = "Macallan",
                    price = 58.99,
                    picture = R.drawable.woodford_reserve,
                    checkoutId = 0
                ),
                Product(
                    id = 0,
                    name = "Elijah Craig Burbon",
                    description = "United States - Kentucky - 47.0%-Bottled exclusively from a dumping of 70 barrels or less, all drawn from the middle to upper floors of the traditional metal clad rickhouses. It is the original Small Batch Bourbon, having been made available even before the term was invented.",
                    seller = "Elijah Craig",
                    price = 61.99,
                    picture = R.drawable.elijah_craig_burbon,
                    checkoutId = 0
                ),
                Product(
                    id = 0,
                    name = "Isle of Skye 21 Year Scotch Whiskey",
                    description = "Scotland - Aged for over 21 years in rich oak casks. Aromas of shelled walnuts, dried banana and brown sugar fill the nose. A meticulously blended premium malt whisky, with a full-bodied mouthfeel and tastes of soft peat smoke and tropical fruits.",
                    seller = "Isle of Skye",
                    price = 89.99,
                    picture = R.drawable.isle_of_skey_21,
                    checkoutId = 0
                ),
                Product(
                    id = 0,
                    name = "Johnnie Walker Blue Label Blended Scotch",
                    description = "Scotland- 40% -Unlock the ultimate whisky experience with Johnnie Walker Blue Label, a velvety smooth and vibrant Scotch crafted using rare hand-selected whiskies from the four corners of Scotland. Every sip of Johnnie Walker Blue Label is a journey through a rich and complex flavor.",
                    seller = "Johnnie Walker",
                    price = 174.99,
                    picture = R.drawable.johnnie_walker_blue_label,
                    checkoutId = 0
                ),
                Product(
                    id = 0,
                    name = "Blanton's Burboun",
                    description = "Kentucky- Introduced in 1984 and one of the first specialty Bourbons. A deep, satisfying nose of nutmeg and spices. Powerful dry vanilla notes in harmony with hints of honey amid strong caramel and corn. A medium finish composed of returning corn and nutmeg flavors.",
                    seller = "Blanton's",
                    price = 59.99,
                    picture = R.drawable.blantons_burbon,
                    checkoutId = 0
                )
            )

            newProducts.forEach { product ->
                productDao.create(product)
            }
        }

        lifecycleScope.launch {
            productDao.list().collect { products ->
                recyclerView.adapter = ProductAdapter(products, productDao, lifecycleScope)
            }
        }


        return root
    }
}