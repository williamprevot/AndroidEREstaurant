package fr.isen.janowski.androiderestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import fr.isen.janowski.androiderestaurant.databinding.CellDishBinding
import fr.isen.janowski.androiderestaurant.network.Dish

class DishesAdapter(val items: List<Dish>,
                    val itemClickListener: (Dish) -> Unit)
    : RecyclerView.Adapter<DishesAdapter.DishViewHolder>() {

    class DishViewHolder(binding: CellDishBinding): RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.title
        val layout: ConstraintLayout = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val binding = CellDishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DishViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.name
        holder.layout.setOnClickListener {
            itemClickListener.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}