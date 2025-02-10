package com.tashila.neveragain

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tashila.neveragain.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.showTip.setOnClickListener { showTip() }
    }

    private fun showTip() {
        val dialog = NeverAgainDialog.newInstance(
            title = "Tip",
            message = "This is a helpful tip.",
            checkboxText = "Don't show this again",
            isChecked = false, // Default state of the checkbox
            positiveButtonText = "Got it" // Custom text for the positive button
        )

        dialog.setOnDismissedListener { isChecked ->
            Log.i(TAG, "isChecked: $isChecked")
        }

        dialog.show(supportFragmentManager, "NeverAgainDialog")
    }
}