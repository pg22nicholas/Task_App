package com.vfs.todoapp_final.loginpage.signup

class SignUpPresenter : SignUpContract.SignUpPresenter {

    private var view: SignUpContract.SignUpView? = null

    override fun setView(view: SignUpContract.SignUpView?) {
        this.view = view
    }

    override fun destroy() {
        view = null
    }

    override fun signUp(username: String?, password: String?, email: String?) {
        // TODO:
    }

}