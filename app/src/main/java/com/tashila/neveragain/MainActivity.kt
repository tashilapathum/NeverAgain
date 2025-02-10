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
        binding.showTip2.setOnClickListener { showTip2() }
    }

    // Full example
    private fun showTip() {
        val dialog = NeverAgainDialog.create(
            title = "Did you know?",
            message = "This is a helpful tip.",
            checkboxText = "Don't show this ever again",
            isChecked = false,
            positiveButtonText = "Got it"
        )

        dialog.setOnDismissedListener { isChecked ->
            Log.i(TAG, "Don't want to see this thing again: $isChecked")
        }

        dialog.show(supportFragmentManager, "NeverAgainDialog")

    }

    // Minimal example
    private fun showTip2() {
        val dialog2 = NeverAgainDialog.create("Tip", "This is another helpful tip")
        dialog2.show(supportFragmentManager, null)
    }
}