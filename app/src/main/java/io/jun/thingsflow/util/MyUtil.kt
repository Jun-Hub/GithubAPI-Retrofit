package io.jun.thingsflow.util

import android.content.Context
import android.view.inputmethod.InputMethodManager

class MyUtil {
    companion object {

        fun showKeyboard(context: Context){

            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        fun closeKeyboard(context: Context){

            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }

    }
}