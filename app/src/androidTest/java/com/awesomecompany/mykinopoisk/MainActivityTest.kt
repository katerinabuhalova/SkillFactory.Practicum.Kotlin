package com.awesomecompany.mykinopoisk

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var mActivityRule: ActivityTestRule<MainActivity?>? = ActivityTestRule(
        MainActivity::class.java
    )

    @Test
    fun checkRecyclerView() {
        onView(withId(R.id.main_recycler)).check(matches(isDisplayed()))
        onView(withId(R.id.main_recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FilmViewHolder>(
                0,
                click()
            )
        )
    }

    @Test
    fun shouldOpenDetailsFragment() {
        onView(withId(R.id.main_recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FilmViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun addToFavoritesButtonClickable() {
        onView(withId(R.id.main_recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FilmViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.details_fab_favorites)).perform(click())
        onView(withId(R.id.details_fab_favorites)).perform(click())
    }

    @Test
    fun allMenuDestinationsShouldWork() {
        onView(withId(R.id.favorites)).perform(click())
        onView(withId(R.id.favorites_root)).check(matches(isDisplayed()))

        onView(withId(R.id.watch_later)).perform(click())
        onView(withId(R.id.watch_later_root)).check(matches(isDisplayed()))

        onView(withId(R.id.selections)).perform(click())
        onView(withId(R.id.selections_root)).check(matches(isDisplayed()))

        onView(withId(R.id.home)).perform(click())
        onView(withId(R.id.home_fragment_root)).check(matches(isDisplayed()))
    }
}