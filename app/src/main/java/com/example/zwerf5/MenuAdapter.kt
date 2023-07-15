package com.example.zwerf5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MenuAdapter (
    private var menuList:ArrayList<Menu>,
    private val onMenuClickListener: OnMenuClickListener
    )
    : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>(){

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvName : TextView = itemView.findViewById(R.id.name)
        val ivImg : ImageView = itemView.findViewById(R.id.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MenuViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = menuList[position]
        holder.tvName.text = menu.name
        Picasso.get().load(menu.img).into(holder.ivImg)

        holder.itemView.setOnClickListener {
            onMenuClickListener.onMenuClicked(position)
        }
    }
}