package com.awesomecompany.mykinopoisk

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.awesomecompany.mykinopoisk.data.Film
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appBarClickListener()
        bottomListener()

        val tag = "home"
        val fragment = checkFragmentExistence(tag)
        changeFragment(fragment ?: HomeFragment(), tag)
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

    private fun bottomListener() {
        bottom_navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.home -> {
                    val tag = "home"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: HomeFragment(), tag)
                    true
                }
                R.id.favorites -> {
                    val tag = "favorites"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: FavoritesFragment(), tag)
                    true
                }
                R.id.watch_later -> {
                    val tag = "watch_later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: WatchLaterFragment(), tag)
                    true
                }
                R.id.selections -> {
                    val tag = "selections"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: SelectionsFragment(), tag)
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
}