package fr.isen.janowski.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SimpleAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.janowski.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.janowski.androiderestaurant.network.NetworkConstants
import org.json.JSONObject

enum class LunchType {
    STARTER, MAIN, FINISH;

    companion object {
        fun getResString(type: LunchType): Int {
            return when(type) {
                STARTER -> R.string.staters
                MAIN -> R.string.main
                FINISH -> R.string.finish
            }
        }
    }
}

class CategoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryBinding
    lateinit var currentCategory: LunchType

    val fakeItems = listOf("plat1", "plat2", "plat3", "plat4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentCategory = intent.getSerializableExtra(HomeActivity.CategoryType) as? LunchType ?: LunchType.STARTER
        setupTitle()
        makeRequest()
        loadList()
    }

    private fun makeRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstants.BASE_URL + NetworkConstants.MENU
        val paramters = JSONObject()
        paramters.put(NetworkConstants.KEY_SHOP, NetworkConstants.SHOP)

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            paramters,
            {
                Log.d("volley", "${it.toString(2)}")
            },
            {
                Log.d("Volley error", "$it")
            }
        )

        queue.add(request)
    }

    private fun setupTitle() {
        binding.title.text = getString(LunchType.getResString(currentCategory))
    }

    private fun loadList() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = DishesAdapter(fakeItems) { selectedItem ->
            showDetail(selectedItem)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun showDetail(item: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(CategoryActivity.SELECTED_ITEM, item)
        startActivity(intent)
    }

    companion object {
        const val SELECTED_ITEM = "SELECTED_ITEM"
    }
}