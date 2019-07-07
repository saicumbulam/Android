package com.kickstarter.ui.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.kickstarter.R
import com.kickstarter.libs.BaseActivity
import com.kickstarter.libs.Build
import com.kickstarter.libs.CurrentUserType
import com.kickstarter.libs.qualifiers.RequiresActivityViewModel
import com.kickstarter.libs.utils.Secrets
import com.kickstarter.models.User
import com.kickstarter.models.chrome.ChromeTabsHelperActivity
import com.kickstarter.ui.activities.HelpActivity.*
import com.kickstarter.viewmodels.HelpSettingsViewModel
import kotlinx.android.synthetic.main.activity_help_settings.*
import rx.android.schedulers.AndroidSchedulers
import java.util.*


@RequiresActivityViewModel(HelpSettingsViewModel.ViewModel::class)
class HelpSettingsActivity : BaseActivity<HelpSettingsViewModel.ViewModel>() {

    private lateinit var build: Build
    private lateinit var currentUser: CurrentUserType

    private val mailto = R.string.mailto
    private val supportEmail = R.string.support_email_to_android
    private val supportEmailBody = R.string.support_email_body
    private val supportEmailSubject = R.string.support_email_subject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_settings)

        this.build = environment().build()
        this.currentUser = environment().currentUser()

        contact.setOnClickListener {
            this.viewModel.contactClicked()

            this.currentUser.observable()
                    .take(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::composeContactEmail)
        }

        cookie_policy.setOnClickListener {
            startChromeTab(buildHelpUrl(COOKIES), Intent(this, HelpActivity.CookiePolicy::class.java))
        }

        help_center.setOnClickListener {
            startChromeTab(Secrets.HelpCenter.ENDPOINT, null)
        }

        privacy_policy.setOnClickListener {
            startChromeTab(buildHelpUrl(PRIVACY), Intent(this, HelpActivity.PRIVACY::class.java))
        }

        terms_of_use.setOnClickListener {
            startChromeTab(buildHelpUrl(TERMS_OF_USE), Intent(this, HelpActivity.Terms::class.java))
        }
    }

    private fun buildHelpUrl(endpoint: String): String {
        val webEndpointBuilder = Uri.parse(this.environment().webEndpoint()).buildUpon()
        webEndpointBuilder.appendEncodedPath(endpoint)

        return webEndpointBuilder.build().toString()
    }

    private fun composeContactEmail(user: User?) {
        val debugInfo = Arrays.asList(
                user?.id()?: getString(R.string.Logged_Out),
                this.build.versionName(),
                android.os.Build.VERSION.RELEASE + " (SDK " + Integer.toString(android.os.Build.VERSION.SDK_INT) + ")",
                android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL
        )
        val body = StringBuilder()
                .append(getString(this.supportEmailBody))
                .append(TextUtils.join(" | ", debugInfo))
                .toString()
        val intent = Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse(getString(this.mailto)))
                .putExtra(Intent.EXTRA_SUBJECT, "[Android] " + getString(this.supportEmailSubject))
                .putExtra(Intent.EXTRA_TEXT, body)
                .putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(getString(this.supportEmail)))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, getString(R.string.support_email_chooser)))
        }
    }

    private fun startChromeTab(url: String, helpActivityIntent: Intent?) {
        val uri = Uri.parse(url)
        val builder = CustomTabsIntent.Builder()

        builder.setShowTitle(true)
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.primary))

        val customTabsIntent = builder.build()

        ChromeTabsHelperActivity.openCustomTab(this, customTabsIntent, uri, object :
                ChromeTabsHelperActivity.CustomTabFallback {

            override fun openUri(activity: Activity, uri: Uri) {
                if (helpActivityIntent != null) {
                    activity.startActivity(helpActivityIntent)
                } else {
                    // We're not handling users without browsers, We'll monitor on Fabric.
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    activity.startActivity(intent)
                }
            }
        })
    }

}
