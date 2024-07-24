package com.valexus.homelibrary.ui.auth

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import com.valexus.homelibrary.R
import com.valexus.homelibrary.data.NetworkState
import com.valexus.homelibrary.databinding.FragmentLoginBinding
import com.valexus.homelibrary.ui.isValidEmail
import com.valexus.homelibrary.ui.isValidPassword
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    companion object {
        const val TAG: String = "LoginFragment"
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navController = findNavController()
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        addSignUpText()
        binding.email.addTextChangedListener {
            it?.let {
                isValid()
                if (!it.isValidEmail() && it.isNotEmpty()) binding.email.error =
                    ContextCompat.getString(
                        requireContext(),
                        R.string.email_requirement
                    ) else binding.email.error = null
            }
        }
        binding.password.addTextChangedListener {
            it?.let {
                isValid()
                if (!it.isValidPassword() && it.isNotEmpty()) binding.password.error =
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
                email = binding.email.text.toString(),
                password = binding.password.text.toString()
            )
            authViewModel.loginUser()
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
                    ContextCompat.getString(requireContext(), R.string.failed_login),
                    Toast.LENGTH_SHORT
                ).show()
            }

            is NetworkState.Loading -> {
                binding.continueButton.icon = circularIndicator()
            }

            is NetworkState.Success -> {
                navController.navigate(R.id.action_login_fragment_to_main_fragment)
            }

            null -> {}
        }
    }

    private fun isValid() {
        val res = binding.email.text.isValidEmail() && binding.password.text.isValidPassword()
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

    private fun addSignUpText() {
        val source = ContextCompat.getString(
            requireContext(),
            R.string.sign_up_text
        )
        val span: Spannable = SpannableStringBuilder.valueOf(
            source
        )
        span.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                navController.navigate(R.id.action_login_fragment_to_register_fragment)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = ContextCompat.getColor(
                    requireContext(),
                    R.color.blue_neon
                )
            }

        }, source.split("?")[0].length + 1, source.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.signUpText.text = span
        binding.signUpText.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        authViewModel.clearFlows()
        _binding = null

    }
}