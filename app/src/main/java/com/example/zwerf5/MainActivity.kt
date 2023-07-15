package com.example.zwerf5

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Locale
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), OnMainClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ingredientList: ArrayList<Ingredient>
    private lateinit var searchView: SearchView
    private lateinit var ingredientAdapter: IngredientAdapter
    private var dbfire = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSearch = findViewById<BottomNavigationItemView>(R.id.searchPage)
        val btnAdd = findViewById<BottomNavigationItemView>(R.id.addDataPage)
        val btnView = findViewById<BottomNavigationItemView>(R.id.viewDataPage)

        btnSearch.setOnClickListener {
            Toast.makeText(this, "Now you are in Search page!!!", Toast.LENGTH_SHORT).show()
        }

        btnAdd.setOnClickListener{
            val intent = Intent(this@MainActivity, AddDatabaseActivity::class.java)
            startActivity(intent)
        }

        btnView.setOnClickListener{
            val intent = Intent(this@MainActivity, ViewDatabaseActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recylerView)
        searchView = findViewById(R.id.searchView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        ingredientList = arrayListOf()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        dbfire = FirebaseFirestore.getInstance()

        dbfire.collection("Ingredient").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val ingredient: Ingredient? = data.toObject(Ingredient::class.java)
                    if (ingredient != null) {
                        ingredientList.add(ingredient)
                    }
                }
                ingredientAdapter = IngredientAdapter(ingredientList,this)
                recyclerView.adapter = ingredientAdapter
                ingredientAdapter.notifyDataSetChanged()
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun filterList(query: String?) {
        if (query != null){
            val filteredList = ArrayList<Ingredient>()
            for (i in ingredientList){
                if (i.name!!.lowercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()){
                Toast.makeText(this,"No ingredient Found",Toast.LENGTH_SHORT).show()
            }
            else {
                ingredientAdapter.setFilterList(filteredList)
            }
        }
    }

    override fun onMainClicked(name: String) {
        val activityMap = mapOf(
            "หมู" to PorkSelectedActivity::class.java,
            "ไก่" to ChickSelectedActivity::class.java,
            "กุ้ง" to ShrimpSelectedActivity::class.java,
            "ปู" to CrabSelectedActivity::class.java,
            "หมึก" to SquidSelectedActivity::class.java,
            "กะหล่ำปลี" to CabbageSelectedActivity::class.java,
            "คะน้า" to KaleSelectedActivity::class.java,
            "สตรอว์เบอร์รี" to StrawberrySelectedActivity::class.java,
            "ชีส" to CheeseSelectedActivity::class.java,
            "เป็ด" to DuckSelectedActivity::class.java,
            "เนื้อวัว" to BeefSelectedActivity::class.java,
            "ไข่" to EggSelectedActivity::class.java,
            "ปลากระป๋อง" to FishCanSelectedActivity::class.java,
            "ข้าวโพด" to CornSelectedActivity::class.java
        )
        val selectedActivity = activityMap[name]
        selectedActivity?.let {
            val intent = Intent(this, it)
            startActivity(intent)
        }
    }
}