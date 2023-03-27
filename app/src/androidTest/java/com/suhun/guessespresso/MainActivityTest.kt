package com.suhun.guessespresso

import android.content.res.Resources
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    @Rule
    @JvmField
    val activityScenarioRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @Test
    fun runGuessTest(){
        var secret:Int = 0
        lateinit var resources:Resources

        activityScenarioRule.scenario.onActivity {
            secret = it.secretNumber.secretRandom
            resources = it.resources
        }

        for(num in 1..30){
            val message:String = if(secret > num) resources.getString(R.string.bigger)
                                    else if(secret<num) resources.getString(R.string.smaller)
                                    else resources.getString(R.string.you_got_it)
            Espresso.onView(ViewMatchers.withId(R.id.userInputEditText)).perform(ViewActions.clearText())
            Espresso.onView(ViewMatchers.withId(R.id.userInputEditText)).perform(ViewActions.typeText(num.toString()))
            Espresso.onView(ViewMatchers.withId(R.id.guessButton)).perform(ViewActions.click())
            Espresso.onView(ViewMatchers.withText(message)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Espresso.onView(ViewMatchers.withText("Ok")).perform(ViewActions.click())
        }
    }
}

