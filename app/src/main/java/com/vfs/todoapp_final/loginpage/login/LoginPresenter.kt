package com.vfs.todoapp_final.loginpage.login

import com.vfs.todoapp_final.data.LoginRepository

class LoginPresenter : LoginContract.LoginPresenter{

    private var view: LoginContract.LoginView? = null

    override fun setView(view: LoginContract.LoginView?) {
        this.view = view
    }

    override fun destroy() {
        view = null
    }

    override fun login(email: String, password: String) {
        LoginRepository.login(email, password, ::loginResponse)
    }

    private fun loginResponse(isSuccess: Boolean, error: String) {
        if (isSuccess) view?.loginSuccessful()
        else view?.loginUnSuccessful(error)
    }
}