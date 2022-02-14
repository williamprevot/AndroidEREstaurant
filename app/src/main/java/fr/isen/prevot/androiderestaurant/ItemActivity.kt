package fr.isen.prevot.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.prevot.androiderestaurant.Detail.DetailActivity
import fr.isen.prevot.androiderestaurant.databinding.ActivityItemBinding
import fr.isen.prevot.androiderestaurant.network.Dish
import fr.isen.prevot.androiderestaurant.network.MenuResult
import fr.isen.prevot.androiderestaurant.network.NetworkConstants
import org.json.JSONObject

enum class LunchType {
    STARTER, MAIN, FINISH;

    companion object {
        fun getResString(type: LunchType): Int {
            return return when(type) {
                STARTER -> R.string.staters
                MAIN -> R.string.main
                FINISH -> R.string.end
            }
        }

        fun getCategoryTitle(type: LunchType): String {
           return return when(type) {
                STARTER -> "Entrées"
                MAIN -> "Plats"
                FINISH -> "Desserts"
            }
        }
    }
}

class ItemActivity : BaseActivity() {
    lateinit var binding: ActivityItemBinding
    lateinit var currentCategory: LunchType
    // val fakeItems= listOf("plat 1","plat 2","plat 3","plat 4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
       // val actionBar = supportActionBar
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //actionBar!!.title = "Category"
        currentCategory = intent.getSerializableExtra(HomeActivity.CategoryType) as? LunchType ?: LunchType.STARTER
        setupTitle()
        makeRequest()

        //val item: LunchType = intent.getSerializableExtra(HomeActivity.CategoryType) as LunchType ?: LunchType.STARTER
        //binding.textView.setText("Contenu du get extra: " +item)
        Log.d("lifecycle", "ItemList onCreate")
    }
    override fun onDestroy() {
        Log.d("life cycle", "ItemList onDestroy")
        super.onDestroy()
    }
    override fun onRestart() {
        Log.d("life cycle", "ItemList onRestart")
        super.onRestart()
    }
    override fun onResume() {
        Log.d("life cycle", "ItemList onResume")
        super.onResume()
    }
    override fun onStart() {
        Log.d("life cycle", "ItemList onStart")
        super.onStart()
    }
    override fun onStop() {
        Log.d("life cycle", "ItemList onStop")
        super.onStop()
    }




    private fun setupTitle() {
        binding.mainCourse.text = getString(LunchType.getResString(currentCategory))
    }

    private fun loadList(items: List<Dish>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ItemAdapter(items) { selectedItem ->
            showDetail(selectedItem)
        }
        binding.recyclerView.adapter = adapter
    }
    private fun makeRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstants.BASE_URL + NetworkConstants.MENU
        val paramters = JSONObject()//dico de type JSON
        paramters.put(NetworkConstants.KEY_SHOP, NetworkConstants.SHOP)

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            paramters,
            {
                //retour si ca s'est bien passé
                Log.d("Volley", "${it.toString(2)}")
                parseResult(it.toString())
            },
            {
                //retour si ca s'est mal passé( ex :pas de co , 404 error)
                Log.d("Volley error", "$it")
            }
        )

        queue.add(request)
    }

    private fun showDetail(item: Dish) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(ItemActivity.SELECTED_ITEM, item)
        startActivity(intent)
    }
    private fun parseResult(response: String) {
        val result = GsonBuilder().create().fromJson(response, MenuResult::class.java)
        val items = result.data.firstOrNull {
            it.name == LunchType.getCategoryTitle(currentCategory)
        }?.items
        //if (items != null) {
        //    setupList(items)
        //}
        items?.let {
            loadList(it)
        }
    }

    companion object {
        const val SELECTED_ITEM = "SELECTED_ITEM"
    }
}