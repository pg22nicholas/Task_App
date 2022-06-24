package com.vfs.todoapp_final.loginpage.login

interface LoginContract {

    interface LoginPresenter {
        /**
         * Attach the Fragment's view to the presenter.
         * @param view  Implemented methods in Fragment to communicate with it.
         */
        fun setView(view: LoginView?)

        /**
         * Destroy the presenter and its attached view.
         */
        fun destroy()

        /**
         * Use user input to login with the username and password.
         * @param username              Username of the new user
         * @param password              Password of the new user
         */
        fun login(username: String?, password: String?)
    }

    interface LoginView {
        fun loadingStarted()
        fun loadingFailed(message: String?)
        fun loginSuccessful()
        fun loginUnSuccessful(message: String?)
    }

    interface LoginWebServiceCallback {
        fun onResponse()
        fun onFailure(responseCode: Int, message: String?)
    }
}