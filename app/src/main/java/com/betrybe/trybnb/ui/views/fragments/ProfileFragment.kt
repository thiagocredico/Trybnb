package com.betrybe.trybnb.ui.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.betrybe.trybnb.R
import com.betrybe.trybnb.common.ApiIdlingResource
import com.betrybe.trybnb.data.api.RetrofitInstance
import com.betrybe.trybnb.data.models.LoginRequest
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import java.io.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val loginButton = view.findViewById<Button>(R.id.login_button_profile)

        val loginText = view.findViewById<TextInputEditText>(R.id.login_input_profile_text)
        val passwordText = view.findViewById<TextInputEditText>(R.id.password_input_profile_text)

        loginButton.setOnClickListener {
            val email = loginText.text.toString()
            val password = passwordText.text.toString()
            val loginFieldsIsValid = validateLogin(email, password, view)
            if (loginFieldsIsValid) {
                login(email, password, view)
            }
        }

        return view
    }
    private fun validateLogin(email: String, password: String, view: View): Boolean {
        val loginInput = view.findViewById<TextInputLayout>(R.id.login_input_profile)
        val passwordInput = view.findViewById<TextInputLayout>(R.id.password_input_profile)

        val emailIsValid = email.isNotEmpty()
        val passwordIsValid = password.isNotEmpty()

        if (!emailIsValid) {
            loginInput.error = getString(R.string.login_error)
        } else {
            loginInput.error = null
        }

        if (!passwordIsValid) {
            passwordInput.error = getString(R.string.password_error)
        } else {
            passwordInput.error = null
        }

        return passwordIsValid && emailIsValid
    }
    private fun login(email: String, password: String, view: View) {
        val api = RetrofitInstance.create()

        val loginRequest = LoginRequest(email, password)

        val scope = CoroutineScope(Dispatchers.IO)

        val loginStatusMessageTextView = view.findViewById<MaterialTextView>(
            R.id.login_status_message
        )

        scope.launch {
            try {
                ApiIdlingResource.increment()
                val result = api.doLoginRequest(loginRequest)
                println("Token: $result")
                activity?.runOnUiThread {
                    loginStatusMessageTextView.text = getString(R.string.login_feito_com_sucesso)
                    loginStatusMessageTextView.visibility = View.VISIBLE
                }
                ApiIdlingResource.decrement()
            } catch (e: HttpException) {
                println("Error: ${e.message}")
                ApiIdlingResource.decrement()
            } catch (e: IOException) {
                println("Error: ${e.message}")
                ApiIdlingResource.decrement()
            }
        }
    }
}
