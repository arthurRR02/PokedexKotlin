package com.arthurribeiro.abilities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.arthurribeiro.abilities.databinding.ActivityAbilitiesMainBinding
import com.arthurribeiro.foundation.utils.IntentHelper

class AbilitiesMainActivity : AppCompatActivity() {

    private var binding: ActivityAbilitiesMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAbilitiesMainBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
            configureNavGraph()
        }
    }

    private fun configureNavGraph(){
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHost.navController
        val navGraph = navController.navInflater.inflate(R.navigation.abilities_nav_graph)
        val bundle = Bundle().apply { putString(IntentHelper.ABILITY_URL, getExtras()) }
        navController.setGraph(navGraph, bundle)
    }

    private fun getExtras() : String? {
        return intent.getStringExtra(IntentHelper.ABILITY_URL)
    }
}