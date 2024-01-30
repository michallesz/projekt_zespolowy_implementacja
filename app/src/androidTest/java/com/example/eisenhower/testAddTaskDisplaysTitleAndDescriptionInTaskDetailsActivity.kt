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
class testAddTaskDisplaysTitleAndDescriptionInTaskDetailsActivity {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testAddTaskDisplaysTitleAndDescriptionInTaskDetailsActivity() {
        val appCompatImageButton = onView(
            allOf(
                withId(R.id.addTaskButton),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.titleEditText),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        1
                    ),
                    0
                )
            )
        )
        appCompatEditText.perform(scrollTo(), replaceText("title test"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.descriptionEditText),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        1
                    ),
                    1
                )
            )
        )
        appCompatEditText2.perform(scrollTo(), replaceText("desc test"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.addTaskButton), withText("Zapisz"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        1
                    ),
                    5
                )
            )
        )
        materialButton.perform(scrollTo(), click())

        val materialTextView = onView(
            allOf(
                withId(R.id.block0), withText("Ważne, Pilne"),
                childAtPosition(
                    allOf(
                        withId(R.id.area0),
                        childAtPosition(
                            withId(R.id.gridLayout),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val textView = onView(
            withIndex(
                allOf(
                    withId(R.id.taskTitleTextView), withText("title test"),
                    withParent(withParent(withId(R.id.taskDetailsRecyclerView))),
                    isDisplayed()
                ),0
            )
        )
        textView.check(matches(withText("title test")))

        val textView2 = onView(
            withIndex(
                allOf(
                    withId(R.id.taskDescriptionTextView), withText("desc test"),
                    withParent(withParent(withId(R.id.taskDetailsRecyclerView))),
                    isDisplayed()
                ),0
            )
        )
        textView2.check(matches(withText("desc test")))
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
