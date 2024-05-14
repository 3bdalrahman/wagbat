import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wagbat.R
import com.example.wagbat.dishs

class MyAdapter(private val itemList: ArrayList<dishs>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    var onItemClick: ((dishs) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_dishs, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val leftPosition = position * 2
        val rightPosition = position * 2 + 1

        if (leftPosition < itemList.size) {
            holder.bindLeft(itemList[leftPosition])
        }

        if (rightPosition < itemList.size) {
            holder.bindRight(itemList[rightPosition])
        }
    }

    override fun getItemCount(): Int {
        return (itemList.size + 1) / 2
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val leftImg: ImageView = itemView.findViewById(R.id.DishImg)
        private val leftName: TextView = itemView.findViewById(R.id.DishName)
        private val leftPrice: TextView = itemView.findViewById(R.id.DishPrice)
        private val rightImg: ImageView = itemView.findViewById(R.id.DishIm)
        private val rightName: TextView = itemView.findViewById(R.id.DishNam)
        private val rightPrice: TextView = itemView.findViewById(R.id.DishPric)

        init {
            // Set click listeners for left and right items separately
            leftImg.setOnClickListener {
                onItemClick?.invoke(itemList[adapterPosition * 2])
            }

            rightImg.setOnClickListener {
                onItemClick?.invoke(itemList[adapterPosition * 2 + 1])
            }
        }

        fun bindLeft(item: dishs) {
            leftImg.setImageResource(item.dish_img)
            leftName.text = item.dish_name
            leftPrice.text = item.price.toString()
        }

        fun bindRight(item: dishs) {
            rightImg.setImageResource(item.dish_img)
            rightName.text = item.dish_name
            rightPrice.text = item.price.toString()
        }
    }
}
