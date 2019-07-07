package com.kickstarter.services

import CreatePasswordMutation
import DeletePaymentSourceMutation
import SavePaymentMethodMutation
import SendEmailVerificationMutation
import UpdateUserCurrencyMutation
import UpdateUserEmailMutation
import UpdateUserPasswordMutation
import UserPrivacyQuery
import com.kickstarter.models.Project
import com.kickstarter.models.Reward
import com.kickstarter.models.StoredCard
import com.kickstarter.models.User
import rx.Observable
import type.CurrencyCode
import type.PaymentTypes

interface ApolloClientType {
    fun checkout(project: Project, amount: String, paymentSourceId: String, locationId: String?, reward: Reward?): Observable<Boolean>

    fun clearUnseenActivity(): Observable<Long>

    fun createPassword(password: String, confirmPassword: String): Observable<CreatePasswordMutation.Data>

    fun deletePaymentSource(paymentSourceId: String): Observable<DeletePaymentSourceMutation.Data>

    fun getStoredCards(): Observable<List<StoredCard>>

    fun savePaymentMethod(paymentTypes: PaymentTypes, stripeToken: String, cardId: String): Observable<SavePaymentMethodMutation.Data>

    fun sendMessage(project: Project, recipient: User, body: String): Observable<Long>

    fun sendVerificationEmail(): Observable<SendEmailVerificationMutation.Data>

    fun updateUserCurrencyPreference(currency: CurrencyCode): Observable<UpdateUserCurrencyMutation.Data>

    fun updateUserEmail(email: String, currentPassword: String): Observable<UpdateUserEmailMutation.Data>

    fun updateUserPassword(currentPassword: String, newPassword: String, confirmPassword: String): Observable<UpdateUserPasswordMutation.Data>

    fun userPrivacy(): Observable<UserPrivacyQuery.Data>
}
