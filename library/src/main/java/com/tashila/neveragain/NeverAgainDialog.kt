package com.tashila.neveragain

import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NeverAgainDialog : DialogFragment() {
    private val TAG = "NeverAgain"
    private lateinit var checkBox: CheckBox
    private var isChecked: Boolean = false
    private lateinit var prefKey: String
    private lateinit var sharedPref: SharedPreferences
    private var onDismissedListener: ((isChecked: Boolean) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getString(ARG_TITLE)
        val message = arguments?.getString(ARG_MESSAGE)
        val checkboxText = arguments?.getString(ARG_CHECKBOX_TEXT)
        val isCheckedDefault = arguments?.getBoolean(ARG_IS_CHECKED) ?: false
        val positiveButtonText = arguments?.getString(ARG_POSITIVE_BUTTON_TEXT) ?: "OK"

        // Generate the hash value for shared pref
        val prefName = "${requireActivity().packageName}.SHARED_PREF"
        sharedPref = requireActivity().getSharedPreferences(prefName, MODE_PRIVATE)
        prefKey = message.hashCode().toString()

        // Create a custom view for the checkbox
        val view = layoutInflater.inflate(R.layout.dialog_never_again, null)
        checkBox = view.findViewById(R.id.checkbox)
        checkBox.text = checkboxText
        checkBox.isChecked = isCheckedDefault
        checkBox.setOnCheckedChangeListener { _, b -> isChecked = b }

        val builder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setView(view)
            .setPositiveButton(positiveButtonText, null)

        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        checkPreference()
    }

    /**
     * Stores the preference with a key generated using the hash value of the message set to the dialog.
     * */
    private fun savePreference() {
        if (isChecked) {
            sharedPref.edit().putBoolean(prefKey, true).apply()
            Log.i(TAG, "Saved preference with the key: $prefKey")
        }
        onDismissedListener?.invoke(isChecked)
    }

    /**
     * Prevents showing the dialog if the pref is already stored as 'true'. `SharedPreferences.contains`
     * is not used because the developer can reset the preference to false manually to show the
     * dialog again if needed.
     * */
    private fun checkPreference() {
        if (sharedPref.getBoolean(prefKey, false)) {
            Log.i(TAG, "Dialog is not shown based on the stored preference.")
            onDismissedListener?.invoke(true)
            onDismissedListener = null
            dismissAllowingStateLoss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        savePreference()
        super.onDismiss(dialog)
    }

    fun setOnDismissedListener(action: (isChecked: Boolean) -> Unit): NeverAgainDialog {
        onDismissedListener = action
        return this
    }

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_MESSAGE = "message"
        private const val ARG_CHECKBOX_TEXT = "checkbox_text"
        private const val ARG_IS_CHECKED = "is_checked"
        private const val ARG_POSITIVE_BUTTON_TEXT = "positive_button_text"

        fun newInstance(
            title: String,
            message: String,
            checkboxText: String = "Don't show again",
            isChecked: Boolean = false,
            positiveButtonText: String = "OK"
        ): NeverAgainDialog {
            val fragment = NeverAgainDialog()
            val args = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_MESSAGE, message)
                putString(ARG_CHECKBOX_TEXT, checkboxText)
                putBoolean(ARG_IS_CHECKED, isChecked)
                putString(ARG_POSITIVE_BUTTON_TEXT, positiveButtonText)
            }
            fragment.arguments = args
            return fragment
        }
    }
}