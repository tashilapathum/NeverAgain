package com.tashila.neveragainlibrary

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

open class NeverAgainDialog : DialogFragment() {
    private lateinit var dialog: MaterialAlertDialogBuilder

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = MaterialAlertDialogBuilder(requireContext())
        return dialog.create()
    }

    fun title(title: String) {
        dialog.setTitle(title)
    }

    fun message(message: String) {
        dialog.setMessage(message)
    }

    fun checkbox(label: String) {
        dialog.setMultiChoiceItems(arrayOf(label), booleanArrayOf(true)) { p0, p1, p2 -> }
    }

    fun action(label: String) {
        dialog.setPositiveButton(label) { _, _ ->
            Toast.makeText(requireActivity(), "Done!", Toast.LENGTH_SHORT).show()
        }
    }

    fun show() {
        dialog.show()
    }
}