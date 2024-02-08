package com.example.eisenhower


import android.app.Application
import android.content.Context
import android.graphics.Color
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
import com.example.eisenhower.model.Block
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class testSettingsTitleChangeReflectsInBlockView {

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
        val blockDao = db.blockDao()
        blockDao.update(Block(id = 1, title = "Ważne, Pilne", color = Color.parseColor("#FFCCCC"), priority = 0))
        blockDao.update(Block(id = 2, title = "Ważne, Niepilne", color = Color.parseColor("#FFDDCC"), priority = 1))
        blockDao.update(Block(id = 3, title = "Nieważne, Pilne", color = Color.parseColor("#FFEECC"), priority = 2))
        blockDao.update(Block(id = 4, title = "Nieważne, Niepilne", color = Color.parseColor("#FFFFCC"), priority = 3))
    }

    @After
    fun clearDb2() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val application = context as Application
        val db = AppDatabase.getDatabase(application)
        val taskDao = db.tasksDao()
        taskDao.deleteAll()
        val blockDao = db.blockDao()
        blockDao.update(Block(id = 1, title = "Ważne, Pilne", color = Color.parseColor("#FFCCCC"), priority = 0))
        blockDao.update(Block(id = 2, title = "Ważne, Niepilne", color = Color.parseColor("#FFDDCC"), priority = 1))
        blockDao.update(Block(id = 3, title = "Nieważne, Pilne", color = Color.parseColor("#FFEECC"), priority = 2))
        blockDao.update(Block(id = 4, title = "Nieważne, Niepilne", color = Color.parseColor("#FFFFCC"), priority = 3))
    }

    @Test
    fun testSettingsTitleChangeReflectsInBlockView() {
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
                withId(R.id.titleEditText), withText("Ważne, Niepilne"),
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

        val textView = onView(
            withIndex(
                allOf(
                    withId(R.id.block1), withText("renamed title"),
                    withParent(withParent(withId(R.id.area1))),
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
