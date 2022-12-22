package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }

        binding.costOfServiceEditText.setOnKeyListener {view, keyCode, _ -> handlekeyEvent(view, keyCode)}
    }

    private fun handlekeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard

            val inputMethodMgr = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputMethodMgr.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }

        return false
    }

    private fun calculateTip() {

        try {
            val costInText = binding.costOfServiceEditText.text.toString()
            val cost = costInText.toDouble()

            val percentTip = when(binding.tipOptions.checkedRadioButtonId) {
                R.id.option_good_service -> 0.18
                R.id.option_amazing_service -> 0.20
                else -> 0.15
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