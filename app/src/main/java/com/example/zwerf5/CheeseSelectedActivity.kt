package com.example.zwerf5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CheeseSelectedActivity : AppCompatActivity(), OnMenuClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var menuList: ArrayList<Menu>
    private lateinit var menuAdapter: MenuAdapter
    private var dbCheese = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheese_selected)

        recyclerView = findViewById(R.id.recyclerViewCheese)
        recyclerView.layoutManager = LinearLayoutManager(this)

        menuList = arrayListOf()

        dbCheese = FirebaseFirestore.getInstance()

        dbCheese.collection("MenuCheese").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val menu: Menu? = data.toObject(Menu::class.java)
                    if (menu != null) {
                        menuList.add(menu)
                    }
                }
                menuAdapter = MenuAdapter(menuList,this)
                recyclerView.adapter = menuAdapter
                menuAdapter.notifyDataSetChanged()
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    override fun onMenuClicked(position: Int) {
        if (position >= 0 && position < menuList.size) {
            val intent = Intent(this, MenuDetailActivity::class.java)
            intent.putExtra("name", menuList[position].name)
            intent.putExtra("img", menuList[position].img)
            intent.putExtra("ingredient", menuList[position].ingredient)
            intent.putExtra("howtodo", menuList[position].howtodo)
            startActivity(intent)
        }
    }

}