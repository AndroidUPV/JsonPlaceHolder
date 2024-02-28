package upv.dadm.jsonplaceholder.ui

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import upv.dadm.jsonplaceholder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tilSearchById.setEndIconOnClickListener { view ->
            // Hide the soft input keyboard
            (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(view.windowToken, 0)
            // Display the entered Id
            Snackbar.make(binding.root, "${binding.etSearchById.text}", Snackbar.LENGTH_SHORT).show()
            // Clear the EditText
            binding.etSearchById.text?.clear()
        }

    }
}