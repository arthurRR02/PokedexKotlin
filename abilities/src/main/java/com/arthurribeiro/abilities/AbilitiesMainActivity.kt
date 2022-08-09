package com.arthurribeiro.abilities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.arthurribeiro.abilities.databinding.ActivityAbilitiesMainBinding

class AbilitiesMainActivity : AppCompatActivity() {

    private var binding: ActivityAbilitiesMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityAbilitiesMainBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
            configureNavGraph()
        }
    }

    private fun configureNavGraph(){
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHost.navController
        val navGraph = navController.navInflater.inflate(R.navigation.abilities_nav_graph)

        navController.graph = navGraph
    }
}