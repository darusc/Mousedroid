package com.darusc.mousedroid

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.darusc.mousedroid.fragments.DeviceList
import com.darusc.mousedroid.networking.Connection
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeviceListScopeTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val preferences = context.getSharedPreferences("devices", Context.MODE_PRIVATE)
    private val serverName = "DeviceListScopeTest server"

    @Before
    fun removeTestServer() {
        preferences.edit().remove(serverName).commit()
    }

    @After
    fun cleanUp() = removeTestServer()

    private fun modeArguments(mode: Connection.Mode) = Bundle().apply {
        putSerializable("CONNECTION_MODE", mode)
    }

    private fun replaceList(scenario: FragmentScenario<DeviceList>, mode: Connection.Mode) {
        scenario.onFragment { current ->
            val next = DeviceList().apply { arguments = modeArguments(mode) }
            // Reuse the same Activity: separate activities would hide the original bug.
            current.parentFragmentManager.beginTransaction()
                .replace(current.id, next, current.tag)
                .commitNow()
        }
    }

    @Test
    fun bluetoothThenWifiCanSaveAndRetainServer() {
        // FragmentScenario has no MainActivity Bluetooth initialization. The
        // Bluetooth list is empty, and this test needs no radio or paired device.
        launchFragmentInContainer<DeviceList>(
            fragmentArgs = modeArguments(Connection.Mode.BLUETOOTH),
            themeResId = R.style.Theme_Mousedroid
        ).use { scenario ->
            replaceList(scenario, Connection.Mode.WIFI)
            onView(withId(R.id.btnAddDevice)).perform(click())
            onView(withId(R.id.textName)).perform(replaceText(serverName))
            onView(withId(R.id.textAddress)).perform(replaceText("192.0.2.10"), closeSoftKeyboard())
            onView(withId(R.id.deviceAddConfirm)).perform(click())
            onView(withText(serverName)).check(matches(isDisplayed()))

            scenario.recreate()
            onView(withText(serverName)).check(matches(isDisplayed()))
            replaceList(scenario, Connection.Mode.BLUETOOTH)
            onView(withText(serverName)).check(doesNotExist())
            replaceList(scenario, Connection.Mode.WIFI)
            onView(withText(serverName)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun wifiThenBluetoothDoesNotReuseSavedServers() {
        preferences.edit().putString(serverName, "192.0.2.10").commit()
        launchFragmentInContainer<DeviceList>(
            fragmentArgs = modeArguments(Connection.Mode.WIFI),
            themeResId = R.style.Theme_Mousedroid
        ).use { scenario ->
            onView(withText(serverName)).check(matches(isDisplayed()))
            replaceList(scenario, Connection.Mode.BLUETOOTH)
            onView(withText(serverName)).check(doesNotExist())
            replaceList(scenario, Connection.Mode.WIFI)
            onView(withText(serverName)).check(matches(isDisplayed()))
        }
    }
}
