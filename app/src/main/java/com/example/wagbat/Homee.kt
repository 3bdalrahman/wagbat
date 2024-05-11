import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.example.wagbat.R
import com.example.wagbat.adapter
import com.example.wagbat.item
import com.example.wagbat.recy_dishes
import java.util.Locale

class Homee : Fragment() {
    private lateinit var recycler: RecyclerView
    private lateinit var product_list: ArrayList<item>
    private lateinit var img_id: Array<Int>
    private lateinit var search: SearchView
    private lateinit var adapter: adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_homee, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        recycler = view.findViewById(R.id.catagory)
        search = view.findViewById(R.id.searchbar)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        product_list = ArrayList()
        adapter = adapter(product_list)
        recycler.adapter = adapter

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        // Set item click listener for the adapter
        adapter.onItemClick = { clickedItem ->
            val intent = Intent(requireActivity(), recy_dishes::class.java)
            intent.putExtra("name", clickedItem)
            startActivity(intent)
        }
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filterList = ArrayList<item>()
            for (i in product_list) {
                if (i.resturant_nam.lowercase(Locale.ROOT).contains(query)) {
                    filterList.add(i)
                }
            }
            if (filterList.isEmpty()) {
                showToast("No Data Found")
            } else {
                adapter.setfilterdlist(filterList)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        img_id = arrayOf(
            R.drawable.mac,
            R.drawable.king,
            R.drawable.bufalo,
            R.drawable.pizza_hut,
            R.drawable.roma,
            R.drawable.kfc,
            R.drawable.mori,
            R.drawable.shawrma,
            R.drawable.koshary,
            R.drawable.alkhdawy,
            R.drawable.desoky,
            R.drawable.abo_tarek
        )
        val names = arrayOf(
            "MacDonald's", "Burger King", "Bufallo Burger", "Pizza Hut", "Roma Pizza",
            "KFC", "Mori Sushi", "Shawrma El-reem", "El-tahrer", "Elkhedawy", "Desoky & Soda", "Abo-Tarek"
        )

        for (i in img_id.indices) {
            val product = item(img_id[i], names[i])
            product_list.add(product)
        }
        adapter.notifyDataSetChanged()
    }
}
