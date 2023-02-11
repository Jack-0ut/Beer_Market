package com.example.studyingandroid

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studyingandroid.databinding.ActivityAddBeerBinding

class AddBeerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBeerBinding
    private lateinit var db: DbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBeerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // set db 
        db = DbHelper.getInstance(this)
        
        // setting spinner 
        val items  = resources.getStringArray(R.array.beer_colors)
        val itemsWithPrompt = arrayOf("Select color", *items)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsWithPrompt)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.inputBeerColor.adapter = adapter
        binding.inputBeerColor.setSelection(0)
        binding.inputBeerColor.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position == 0) return
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        // when click on add beer
        binding.addBeerButton.setOnClickListener {
            // check if user entered or chosen all that is necessary
            if(binding.inputBeerName.toString().isNotEmpty()){
                val result:Boolean  = db.insertData(binding.inputBeerName.text.toString(),
                                        binding.inputBeerColor.selectedItem.toString())
                if (result) {
                    Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error while adding data", Toast.LENGTH_SHORT).show()
                }
                binding.inputBeerColor.setSelection(0)
                binding.inputBeerName.setText("")

            }else{
                Toast.makeText(this,"Enter the needed values",Toast.LENGTH_SHORT).show()
            }
        }
    }
}