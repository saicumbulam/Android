package com.kickstarter.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/** This is an Extension function to implement a TextChangedListener for EditText */
fun EditText.onChange(input: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            input(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

/** This is an Extension function to get the string input for EditText */
fun EditText.text() = this.text.toString()
