package com.example.eisenhower


import android.app.Application
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.eisenhower.database.AppDatabase
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class testEditTaskDisplaysEditedTitleInMainActivity {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun clearDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val application = context as Application
        val db = AppDatabase.getDatabase(application)
        val taskDao = db.tasksDao()
        taskDao.deleteAll()
    }

    @After
    fun clearDb2() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val application = context as Application
        val db = AppDatabase.getDatabase(application)
        val taskDao = db.tasksDao()
        taskDao.deleteAll()
    }

    @Test
    fun testEditTaskDisplaysEditedTitleInMainActivity() {
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
        appCompatEditText.perform(scrollTo(), replaceText("test"), closeSoftKeyboard())

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
        appCompatEditText2.perform(scrollTo(), replaceText("test"), closeSoftKeyboard())

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

        val appCompatImageButton2 = onView(
            allOf(
                withId(R.id.editTaskButton),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.taskDetailsRecyclerView),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.titleEditText), withText("test"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        1
                    ),
                    0
                )
            )
        )
        appCompatEditText3.perform(scrollTo(), replaceText("edited title"))

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.titleEditText), withText("edited title"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(closeSoftKeyboard())

        val materialButton2 = onView(
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
        materialButton2.perform(scrollTo(), click())

        val appCompatImageButton3 = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.taskToolbar),
                        childAtPosition(
                            withId(R.id.taskDetailsConstraintLayout),
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
                    withId(R.id.taskTextView), withText("edited title"),
                    withParent(withParent(withId(R.id.zero))),
                    isDisplayed()
                ),0
            )
        )
        textView.check(matches(withText("edited title")))
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
