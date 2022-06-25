package com.vfs.todoapp_final.data

import android.os.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.vfs.todoapp_final.data.model.LoggedInUser
import com.vfs.todoapp_final.models.FirebaseData

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

object LoginRepository {

    var auth : FirebaseAuth = FirebaseAuth.getInstance()

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun signup(username: String, email: String, password: String, successCallback: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val profileUpdate = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()
                    auth.currentUser!!.updateProfile(profileUpdate)

                    successCallback(true, "")
                } else {
                    successCallback(false, task.exception.toString())
                }
            }
    }

    fun login(email: String, password: String, successCallback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val username = auth.currentUser!!.displayName.toString()
                    val email = auth.currentUser!!.email.toString()
                    val uid = auth.currentUser!!.uid
                    setLoggedInUser(LoggedInUser(uid, username, email))
                    successCallback(true, "")
                } else {
                    successCallback(false, task.exception.toString())
                }
            }
    }

    fun logout() {
        user = null
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}