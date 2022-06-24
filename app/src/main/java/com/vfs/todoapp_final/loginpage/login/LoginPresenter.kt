package com.vfs.todoapp_final.loginpage.login

class LoginPresenter : LoginContract.LoginPresenter{

    private var view: LoginContract.LoginView? = null

    override fun setView(view: LoginContract.LoginView?) {
        this.view = view
    }

    override fun destroy() {
        view = null
    }

    override fun login(username: String?, password: String?) {
        // TODO: Perform login
    }
}