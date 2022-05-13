package com.example.firebasedemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.firebasedemo.databinding.FragmentLoginBinding
import com.example.firebasedemo.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {

    lateinit var authentication: FirebaseAuth

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container,false)
        val root: View = binding.root
        authentication = Firebase.auth
        binding.registerUserButtonRegister.setOnClickListener { view ->
            if (validateFields(binding)) {
                Toast.makeText(this.context, "Registered User", Toast.LENGTH_SHORT).show()
                registerUser(binding)
            } else {
                Toast.makeText(this.context, "Complete required information", Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }

    private fun validateFields(binding: FragmentRegisterBinding): Boolean {

        val nameFilled: Boolean = !binding.nameEditTextRegister.text.isEmpty()
        val lastNameFilled: Boolean = !binding.lastNameEditTextRegister.text.isEmpty()
        val mailFilled: Boolean = !binding.mailEditTextRegister.text.isEmpty()
        val passwordFilled: Boolean = !binding.passwordEditTextRegister.text.isEmpty()

        return nameFilled && lastNameFilled && mailFilled && passwordFilled
    }

    private fun registerUser(binding: FragmentRegisterBinding) {
        val email: String = binding.mailEditTextRegister.text.toString()
        val password: String = binding.passwordEditTextRegister.text.toString()
        val root: View = binding.root

        authentication.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Navigation.findNavController(root).navigate(R.id.action_registerFragment_to_loginFragment)
            } else {
                Toast.makeText(this.context, "Error while registered user", Toast.LENGTH_SHORT).show()
            }
        }

    }
}