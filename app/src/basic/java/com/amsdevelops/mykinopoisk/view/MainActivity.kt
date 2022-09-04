package com.amsdevelops.mykinopoisk.view

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.awesomecompany.mykinopoisk.App
import com.awesomecompany.mykinopoisk.R
import com.awesomecompany.mykinopoisk.data.entity.Film
import com.awesomecompany.mykinopoisk.databinding.ActivityMainBinding
import com.awesomecompany.mykinopoisk.view.fragments.*
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appBarClickListener()
        initNavigation()

        val tag = "home"
        val fragment = checkFragmentExistence(tag)
        changeFragment(fragment ?: HomeFragment(), tag)

        receiver = ConnectionChecker()
        val filters = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_BATTERY_LOW)
        }
        registerReceiver(receiver, filters)

        showPromo()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    private fun appBarClickListener() {
        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun checkFragmentExistence(tag: String): Fragment? =
        supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .commit()
    }

    private fun initNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val tag = "home"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: HomeFragment(), tag)
                    true
                }
                R.id.favorites -> {
                    Toast.makeText(this, getString(R.string.MainActivity_favorites), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.watch_later -> {
                    val tag = "watch_later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: WatchLaterFragment(), tag)
                    true
                }
                R.id.selections -> {
                    Toast.makeText(this, getString(R.string.MainActivity_selections), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.settings -> {
                    val tag = "settings"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: SettingsFragment(), tag)
                    true
                }
                else -> false
            }
        }
    }

    fun launchDetailsFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable("film", film)
        val fragment = DetailsFragment()
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        var count = supportFragmentManager.backStackEntryCount
        if (count > 0) {
            super.onBackPressed()
            return
        }

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.AlertDialogTitle))
            .setPositiveButton(getString(R.string.AlertDialog_positiveButton)) { _, _ ->
                super.onBackPressed()
            }
            .setNegativeButton(getString(R.string.AlertDialog_negativeButton)) { _, _ ->
            }
            .setNeutralButton(getString(R.string.AlertDialog_neutralButton)) { _, _ ->
                Toast.makeText(this, getString(R.string.AlertDialog_toast), Toast.LENGTH_SHORT)
                    .show()
            }
            .show()
    }

   private fun showPromo(){
       if (!App.instance.isPromoShown) {
           val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
           val configSettings = FirebaseRemoteConfigSettings.Builder()
               .setMinimumFetchIntervalInSeconds(0)
               .build()
           firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
           firebaseRemoteConfig.fetch()
               .addOnCompleteListener {
                   if (it.isSuccessful) {
                       firebaseRemoteConfig.activate()
                       val filmLink = firebaseRemoteConfig.getString("film_link")
                       //Если поле не пустое
                       if (filmLink.isNotBlank
                               ()) {
                           App.instance.isPromoShown = true
                           binding.promoViewGroup.apply {
                               //Делаем видимой
                               visibility = View.VISIBLE
                               animate()
                                   .setDuration(1500)
                                   .alpha(1f)
                                   .start()
                               setLinkForPoster(filmLink)
                               watchButton.setOnClickListener {
                                   visibility = View.GONE
                               }
                           }
                       }
                   }
               }
       }
   }
}