package fr.isen.prevot.androiderestaurant.Detail

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import fr.isen.prevot.androiderestaurant.BaseActivity
import fr.isen.prevot.androiderestaurant.ItemActivity
import fr.isen.prevot.androiderestaurant.R
import fr.isen.prevot.androiderestaurant.basket.Basket
import fr.isen.prevot.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.prevot.androiderestaurant.network.Dish
import kotlin.math.max

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var currentDish: Dish? = null
    private var itemCount = 1F
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentDish = intent.getSerializableExtra(ItemActivity.SELECTED_ITEM) as? Dish
        setupContent()
        observeClick()
        refreshShopButton()
    }

    private fun setupContent() {
        binding.mainCourse.text = currentDish?.name
        binding.ingredients.text = currentDish?.ingredients?.map { it.name }?.joinToString(", ")
        currentDish?.let {
            binding.viewPager.adapter = PhotoAdapter(this, it.images)
        }
    }

    private fun refreshShopButton() {
        currentDish?.let { dish ->
            val price: Float = dish.prices.first().price.toFloat()
            val total = price * itemCount
            binding.buttonShop.text = "${getString(R.string.total)} $total €"
            binding.quantity.text = itemCount.toInt().toString()
        }
    }

    private fun observeClick() {
        binding.buttonLess.setOnClickListener {
            itemCount = max(1f, itemCount - 1)
            refreshShopButton()
            //val count = itemCount - 1
            //if(count > 0) {
            //    itemCount = count
            //    refreshShopButton()
            //}
        }

        binding.buttonMore.setOnClickListener {
            itemCount++
            refreshShopButton()
        }

        binding.buttonShop.setOnClickListener {
            // Ajoute a notre panier
            currentDish?.let { dish ->
                val basket = Basket.getBasket(this)
                basket.addItem(dish, itemCount.toInt())
                basket.save(this)
                Snackbar.make(binding.root, R.string.addNotif, Snackbar.LENGTH_LONG).show()
                invalidateOptionsMenu()
                //Toast.makeText(this, R.string.itemAdded, Toast.LENGTH_LONG).show()
            }
        }
    }
}