package com.example.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    private fun calculateTip() {

        try {
            val costInText = binding.costOfService.text.toString()
            val cost = costInText.toDouble()

            val percentTip = when(binding.tipOptions.checkedRadioButtonId) {
                R.id.option_fifteen_percent -> 0.15
                R.id.option_twenty_percent -> 0.20
                else -> 0.10
            }

            var tip = percentTip * cost

            if(binding.roundUpSwitch.isChecked) {
                tip = kotlin.math.ceil(tip)
            }

            displayTip(tip)
        }catch (e: Exception) {
            Log.e("TipTime","Cost is Empty")
            displayTip(0.0)
        }

    }

    private fun displayTip(tip : Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.resultTextView.text = getString(R.string.tip_amount_text,formattedTip)
    }
}