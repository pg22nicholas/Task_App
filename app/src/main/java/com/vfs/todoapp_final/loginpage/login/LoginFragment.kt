package com.vfs.todoapp_final.loginpage.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vfs.todoapp_final.R

class LoginFragment : Fragment(), LoginContract.LoginView {

    private val loginListener: LoginListener? = null
    var presenter: LoginContract.LoginPresenter? = null

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
        fun newInstance() = LoginFragment().apply {
            presenter = LoginPresenter();
            (presenter as LoginPresenter).setView(this)
        }
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
        view?.findViewById<Button>(R.id.btn_sign_up)?.setOnClickListener {
            loginListener?.gotoSignUpScreen()
        }

        // onClick for logging in
        view?.findViewById<Button>(R.id.btn_login)?.setOnClickListener {
            val username: String = view?.findViewById<TextView>(R.id.edit_txt_username)?.text.toString()
            val password: String = view?.findViewById<TextView>(R.id.edit_txt_password)?.text.toString()
            presenter?.login(username, password)
        }

        // onClick for continuing into offline mode
        view?.findViewById<Button>(R.id.btn_cont_offline)?.setOnClickListener {
            loginListener?.continueOffline()
        }
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