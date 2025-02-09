package upv.dadm.jsonplaceholder.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import upv.dadm.jsonplaceholder.R
import upv.dadm.jsonplaceholder.databinding.FragmentUserBinding
import upv.dadm.jsonplaceholder.model.User
import upv.dadm.jsonplaceholder.ui.viewmodels.UserViewModel
import upv.dadm.jsonplaceholder.utils.NoInternetException
import upv.dadm.jsonplaceholder.utils.UserNotFoundException
import java.io.IOException

class UserFragment : Fragment(R.layout.fragment_user) {

    private val viewModel: UserViewModel by viewModels()

    private var _binding: FragmentUserBinding? = null
    private val binding
        get() = _binding!!

    // Find user by Id
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserBinding.bind(view)

        binding.tilSearchById.setEndIconOnClickListener { button ->
            // Hide the soft input keyboard
            (getSystemService(
                requireContext(),
                InputMethodManager::class.java
            ) as InputMethodManager)
                .hideSoftInputFromWindow(button.windowToken, 0)
            // Display the entered Id
            if (binding.etSearchById.text.isNullOrBlank()) displayMessage(R.string.no_id)
            else viewModel.getUserById(binding.etSearchById.text.toString())
            // Clear the EditText
            binding.etSearchById.text?.clear()
        }

        // Add a new user
        binding.fabAddUser.setOnClickListener { _ ->
            viewModel.addUser(
                User(
                    id = "100",
                    name = "David de Andrés",
                    street = "Campus de Vera",
                    suite = "S/N",
                    zipcode = "46022",
                    city = "Valencia",
                    email = "ddandres@disca.upv.es",
                    phone = "963 877 007"
                )
            )
        }

        // Display user data when it changes
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.user.collect {
                    it?.let { user ->
                        binding.etUserId.setText(user.id)
                        binding.etUserName.setText(user.name)
                        binding.etUserAddress.setText(
                            getString(
                                R.string.address_two_lines,
                                user.street,
                                user.suite,
                                user.zipcode,
                                user.city
                            )
                        )
                        binding.etUserEmail.setText(user.email)
                        binding.etUserPhone.setText(user.phone)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.throwable.collect { throwable ->
                    if (throwable != null) {
                        displayMessage(
                            when (throwable) {
                                is IOException -> R.string.no_result
                                is NoInternetException -> R.string.no_internet
                                is UserNotFoundException -> R.string.user_not_found
                                else -> R.string.unexpected
                            }
                        )
                        viewModel.resetError()
                    }
                }
            }
        }
    }

    private fun displayMessage(messageId: Int) {
        displayMessage(getString(messageId))
    }

    private fun displayMessage(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}