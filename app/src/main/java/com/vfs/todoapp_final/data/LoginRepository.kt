package com.vfs.todoapp_final.data

import com.google.firebase.auth.FirebaseAuth
import com.vfs.todoapp_final.data.model.LoggedInUser

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
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun signup(email: String, password: String, successCallback: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    successCallback(true, "")
                } else {
                    successCallback(false, task.exception.toString())
                }
            }
    }

    fun login(email: String, password: String, successCallback: (Boolean, String) -> Unit) {
        // TODO:
    }

    fun logout() {
        user = null
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}