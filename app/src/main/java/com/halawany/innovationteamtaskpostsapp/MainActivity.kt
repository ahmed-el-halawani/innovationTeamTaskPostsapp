package com.halawany.innovationteamtaskpostsapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.halawany.innovationteamtaskpostsapp.core.network.ConnectivityObserver.Status.Available
import com.halawany.innovationteamtaskpostsapp.core.network.ConnectivityObserver.Status.Losing
import com.halawany.innovationteamtaskpostsapp.core.network.ConnectivityObserver.Status.Lost
import com.halawany.innovationteamtaskpostsapp.core.network.ConnectivityObserver.Status.Unavailable
import com.halawany.innovationteamtaskpostsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }

    override fun onStart() {
        super.onStart()
        viewModel.connectivityObserverLiveData().observe(this) {
            Log.e(TAG, "onCreate: $it")
            when (it) {
                Available -> binding.layoutOffline.visibility = View.GONE
                Lost, Unavailable, Losing -> binding.layoutOffline.visibility = View.VISIBLE
            }
        }
    }
}

