package com.example.firebasedemo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation
import com.example.firebasedemo.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class loginFragment() : Fragment() {

    lateinit var authentication: FirebaseAuth

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var comingFromLoggedView: Boolean = false

    override fun onStart() {
        super.onStart()
        val root: View = binding.root
        setFragmentResultListener("requestKey") { key, bundle ->
            // Any type can be passed via to the bundle
            val result = bundle.getBoolean("data")
            comingFromLoggedView = result
        }
        val user: FirebaseUser? = authentication.currentUser
        if (user==null && !comingFromLoggedView) {
            Navigation.findNavController(root).navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authentication = Firebase.auth
        _binding = FragmentLoginBinding.inflate(inflater, container,false)
        val root: View = binding.root

        binding.loginButtonLogin.setOnClickListener {
            logIn(root = root)
        }
        return root
    }

    fun logIn(root: View) {
        val mail: String = binding.mailEditTextLogin.text.toString()
        val password: String = binding.passwordEditTextLogIn.text.toString()

        authentication.signInWithEmailAndPassword(mail,password).addOnCompleteListener {
            if (it.isSuccessful) {
                Navigation.findNavController(root).navigate(R.id.action_loginFragment_to_loggedFragment)
            } else {
                Toast.makeText(this.context, "Wrong Credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}