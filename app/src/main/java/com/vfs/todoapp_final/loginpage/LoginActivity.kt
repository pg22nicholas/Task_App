package com.vfs.todoapp_final.loginpage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.vfs.todoapp_final.MainActivity
import com.vfs.todoapp_final.R
import com.vfs.todoapp_final.loginpage.login.LoginFragment
import com.vfs.todoapp_final.loginpage.signup.SignUpFragment

class LoginActivity : AppCompatActivity(), SignUpFragment.SignUpListener, LoginFragment.LoginListener {

    private val SIGN_UP_TAG: String? = null
    private val LOGIN_TAG: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun gotoSignUpScreen() {
        setLoginScreenVisibility(GONE)
    }

    override fun loginSuccessful() {
        gotoMainScreen()
    }

    override fun continueOffline() {
        gotoMainScreen()
    }

    override fun gotoLoginScreen() {
        setLoginScreenVisibility(VISIBLE)
    }

    override fun signUpSuccessful() {
        gotoMainScreen()
    }

    /**
     * Go to the main screen.
     */
    private fun gotoMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    /**
     * Sets the visibility of the login screen to visibility, and the sign up screen to the opposite.
     * @param visibility    The visibility of the login screen
     */
    private fun setLoginScreenVisibility(visibility: Int) {

        findViewById<FrameLayout>(R.id.fragment_login_container).visibility = VISIBLE
        findViewById<FrameLayout>(R.id.fragment_sign_up_container).visibility = if (visibility == VISIBLE) GONE else VISIBLE
    }

    override fun onBackPressed() {
        if (findViewById<FrameLayout>(R.id.fragment_login_container).visibility == VISIBLE) {
            setLoginScreenVisibility(VISIBLE)
        } else {
            super.onBackPressed()
        }
    }
}