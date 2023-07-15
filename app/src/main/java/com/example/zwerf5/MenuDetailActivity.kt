package com.example.zwerf5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class MenuDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_detail)

        val textViewMenu : TextView = findViewById(R.id.TvMenu)
        val imageViewMenu : ImageView = findViewById(R.id.IvMenu)
        val textViewIngredient : TextView = findViewById(R.id.TvMenuIngredient)
        val textViewHowToDo : TextView = findViewById(R.id.TvMenuHowToDo)

        val bundle : Bundle?= intent.extras
        val name = bundle!!.getString("name")
        val image = bundle.getString("img")
        val ingredient = bundle.getString("ingredient")
        val howtodo = bundle.getString("howtodo")

        textViewMenu.text = name
        Picasso.get().load(image).into(imageViewMenu)
        textViewIngredient.text = ingredient!!.replace("\\n","\n")
        textViewHowToDo.text = howtodo!!.replace("\\n","\n")
    }
}