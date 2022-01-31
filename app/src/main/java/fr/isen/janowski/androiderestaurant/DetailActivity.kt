package fr.isen.janowski.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.janowski.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.janowski.androiderestaurant.network.Dish

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var currentDish: Dish? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentDish = intent.getSerializableExtra(CategoryActivity.SELECTED_ITEM) as? Dish
        setupTitle()
    }

    private fun setupTitle() {
        binding.title.text = currentDish?.name
    }
}