package com.app.githubuser.ui.main

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.app.githubuser.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    private val username = "ichsnn"

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun assertSearchUser() {
        onView(withId(R.id.search_github_user)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText(username))
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(pressImeActionButton())
        onView(withId(R.id.rv_list_github_user)).check(ViewAssertions.matches(isDisplayed()))
    }

}