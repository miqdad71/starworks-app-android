package com.miqdad71.starworks.view.dialog

import android.app.AlertDialog
import android.content.Context

class Dialog {
    fun dialog(context: Context?, message: String, listAction: () -> Unit) {
        val dialog = AlertDialog.Builder(context).apply {
            setTitle("Notice")
            setMessage(message)
            setCancelable(false)
            setPositiveButton("Yes") { dialogInterface, i ->
                listAction()
            }
            setNegativeButton("No") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
        }
        dialog.show()
    }

    fun dialogCancel(context: Context?, message: String, listAction: () -> Unit) {
        val dialog = AlertDialog.Builder(context).apply {
            setTitle("Notice")
            setMessage(message)
            setCancelable(false)
            setPositiveButton("OK") { _, _ ->
                listAction()
            }
        }
        dialog.show()
    }

}