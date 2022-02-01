package fr.isen.janowski.androiderestaurant.basket

import fr.isen.janowski.androiderestaurant.network.Dish
import java.io.Serializable

class Basket(val items: MutableList<BasketItem>): Serializable {
    fun addItem(item: Dish, quantity: Int) {
        // A vous de coder
    }

    fun save() {
        // A vous de coder
    }

    companion object {
        fun getBasket(): Basket {
            return Basket(mutableListOf())
        }
    }
}

class BasketItem(val dish: Dish, var quantity: Int): Serializable {}