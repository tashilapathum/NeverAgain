package com.tashila.neveragain

import android.app.Dialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

public open class NeverAgainDialog : DialogFragment() {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var prefKey: String
    private lateinit var checkBox: CheckBox
    private var isChecked: Boolean = false
    private var onDismissedListener: ((isChecked: Boolean) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getString(ARG_TITLE)
        val message = arguments?.getString(ARG_MESSAGE)
        val checkboxText = arguments?.getString(ARG_CHECKBOX_TEXT)
        val isCheckedDefault = arguments?.getBoolean(ARG_IS_CHECKED) ?: false
        val positiveButtonText = arguments?.getString(ARG_POSITIVE_BUTTON_TEXT) ?: "OK"

        // Generate the hash value for shared pref
        val prefName = "${requireActivity().packageName}.$PREF_NAME"
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
            log("Saved preference with the key: $prefKey")
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
            log("Dialog is not shown based on the stored preference.")
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

    /**
     * Clears the stored preference to not show the dialog again with the specified `message`, so
     * that the dialog will be shown again.
     * */
    fun clear(context: Context, message: String) {
        val prefName = "${context.packageName}.$PREF_NAME"
        val sharedPref = context.getSharedPreferences(prefName, MODE_PRIVATE)
        val prefKey = message.hashCode().toString()

        sharedPref.edit().remove(prefKey).apply()
        log("Cleared preference with key: $prefKey")
    }

    /**
     * Clears all stored preferences to not show the dialogs again, so all dialogs will be shown again.
     * */
    fun clearAll(context: Context) {
        val prefName = "${context.packageName}.$PREF_NAME"
        val sharedPref = context.getSharedPreferences(prefName, MODE_PRIVATE)

        sharedPref.edit().clear().apply()
        log("Cleared all preferences")
    }

    private fun log(message: String) {
        if (isLoggingEnabled) {
            Log.i(TAG, message)
        }
    }

    companion object {
        private const val TAG = "NeverAgain"
        private const val PREF_NAME = "never_again_prefs"
        private const val ARG_TITLE = "title"
        private const val ARG_MESSAGE = "message"
        private const val ARG_CHECKBOX_TEXT = "checkbox_text"
        private const val ARG_IS_CHECKED = "is_checked"
        private const val ARG_POSITIVE_BUTTON_TEXT = "positive_button_text"
        private var isLoggingEnabled = false

        /**
         * Creates a new instance of the dialog.
         *
         * @param title Shown at the top of the dialog.
         *
         * @param message The key for the `SharedPreferences` entry is generated based on this
         * value. If the `message` is changed in the future, the dialog will be shown again.
         *
         * @param isChecked Checks the checkbox by default if `true`.The user's preference is
         * stored if the checkbox is checked when the dialog is dismissed, regardless of this passed
         * value.
         * */
        fun create(
            title: String,
            message: String,
            positiveButtonText: String = "OK",
            checkboxText: String = "Don't show again",
            isChecked: Boolean = false
        ): NeverAgainDialog {
            val fragment = NeverAgainDialog()
            val args = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_MESSAGE, message)
                putString(ARG_POSITIVE_BUTTON_TEXT, positiveButtonText)
                putString(ARG_CHECKBOX_TEXT, checkboxText)
                putBoolean(ARG_IS_CHECKED, isChecked)
            }
            fragment.arguments = args
            return fragment
        }

        /**
         * Toggles helpful logs for debugging.
         * */
        fun setLoggingEnabled(enabled: Boolean) {
            isLoggingEnabled = enabled
        }

    }
}