package br.com.cubos.challenge.android.moviescatalog

import android.content.Intent
import android.content.pm.PackageManager
import android.widget.TextView
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class SimpleInstrumentedTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)
    private lateinit var uiDevice: UiDevice

    @Before
    fun setup() {
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        // Start from the home screen
        uiDevice.pressHome()

        uiDevice.wait(Until.hasObject(By.pkg(getLauncherPackageName()).depth(0)), 1000)
    }

    private fun getLauncherPackageName(): String? {
        // Create launcher Intent
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)

        // Use PackageManager to get the launcher package name
        val pm: PackageManager = InstrumentationRegistry.getInstrumentation().context.packageManager
        val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)

        return resolveInfo.activityInfo.packageName
    }

    @Test
    fun checkTabs() {
        uiDevice.pressHome()

        val allAppsButton: UiObject = uiDevice.findObject(UiSelector().description("Apps"))

        allAppsButton.clickAndWaitForNewWindow()

        val appViews = UiScrollable(
            UiSelector().scrollable(true))

        appViews.setAsVerticalList()

        val searchOnListViewer = appViews
            .getChildByText(
                UiSelector().className(TextView::class.java.name),
                activityRule.activity.resources.getString(R.string.app_name),
                true
            )

        searchOnListViewer.clickAndWaitForNewWindow()

        val catalogValidation = uiDevice
            .findObject(
                UiSelector()
                    .packageName(activityRule.activity.applicationContext.packageName)
            )

        assertTrue(catalogValidation.exists())

        val searchIcon = uiDevice.findObject(
            UiSelector()
                .resourceId("br.com.cubos.challenge.android.myapplication:id/menu_search_item"))

        searchIcon.clickAndWaitForNewWindow()

        val searchButton = uiDevice.findObject(
            UiSelector()
                .resourceId("android:id/search_button"))

        searchButton.clickAndWaitForNewWindow()

        val fantasyTab = uiDevice.findObject(
            UiSelector()
                .text("FANTASIA"))

        assertTrue(fantasyTab.exists())

        fantasyTab.clickAndWaitForNewWindow()
    }
}