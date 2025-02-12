package com.tashila.neveragain

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tashila.neveragain.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val tip1 = "This is a helpful tip."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.showTip1.setOnClickListener { showTip() }
        binding.showTip2.setOnClickListener { showTip2() }
        binding.clearTip1.setOnClickListener { clearTip1() }
        binding.clearAll.setOnClickListener { clearAll() }
    }

    private fun showTip() {
        NeverAgainDialog.setLoggingEnabled(true)
        NeverAgainDialog.create(
            title = "Did you know?",
            message = tip1,
            positiveButtonText = "Got it",
            checkboxText = "Don't show this ever again",
            isChecked = false,
        ).setOnDismissedListener { isChecked ->
            Log.i(TAG, "Don't want to see this thing again: $isChecked")
        }.show(supportFragmentManager, "tag")
    }

    private fun showTip2() {
        NeverAgainDialog
            .create("Tip", "This is another helpful tip")
            .show(supportFragmentManager, null)
    }

    private fun clearTip1() {
        val neverAgainDialog = NeverAgainDialog()
        neverAgainDialog.clear(this, tip1)
    }

    private fun clearAll() {
        val neverAgainDialog = NeverAgainDialog()
        neverAgainDialog.clearAll(this)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}