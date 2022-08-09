package com.arthurribeiro.moves

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.arthurribeiro.foundation.utils.IntentHelper
import com.arthurribeiro.moves.databinding.ActivityMovesMainBinding

class MovesMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovesMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovesMainBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
            configureNavGraph()
        }
    }

    private fun configureNavGraph(){
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHost.navController
        val navGraph = navController.navInflater.inflate(R.navigation.moves_nav_graph)
        val bundle = Bundle().apply { putString(IntentHelper.MOVE_URL, getExtras()) }
        navController.setGraph(navGraph, bundle)
    }

    private fun getExtras() : String? {
        return intent.getStringExtra(IntentHelper.MOVE_URL)
    }
}