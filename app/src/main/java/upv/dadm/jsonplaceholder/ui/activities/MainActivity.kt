package upv.dadm.jsonplaceholder.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import upv.dadm.jsonplaceholder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}