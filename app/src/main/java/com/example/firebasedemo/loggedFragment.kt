package com.example.firebasedemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import com.example.firebasedemo.databinding.FragmentLoggedBinding
import com.example.firebasedemo.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class loggedFragment : Fragment() {

    lateinit var authentication: FirebaseAuth

    private var _binding : FragmentLoggedBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authentication = FirebaseAuth.getInstance()
        _binding = FragmentLoggedBinding.inflate(inflater, container,false)
        val root: View = binding.root
        binding.logoutButton.setOnClickListener { view ->
            Firebase.auth.signOut()
            val result = true
            setFragmentResult("requestKey", bundleOf("data" to result))
            Navigation.findNavController(root).navigate(R.id.action_loggedFragment_to_loginFragment)
        }
        return root
    }
}