package fr.isen.janowski.androiderestaurant.basket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.janowski.androiderestaurant.databinding.ActivityBasketBinding

class BasketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBasketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadList()
    }

    private fun loadList() {
        val items = Basket.getBasket(this).items
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = BasketAdapter(items)
    }
}