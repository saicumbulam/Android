
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.kickstarter.R
import com.kickstarter.ui.activities.HelpSettingsActivity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import utils.Events


@RunWith(AndroidJUnit4::class)
class HelpSettingsActivityTest {

    private val events = Events()

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(HelpSettingsActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testContactClick() {
        events.clickOnView(R.id.contact)
        intended(allOf(
                IntentMatchers.hasAction(Intent.ACTION_CHOOSER)))
    }

    @Test
    fun testCookiePolicyClick() {
        events.clickOnView(R.id.cookie_policy)
        intended(allOf(
                IntentMatchers.hasAction(Intent.ACTION_VIEW),
                IntentMatchers.hasData("https://www.kickstarter.com/cookies")))
    }

    @Test
    fun testHelpCenterClick() {
        events.clickOnView(R.id.help_center)
        intended(allOf(
                IntentMatchers.hasAction(Intent.ACTION_VIEW),
                IntentMatchers.hasData("https://www.kickstarter.com/help")))
    }

    @Test
    fun testPrivacyPolicyClick() {
        events.clickOnView(R.id.privacy_policy)
        intended(allOf(
                IntentMatchers.hasAction(Intent.ACTION_VIEW),
                IntentMatchers.hasData("https://www.kickstarter.com/privacy")))
    }

    @Test
    fun testTermsClick() {
        events.clickOnView(R.id.terms_of_use)

        intended(allOf(
                IntentMatchers.hasAction(Intent.ACTION_VIEW),
                IntentMatchers.hasData("https://www.kickstarter.com/terms-of-use")))
    }

    @Test
    fun testToolbarColor() {
        val color = R.color.primary
        val intent = CustomTabsIntent.Builder().setToolbarColor(color).build().intent
        assertTrue(intent.hasExtra(CustomTabsIntent.EXTRA_TOOLBAR_COLOR))
        assertEquals(color, intent.getIntExtra(CustomTabsIntent.EXTRA_TOOLBAR_COLOR, 0))
    }
}
