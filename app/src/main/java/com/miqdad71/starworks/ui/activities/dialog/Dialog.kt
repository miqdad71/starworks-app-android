package com.miqdad71.starworks.ui.activities.dialog

import android.app.AlertDialog
import android.content.Context

class Dialog {
    fun dialog(context: Context?, message: String, listAction: () -> Unit) {
        val dialog = AlertDialog.Builder(context).apply {
            setTitle("Notice")
            setMessage(message)
            setCancelable(false)
            setPositiveButton("ok") { dialogInterface, i ->
                listAction()
            }
//            setNegativeButton("No") { dialogInterface, i ->
//                dialogInterface.dismiss()
//            }
        }
        dialog.show()
    }
}