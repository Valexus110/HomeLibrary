package com.valexus.homelibrary.ui.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseUser
import com.valexus.homelibrary.R
import com.valexus.homelibrary.databinding.FragmentMainPageBinding
import com.valexus.homelibrary.ui.auth.AuthViewModel
import com.valexus.homelibrary.ui.main.adapters.FragmentPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageFragment : Fragment() {

    companion object {
        const val TAG: String = "MainFragment"
    }

    private val authViewModel: AuthViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!
    private var user: FirebaseUser? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // authViewModel.logout()
        user = authViewModel.currentUser
        navController = findNavController()
        if (user == null) navController.navigate(R.id.action_main_fragment_to_login_fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        binding.viewModel = mainViewModel
        buildUI()
        return binding.root
    }

    private fun buildUI() {
        binding.profileText.text = getString(R.string.welcome_user, user?.displayName)
        binding.viewPager.adapter = FragmentPagerAdapter(requireParentFragment())
        binding.viewPager.isUserInputEnabled = true
        TabLayoutMediator(
            binding.tabLayout as TabLayout,
            binding.viewPager,
        ) { tab, position ->
            val tabNames = listOf(
                getString(R.string.all_tab),
                getString(R.string.want_read_tab),
                getString(R.string.have_read_tab)
            )
            tab.text = tabNames[position]
        }.attach()
    }
}