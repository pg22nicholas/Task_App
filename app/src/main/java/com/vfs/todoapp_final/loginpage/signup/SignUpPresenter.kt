package com.vfs.todoapp_final.loginpage.signup

class SignUpPresenter {

    private var view: SignUpContract.SignUpView? = null

    fun setView(view: SignUpContract.SignUpView?) {
        this.view = view
    }

    fun destroy() {
        view = null
    }

    fun signUp(username: String?, password: String?, email: String?) {
        // TODO: Signup
    }
}