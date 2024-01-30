package com.example.eisenhower


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class testSettingsTitleChangeReflectsInMainActivity {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testSettingsTitleChangeReflectsInMainActivity() {
        val appCompatImageButton = onView(
            allOf(
                withId(R.id.settingsButton),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val appCompatImageButton2 = onView(
            allOf(
                withId(R.id.editTaskButton1),
                childAtPosition(
                    allOf(
                        withId(R.id.area1),
                        childAtPosition(
                            withId(R.id.gridLayout),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.titleEditText), withText("Wazne, Niepilne"),
                childAtPosition(
                    childAtPosition(
                        withId(com.google.android.material.R.id.design_bottom_sheet),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("renamed title"))

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.titleEditText), withText("renamed title"),
                childAtPosition(
                    childAtPosition(
                        withId(com.google.android.material.R.id.design_bottom_sheet),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.saveButton), withText("Zapisz"),
                childAtPosition(
                    childAtPosition(
                        withId(com.google.android.material.R.id.design_bottom_sheet),
                        0
                    ),
                    10
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val appCompatImageButton3 = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.taskToolbar),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton3.perform(click())

        val textView = onView(
            withIndex(
                allOf(
                    withId(R.id.block1), withText("renamed title"),
                    withParent(
                        allOf(
                            withId(R.id.area1),
                            withParent(withId(R.id.gridLayout))
                        )
                    ),
                    isDisplayed()
                ),0
            )
        )
        textView.check(matches(withText("renamed title")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
    fun withIndex(matcher: Matcher<View?>, index: Int): Matcher<View?>? {
        return object : TypeSafeMatcher<View?>() {
            var currentIndex = 0
            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View?): Boolean {
                return matcher.matches(view) && currentIndex++ == index
            }
        }
    }
}
