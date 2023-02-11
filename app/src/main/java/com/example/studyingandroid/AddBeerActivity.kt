package com.example.studyingandroid

import android.os.Bundle
import android.util.Log
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
        db = DbHelper.getInstance(this)
        binding.addBeerButton.setOnClickListener {
            if(binding.inputBeerName.toString().isNotEmpty()){
                Log.d("check_f","Input is good")
                val result:Boolean  = db.insertData(binding.inputBeerName.text.toString(),
                                        binding.inputBeerColor.selectedItem.toString())
                if (result) {
                    Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error while adding data", Toast.LENGTH_SHORT).show()
                }
                binding.inputBeerName.setText("")

            }else{
                Toast.makeText(this,"Enter the needed values",Toast.LENGTH_SHORT).show()
            }
        }
    }
}