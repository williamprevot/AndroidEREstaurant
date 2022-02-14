package fr.isen.prevot.androiderestaurant

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.prevot.androiderestaurant.databinding.CellDishBinding
import fr.isen.prevot.androiderestaurant.network.Dish

class ItemAdapter(val items: List<Dish>,
                  val itemClickListener: (Dish) -> Unit)
    : RecyclerView.Adapter<ItemAdapter.DishViewHolder>() {
    //On cree un pont de discussion retour entre activité et adapter
    class DishViewHolder(binding: CellDishBinding): RecyclerView.ViewHolder(binding.root) {
        val price: TextView = binding.price
        val image: ImageView = binding.imageView
        //mapper le contenu de notre cellule,permet d'avoir une classe associé
        val title: TextView = binding.mainCourse
        //On doit recuperer le root de la cell
        val layout: CardView = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val binding = CellDishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DishViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        //appelé au moment de l'affichage de la cellule
        //p1:position sur laquelle on se trouve
        val item = items[position]
        holder.title.text = item.name
        holder.price.text = "${item.prices.first().price} €"
        Picasso
            .get()
            .load(item.getThumbnailURL())
            .placeholder(R.drawable.good_food)
            .into(holder.image)
        holder.layout.setOnClickListener {
            Log.d("debug","clique sur la cell"+position)
            itemClickListener.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}