package com.vfs.todoapp_final.loginpage.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vfs.todoapp_final.R

class LoginFragment : Fragment(), LoginContract.LoginView {

    private val loginListener: LoginListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {}
    }

    override fun loadingStarted() {
        Toast.makeText(context, "Loading Started", Toast.LENGTH_SHORT).show()
    }

    override fun loadingFailed(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun loginSuccessful() {
        loginListener?.loginSuccessful()
    }

    override fun loginUnSuccessful(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Set up the onClick events.
     */
    private fun setOnClicks() {
        // onClick for signing up
        binding.btnSignUp.setOnClickListener { l -> loginListener.gotoSignUpScreen() }

        // onClick for logging in
        binding.btnLogin.setOnClickListener { l ->
            val username: String = binding.editTxtUsername.getText().toString()
            val password: String = binding.editTxtPassword.getText().toString()
            presenter.login(username, password)
        }

        // onClick for continuing into offline mode
        binding.btnContOffline.setOnClickListener { l -> loginListener.continueOffline() }
    }

    interface LoginListener {
        /**
         * Go to the SignUp Fragment.
         */
        fun gotoSignUpScreen()

        /**
         * Successful login, goto main screen.
         */
        fun loginSuccessful()

        /**
         * Continue in offline mode, going to main screen without a token.
         */
        fun continueOffline()
    }
}
}