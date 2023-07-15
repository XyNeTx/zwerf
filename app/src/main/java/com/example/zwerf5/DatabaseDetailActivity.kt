package com.example.zwerf5

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.util.Calendar

class DatabaseDetailActivity : AppCompatActivity() {

    private lateinit var ingredient: EditText
    private lateinit var expiredate: EditText
    private lateinit var id: TextView
    private lateinit var delete: ImageView
    private lateinit var edit: ImageView
    private lateinit var dbh: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_detail)

        id = findViewById(R.id.edTId)
        id.visibility = View.GONE
        val imageViewId = findViewById<ImageView>(R.id.imageViewId)
        imageViewId.visibility = View.GONE
        ingredient = findViewById(R.id.edTIngredient)
        expiredate = findViewById(R.id.edTExpireDate)
        delete = findViewById(R.id.iVdel)
        edit = findViewById(R.id.iVEdit)

        dbh = DBHelper(this)

        id.text = intent.getStringExtra("id")
        ingredient.setText(intent.getStringExtra("ingredient"))
        expiredate.setText(intent.getStringExtra("expiredate"))

        expiredate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                val formattedMonth = String.format("%02d", monthOfYear + 1)
                val formattedDay = String.format("%02d", dayOfMonth)
                val dat = "$year-$formattedMonth-$formattedDay"
                expiredate.setText(dat)
            }, year, month, day)
            datePickerDialog.show()
        }

        delete.setOnClickListener{
            val id = id.text.toString()
            val deletedata = dbh.deleteuserdata(id)
            if (deletedata == true) {
                Toast.makeText(this, "Data Deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Can't Delete Data", Toast.LENGTH_SHORT).show()
            }
        }

        edit.setOnClickListener {
            val id = id.text.toString()
            val ingredient = ingredient.text.toString()
            val expiredate = expiredate.text.toString()
            val updatedata = dbh.updateuserdata(id,ingredient,expiredate)

            if (updatedata == true) {
                Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Can't Update Data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}