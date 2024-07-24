package com.valexus.homelibrary.ui.auth

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import com.valexus.homelibrary.R
import com.valexus.homelibrary.data.NetworkState
import com.valexus.homelibrary.databinding.FragmentRegisterBinding
import com.valexus.homelibrary.ui.isValidEmail
import com.valexus.homelibrary.ui.isValidPassword
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    companion object {
        const val TAG: String = "RegisterFragment"
    }

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navController = findNavController()
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.username.addTextChangedListener {
            it?.let {
                isValid()
                if (it.length < 4) binding.username.error =
                    ContextCompat.getString(
                        requireContext(),
                        R.string.username_requirement
                    ) else binding.username.error = null
            }
        }
        binding.email.addTextChangedListener {
            it?.let {
                isValid()
                if (!it.isValidEmail()) binding.email.error =
                    ContextCompat.getString(
                        requireContext(),
                        R.string.email_requirement
                    ) else binding.email.error = null
            }
        }
        binding.password.addTextChangedListener {
            it?.let {
                isValid()
                if (!it.isValidPassword()) binding.password.error =
                    ContextCompat.getString(
                        requireContext(),
                        R.string.password_requirement
                    ) else binding.password.error = null
            }
        }
        authViewModel.networkState.observe(viewLifecycleOwner) {
            manageState(it)
        }
        binding.continueButton.setOnClickListener {
            authViewModel.setUser(
                username = binding.username.text.toString(),
                email = binding.email.text.toString(),
                password = binding.password.text.toString()
            )
            authViewModel.signupUser()
        }
        return binding.root
    }

    private fun manageState(state: NetworkState<FirebaseUser>?) {
        when (state) {
            is NetworkState.Failure -> {
                Log.e(TAG, state.exception.toString())
                binding.continueButton.icon = null
                Snackbar.make(
                    binding.root,
                    ContextCompat.getString(requireContext(), R.string.failed_register),
                    Toast.LENGTH_SHORT
                ).show()
            }

            is NetworkState.Loading -> {
                binding.continueButton.icon = circularIndicator()
            }

            is NetworkState.Success -> {
                navController.navigate(R.id.action_register_fragment_to_main_fragment)
            }

            null -> {}
        }
    }

    private fun isValid() {
        val res =
            binding.email.text.isValidEmail() && binding.password.text.isValidPassword() && binding.username.text.length >= 4
        var color = R.color.bright_purple
        if (!res) color = R.color.gray_light
        binding.continueButton.backgroundTintList =
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    color
                )
            )
        binding.continueButton.isEnabled = res
    }

    private fun circularIndicator(): IndeterminateDrawable<CircularProgressIndicatorSpec> {
        val spec =
            CircularProgressIndicatorSpec(
                requireContext(), null, 0,
                com.google.android.material.R.style.Widget_Material3_CircularProgressIndicator_Medium
            )
        spec.trackColor = ContextCompat.getColor(requireContext(), R.color.white_pearl)
        return IndeterminateDrawable.createCircularDrawable(requireContext(), spec)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        authViewModel.clearFlows()
        _binding = null
    }
}