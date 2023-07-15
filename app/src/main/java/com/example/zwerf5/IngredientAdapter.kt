package com.example.zwerf5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class IngredientAdapter(
    private var ingredientList: ArrayList<Ingredient>,
    private val onMainClickListener: OnMainClickListener
    )
    : RecyclerView.Adapter<IngredientAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvName : TextView = itemView.findViewById(R.id.name)
        val ivImg : ImageView = itemView.findViewById(R.id.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ingredient = ingredientList[position]
        holder.tvName.text = ingredient.name
        Picasso.get().load(ingredient.img).into(holder.ivImg)

        holder.itemView.setOnClickListener {
            onMainClickListener.onMainClicked(ingredient.name!!)
        }
    }

    fun setFilterList(ingredientList: List<Ingredient>) {
        this.ingredientList = ArrayList(ingredientList)
        notifyDataSetChanged()
    }
}