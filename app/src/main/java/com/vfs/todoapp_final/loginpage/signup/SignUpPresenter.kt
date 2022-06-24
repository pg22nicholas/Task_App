package com.vfs.todoapp_final.loginpage.signup

import com.vfs.todoapp_final.data.LoginRepository

class SignUpPresenter : SignUpContract.SignUpPresenter {

    private var view: SignUpContract.SignUpView? = null


    override fun setView(view: SignUpContract.SignUpView?) {
        this.view = view
    }

    override fun destroy() {
        view = null
    }

    override fun signUp(email: String, password: String) {
        LoginRepository.signup(email, password, ::signupResponse)
    }

    private fun signupResponse(isSuccess: Boolean, error: String) {
        if (isSuccess) view?.signUpSuccessful()
        else view?.signUpUnSuccessful(error)
    }
}