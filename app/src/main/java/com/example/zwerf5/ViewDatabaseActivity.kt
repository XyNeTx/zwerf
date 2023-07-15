package com.example.zwerf5

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationItemView

class ViewDatabaseActivity : AppCompatActivity() {

    private lateinit var viewDateRecyclerView: RecyclerView
    lateinit var dbh: DBHelper
    private lateinit var newArray: ArrayList<Datalist>
    private lateinit var dataAdapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_database)

        val btnSearch = findViewById<BottomNavigationItemView>(R.id.searchPage)
        val btnAdd = findViewById<BottomNavigationItemView>(R.id.addDataPage)
        val btnView = findViewById<BottomNavigationItemView>(R.id.viewDataPage)


        btnAdd.setOnClickListener{
            val intent = Intent(this@ViewDatabaseActivity, AddDatabaseActivity::class.java)
            startActivity(intent)
        }

        btnSearch.setOnClickListener{
            val intent = Intent(this@ViewDatabaseActivity, MainActivity::class.java)
            startActivity(intent)
        }

        btnView.setOnClickListener {
            Toast.makeText(this, "Now you are in View page!!!", Toast.LENGTH_SHORT).show()
        }

        viewDateRecyclerView = findViewById(R.id.viewDateRecyclerView)

        dbh = DBHelper(this)
        viewDateRecyclerView.layoutManager = LinearLayoutManager(this)
        viewDateRecyclerView.setHasFixedSize(true)
        displayuser()
    }

    private fun displayuser() {
        var newcursor: Cursor? = dbh!!.gettext()
        newArray = ArrayList<Datalist>()
        while (newcursor!!.moveToNext()) {
            val uid = newcursor.getString(0)
            val uingredient = newcursor.getString(1)
            val uexpiredate = newcursor.getString(2)
            newArray.add(Datalist(uid, uingredient, uexpiredate))
        }
        dataAdapter = DataAdapter(newArray)
        viewDateRecyclerView.adapter = dataAdapter
        dataAdapter.onItemClickListener(object : DataAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@ViewDatabaseActivity, DatabaseDetailActivity::class.java)
                intent.putExtra("id",newArray[position].id)
                intent.putExtra("ingredient",newArray[position].ingredient)
                intent.putExtra("expiredate",newArray[position].expiredate)
                startActivity(intent)
            }
        })
    }
}