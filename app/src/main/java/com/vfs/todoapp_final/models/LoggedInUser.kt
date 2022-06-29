package com.vfs.todoapp_final.models

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,
    val username: String,
    val email: String,

)