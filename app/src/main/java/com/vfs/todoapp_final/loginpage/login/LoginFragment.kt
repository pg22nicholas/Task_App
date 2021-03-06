package com.vfs.todoapp_final.loginpage.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vfs.todoapp_final.R

class LoginFragment : Fragment(), LoginContract.LoginView {

    private var loginListener: LoginListener? = null
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnClicks(view)
        presenter = LoginPresenter();
        (presenter as LoginPresenter).setView(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginListener = context as LoginListener
    }

    override fun onDetach() {
        super.onDetach()
        loginListener = null
        presenter!!.destroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment().apply {}
    }

    override fun loadingStarted() {
        Toast.makeText(context, "Loading Started", Toast.LENGTH_SHORT).show()
    }

    override fun loadingFailed(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun loginSuccessful() {
        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
        view?.findViewById<EditText>(R.id.edit_txt_username)?.setText("")
        view?.findViewById<EditText>(R.id.edit_txt_password)?.setText("")
        loginListener?.loginSuccessful()
    }

    override fun loginUnSuccessful(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Set up the onClick events.
     */
    private fun setOnClicks(view : View) {
        // onClick for signing up
        val btn = view.findViewById<Button>(R.id.btn_sign_up)
        view.findViewById<Button>(R.id.btn_sign_up)?.setOnClickListener {
            loginListener?.gotoSignUpScreen()
        }

        // onClick for logging in
        view.findViewById<Button>(R.id.btn_login)?.setOnClickListener {
            val username: String = view.findViewById<EditText>(R.id.edit_txt_username)?.text.toString()
            val password: String = view.findViewById<EditText>(R.id.edit_txt_password)?.text.toString()
            presenter?.login(username, password)
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
    }
}