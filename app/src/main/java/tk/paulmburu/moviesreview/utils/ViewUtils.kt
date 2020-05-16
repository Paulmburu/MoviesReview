package tk.paulmburu.moviesreview.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.isVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}