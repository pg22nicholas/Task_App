package com.vfs.todoapp_final.models

import android.os.Message
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.vfs.todoapp_final.data.LoginRepository

object FirebaseData {

    val database = Firebase.database

    fun writeObject(successCallback: (Boolean, String) -> Unit) {

        database.getReference("users")
            .child(LoginRepository.user!!.userId)
            .setValue(  LoginRepository.user,
                        DatabaseReference.CompletionListener { error, _ ->
                            if (error == null) {
                                successCallback(true, "")
                            } else {
                                successCallback(false, error.message)
                            }
                        })
    }
}