package com.kickstarter.viewmodels

import android.content.Intent
import com.kickstarter.KSRobolectricTestCase
import com.kickstarter.libs.Environment
import com.kickstarter.mock.factories.ApiExceptionFactory
import com.kickstarter.mock.services.MockApiClient
import com.kickstarter.services.apiresponses.AccessTokenEnvelope
import com.kickstarter.ui.IntentKey
import com.kickstarter.ui.data.LoginReason
import org.junit.Test
import rx.Observable
import rx.observers.TestSubscriber

class LoginViewModelTest : KSRobolectricTestCase() {
    private lateinit var vm: LoginViewModel.ViewModel
    private val genericLoginError = TestSubscriber<String>()
    private val invalidLoginError = TestSubscriber<String>()
    private val logInButtonIsEnabled = TestSubscriber<Boolean>()
    private val loginSuccess = TestSubscriber<Void>()
    private val preFillEmail = TestSubscriber<String>()
    private val showChangedPasswordSnackbar = TestSubscriber<Void>()
    private val showResetPasswordSuccessDialog = TestSubscriber<Boolean>()
    private val tfaChallenge = TestSubscriber<Void>()

    fun setUpEnvironment(environment: Environment) {
        this.vm = LoginViewModel.ViewModel(environment)
        this.vm.outputs.genericLoginError().subscribe(this.genericLoginError)
        this.vm.outputs.invalidLoginError().subscribe(this.invalidLoginError)
        this.vm.outputs.loginButtonIsEnabled().subscribe(this.logInButtonIsEnabled)
        this.vm.outputs.loginSuccess().subscribe(this.loginSuccess)
        this.vm.outputs.prefillEmail().subscribe(this.preFillEmail)
        this.vm.outputs.showChangedPasswordSnackbar().subscribe(this.showChangedPasswordSnackbar)
        this.vm.outputs.showResetPasswordSuccessDialog()
                .map { showAndEmail -> showAndEmail.first }
                .subscribe(this.showResetPasswordSuccessDialog)
        this.vm.outputs.tfaChallenge().subscribe(this.tfaChallenge)
    }

    @Test
    fun testLoginButtonEnabled() {
        setUpEnvironment(environment())

        // Button should not be enabled until both a valid email and password are entered.
        this.vm.inputs.email("hello")
        this.logInButtonIsEnabled.assertNoValues()

        this.vm.inputs.email("hello@kickstarter.com")
        this.logInButtonIsEnabled.assertNoValues()

        this.vm.inputs.password("")
        this.logInButtonIsEnabled.assertValues(false)

        this.vm.inputs.password("izzyiscool")
        this.logInButtonIsEnabled.assertValues(false, true)
    }

    @Test
    fun testLoginApiError() {
        val apiClient = object : MockApiClient() {
            override fun login(email: String, password: String): Observable<AccessTokenEnvelope> {
                return Observable.error(ApiExceptionFactory.badRequestException())
            }
        }

        setUpEnvironment(environment().toBuilder().apiClient(apiClient).build())

        this.vm.inputs.email("incorrect@kickstarter.com")
        this.vm.inputs.password("lisaiscool")

        this.vm.inputs.loginClick()

        this.loginSuccess.assertNoValues()
        this.genericLoginError.assertValueCount(1)
    }

    @Test
    fun testLoginApiValidationError() {
        val apiClient = object : MockApiClient() {
            override fun login(email: String, password: String): Observable<AccessTokenEnvelope> {
                return Observable.error(ApiExceptionFactory.invalidLoginException())
            }
        }

        setUpEnvironment(environment().toBuilder().apiClient(apiClient).build())

        this.vm.inputs.email("typo@kickstartr.com")
        this.vm.inputs.password("julieiscool")

        this.vm.inputs.loginClick()

        this.loginSuccess.assertNoValues()
        this.invalidLoginError.assertValueCount(1)
    }

    @Test
    fun testLoginTfaChallenge() {
        val apiClient = object : MockApiClient() {
            override fun login(email: String, password: String): Observable<AccessTokenEnvelope> {
                return Observable.error(ApiExceptionFactory.tfaRequired())
            }
        }

        setUpEnvironment(environment().toBuilder().apiClient(apiClient).build())

        this.vm.inputs.email("hello@kickstarter.com")
        this.vm.inputs.password("androidiscool")

        this.vm.inputs.loginClick()

        this.loginSuccess.assertNoValues()
        this.tfaChallenge.assertValueCount(1)
    }

    @Test
    fun testPrefillEmail() {
        setUpEnvironment(environment())

        // Start the view model with an email to prefill.
        this.vm.intent(Intent().putExtra(IntentKey.EMAIL, "hello@kickstarter.com"))

        this.preFillEmail.assertValue("hello@kickstarter.com")
        this.showResetPasswordSuccessDialog.assertNoValues()
        this.showChangedPasswordSnackbar.assertNoValues()
    }

    @Test
    fun testPrefillEmailAndDialog() {
        setUpEnvironment(environment())

        // Start the view model with an email to prefill.
        this.vm.intent(Intent().putExtra(IntentKey.EMAIL, "hello@kickstarter.com").putExtra(IntentKey.LOGIN_REASON, LoginReason.RESET_PASSWORD))

        this.preFillEmail.assertValue("hello@kickstarter.com")
        this.showChangedPasswordSnackbar.assertNoValues()
        this.showResetPasswordSuccessDialog.assertValue(true)

        // Dismiss the confirmation dialog.
        this.vm.inputs.resetPasswordConfirmationDialogDismissed()
        this.showChangedPasswordSnackbar.assertNoValues()
        this.showResetPasswordSuccessDialog.assertValues(true, false)

        // Simulate rotating the device, first by sending a new intent (similar to what happens after rotation).
        this.vm.intent(Intent().putExtra(IntentKey.EMAIL, "hello@kickstarter.com"))

        // Create new test subscribers – this emulates a new activity subscribing to the vm's outputs.
        val rotatedPrefillEmail = TestSubscriber<String>()
        this.vm.outputs.prefillEmail().subscribe(rotatedPrefillEmail)
        val rotatedShowChangedPasswordSnackbar = TestSubscriber<Void>()
        this.vm.outputs.showChangedPasswordSnackbar().subscribe(rotatedShowChangedPasswordSnackbar)
        val rotatedShowResetPasswordSuccessDialog = TestSubscriber<Boolean>()
        this.vm.outputs.showResetPasswordSuccessDialog()
                .map { showAndEmail -> showAndEmail.first }
                .subscribe(rotatedShowResetPasswordSuccessDialog)

        // Email should still be pre-filled.
        rotatedPrefillEmail.assertValue("hello@kickstarter.com")

        // Dialog should not be shown again – the user has already dismissed it.
        rotatedShowResetPasswordSuccessDialog.assertValue(false)

        // Snackbar should not be shown.
        rotatedShowChangedPasswordSnackbar.assertNoValues()
    }

    @Test
    fun testPrefillEmailAndSnackbar() {
        setUpEnvironment(environment())

        // Start the view model with an email to prefill.
        this.vm.intent(Intent().putExtra(IntentKey.EMAIL, "hello@kickstarter.com").putExtra(IntentKey.LOGIN_REASON, LoginReason.CHANGE_PASSWORD))

        this.preFillEmail.assertValue("hello@kickstarter.com")
        this.showResetPasswordSuccessDialog.assertNoValues()

        // Simulate rotating the device, first by sending a new intent (similar to what happens after rotation).
        this.vm.intent(Intent().putExtra(IntentKey.EMAIL, "hello@kickstarter.com").putExtra(IntentKey.LOGIN_REASON, LoginReason.CHANGE_PASSWORD))

        // Create new test subscribers – this emulates a new activity subscribing to the vm's outputs.
        val rotatedPrefillEmail = TestSubscriber<String>()
        this.vm.outputs.prefillEmail().subscribe(rotatedPrefillEmail)
        val rotatedShowChangedPasswordSnackbar = TestSubscriber<Void>()
        this.vm.outputs.showChangedPasswordSnackbar().subscribe(rotatedShowChangedPasswordSnackbar)
        val rotatedShowResetPasswordSuccessDialog = TestSubscriber<Boolean>()
        this.vm.outputs.showResetPasswordSuccessDialog()
                .map { showAndEmail -> showAndEmail.first }
                .subscribe(rotatedShowResetPasswordSuccessDialog)

        // Email should still be pre-filled.
        rotatedPrefillEmail.assertValue("hello@kickstarter.com")
        rotatedShowChangedPasswordSnackbar.assertValueCount(1)

        // Dialog should not be shown.
        rotatedShowResetPasswordSuccessDialog.assertNoValues()
    }

    @Test
    fun testSuccessfulLogin() {
        this.vm = LoginViewModel.ViewModel(environment())
        this.vm.outputs.loginSuccess().subscribe(this.loginSuccess)

        this.vm.inputs.email("hello@kickstarter.com")
        this.vm.inputs.password("codeisawesome")

        this.vm.inputs.loginClick()

        this.loginSuccess.assertValueCount(1)
        this.koalaTest.assertValues("Login")
    }
}
