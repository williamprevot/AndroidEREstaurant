package fr.isen.prevot.androiderestaurant.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Price(@SerializedName("price") val price: String): Serializable {
}