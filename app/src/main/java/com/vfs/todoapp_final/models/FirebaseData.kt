package com.vfs.todoapp_final.models

import android.os.Message
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.vfs.todoapp_final.data.LoginRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.math.log

object FirebaseData {

    val database = Firebase.database

    fun updateOnlineData(successCallback: (Boolean, String) -> Unit) {
        database.getReference("users").child(LoginRepository.user!!.userId).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    database.getReference("users")
                        .child(LoginRepository.user!!.userId)
                        .child("data")
                        .setValue(Json.encodeToString(Data.categoryList))
                } else {
                    database.getReference("users").child(LoginRepository.user!!.userId).setValue(LoginRepository.user, DatabaseReference.CompletionListener { error, ref ->
                        if (error == null) {
                            database.getReference("users")
                                .child(LoginRepository.user!!.userId)
                                .child("data")
                                .setValue(Json.encodeToString(Data.categoryList))
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun storeOnlineData(successCallback: (Boolean, String) -> Unit) {

        database.getReference("users").child(LoginRepository.user!!.userId).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    database.getReference("users")
                        .child(LoginRepository.user!!.userId)
                        .child("data")
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {

                                val readData = snapshot.value.toString()
                                val stored = Json.encodeToString(Data.categoryList).toString()
                                if (stored != readData) {
                                    Log.i("TEST", "save data")
                                    Data.saveDataFromString(readData)
                                }
                                Log.i("TEST", "callback")
                                successCallback(true, "")
                            }

                            override fun onCancelled(error: DatabaseError) {
                                successCallback(false, error.message)
                            }

                        })
                } else {
                    successCallback(false, "no online data")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })



    }
}