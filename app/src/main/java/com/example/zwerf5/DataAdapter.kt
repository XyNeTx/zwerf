package com.example.zwerf5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(var userList: ArrayList<Datalist>): RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun onItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class DataViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        val ingredient: TextView = itemView.findViewById(R.id.TvDatabaseIngredient)
        val expiredate: TextView = itemView.findViewById(R.id.TvDatabaseExpireDate)
        val id: TextView = itemView.findViewById(R.id.TvId)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_database,parent,false)
        return DataViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.id.text = currentItem.id
        holder.ingredient.text = currentItem.ingredient
        holder.expiredate.text = currentItem.expiredate
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}