package com.east.customview.material_design_1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.east.customview.R

class FruitAdapter(private val mFruitList: List<Fruit>) :
    RecyclerView.Adapter<FruitAdapter.ViewHolder>() {
    private var mContext: Context? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cardView: CardView
        var fruitImage: ImageView
        var fruitName: TextView

        init {
            cardView = view as CardView
            fruitImage =
                view.findViewById<View>(R.id.fruit_image) as ImageView
            fruitName =
                view.findViewById<View>(R.id.fruit_name) as TextView
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.fruit_item, parent, false)
        val holder =
            ViewHolder(view)
        holder.cardView.setOnClickListener {
            val position = holder.adapterPosition
            val fruit = mFruitList[position]
            val intent =
                Intent(mContext, FruitActivity::class.java)
            intent.putExtra(FruitActivity.FRUIT_NAME, fruit.name)
            intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, fruit.imageId)
            mContext!!.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val fruit = mFruitList[position]
        holder.fruitName.text = fruit.name
        Glide.with(mContext!!).load(fruit.imageId).into(holder.fruitImage)
    }

    override fun getItemCount(): Int {
        return mFruitList.size
    }

    companion object {
        private const val TAG = "FruitAdapter"
    }

}