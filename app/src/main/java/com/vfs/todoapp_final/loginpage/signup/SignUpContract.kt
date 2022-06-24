package com.vfs.todoapp_final.loginpage.signup

interface SignUpContract {

    interface SignUpPresenter {
        /**
         * Attach the Fragment's view to the presenter.
         * @param view  Implemented methods in Fragment to communicate with it.
         */
        fun setView(view: SignUpView?)

        /**
         * Destroy the presenter and its attached view.
         */
        fun destroy()

        /**
         * Use the user input to sign up using the Password, and Email. If invalid user input, notify the view.
         * @param password              Password of the new user
         * @param email                 Email of the new user
         */
        fun signUp(email: String, password: String)
    }

    interface SignUpView {
        fun loadingStarted()
        fun loadingFailed(message: String?)
        fun signUpSuccessful()
        fun signUpUnSuccessful(messages: String?)
    }

    interface SignUpWebServiceCallback {
        fun onResponse()
        fun onFailure(responseCode: Int, message: String?)
    }
}