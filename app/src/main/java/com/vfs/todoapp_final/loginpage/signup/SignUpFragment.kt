package com.vfs.todoapp_final.loginpage.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.vfs.todoapp_final.R

class SignUpFragment : Fragment(), SignUpContract.SignUpView {

    var presenter: SignUpContract.SignUpPresenter? = null
    private var listener: SignUpListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnClicks(view)
        presenter = SignUpPresenter()
        (presenter as SignUpPresenter).setView(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // attach listener implemented by parent Activity
        listener = context as SignUpListener
    }

    override fun onDetach() {
        listener = null
        presenter!!.destroy()
        super.onDetach()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {}
    }

    override fun loadingStarted() {
        Toast.makeText(context, "Loading Started", Toast.LENGTH_SHORT).show()
    }

    override fun loadingFailed(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun signUpSuccessful() {
        Toast.makeText(context, "Signup successful", Toast.LENGTH_SHORT).show()
        view?.findViewById<EditText>(R.id.edit_txt_username)?.setText("")
        view?.findViewById<EditText>(R.id.edit_txt_email)?.setText("")
        view?.findViewById<EditText>(R.id.edit_txt_password)?.setText("")
        listener?.gotoLoginScreen()
    }

    override fun signUpUnSuccessful(messages: String?) {
        Toast.makeText(context, messages, Toast.LENGTH_SHORT).show()
    }

    /**
     * Sets up the OnClick events.
     */
    private fun setOnClicks(view : View) {
        // button click for signing in with the current
        view.findViewById<Button>(R.id.btn_sign_up)?.setOnClickListener {
            // take user input for sign in
            val username: String = view.findViewById<EditText>(R.id.edit_txt_username)?.text.toString()
            val email: String = view.findViewById<EditText>(R.id.edit_txt_email)?.text.toString()
            val password: String = view.findViewById<EditText>(R.id.edit_txt_password)?.text.toString()
            // use the user input to create new account if valid user input
            presenter?.signUp(username, email, password)
        }
    }

    /**
     * Listener implemented in the parent Activity to communicate with it
     */
    interface SignUpListener {
        /**
         * Return to the login screen
         */
        fun gotoLoginScreen()

        /**
         * Go to the main screen as signing up was successful.
         */
        fun signUpSuccessful()
    }
}