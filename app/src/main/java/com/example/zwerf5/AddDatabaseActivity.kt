package com.example.zwerf5

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class AddDatabaseActivity : AppCompatActivity() {

    private lateinit var ingredient: TextInputEditText
    private lateinit var expireDate: TextInputEditText
    private lateinit var id: TextInputEditText
    private lateinit var save: Button
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_database)

        val btnSearch = findViewById<BottomNavigationItemView>(R.id.searchPage)
        val btnAdd = findViewById<BottomNavigationItemView>(R.id.addDataPage)
        val btnView = findViewById<BottomNavigationItemView>(R.id.viewDataPage)

        ingredient = findViewById(R.id.EtInputIngredient)
        expireDate = findViewById(R.id.EtInputExpireDate)
        id = findViewById(R.id.EtInputId)
        id.visibility = View.GONE
        save = findViewById(R.id.saveBtn)
        db = DBHelper(this)

        btnAdd.setOnClickListener {
            Toast.makeText(this, "Now you are in Add page!!!", Toast.LENGTH_SHORT).show()
        }

        btnSearch.setOnClickListener {
            val intent = Intent(this@AddDatabaseActivity, MainActivity::class.java)
            startActivity(intent)
        }

        btnView.setOnClickListener {
            val intent = Intent(this@AddDatabaseActivity, ViewDatabaseActivity::class.java)
            startActivity(intent)
        }

        save.setOnClickListener {
            val id = id.text.toString()
            val ingredient = ingredient.text.toString()
            val expiredate = expireDate.text.toString()
            val savedata = db.saveuserdata(id, ingredient, expiredate)
            if (TextUtils.isEmpty(ingredient) || TextUtils.isEmpty(expiredate)) {
                Toast.makeText(this, "Please input id & ingredient & expire date", Toast.LENGTH_SHORT).show()
            } else {
                if (savedata == true) {
                    Toast.makeText(this, "Save Ingredient", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please input difference id", Toast.LENGTH_SHORT).show()
                }
            }
        }
        expireDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                val formattedMonth = String.format("%02d", monthOfYear + 1)
                val formattedDay = String.format("%02d", dayOfMonth)
                val dat = "$year-$formattedMonth-$formattedDay"
                expireDate.setText(dat)
            }, year, month, day)
            datePickerDialog.show()
        }
    }
}