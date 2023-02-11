package com.example.studyingandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.studyingandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //setting the spinner
        binding.spinner.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                binding.recommendedBeers.text = ""
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        // set the db
        db = DbHelper.getInstance(this)

        // when click on the button 'Find Some Beer'
        binding.findButton.setOnClickListener {
            binding.recommendedBeers.text = ""
            val recommendedBeer: ArrayList<String> = db.selectBeerByColor(binding.spinner.selectedItem.toString())
            for (beer in recommendedBeer) {
                binding.recommendedBeers.append("$beer\n")
            }
        }
        // start Add Beer Activity when click on the '+' button
        binding.addBeerButton.setOnClickListener {
            val intent = Intent(this, AddBeerActivity::class.java)
            startActivity(intent)
        }

    }

}
