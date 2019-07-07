package com.kickstarter.viewmodels

import android.content.SharedPreferences
import android.util.Pair
import androidx.annotation.NonNull
import com.kickstarter.R
import com.kickstarter.libs.*
import com.kickstarter.libs.rx.transformers.Transformers.*
import com.kickstarter.libs.utils.*
import com.kickstarter.libs.utils.ProjectUtils
import com.kickstarter.libs.utils.RefTagUtils
import com.kickstarter.models.Project
import com.kickstarter.models.User
import com.kickstarter.services.ApiClientType
import com.kickstarter.ui.activities.BackingActivity
import com.kickstarter.ui.activities.ProjectActivity
import com.kickstarter.ui.adapters.ProjectAdapter
import com.kickstarter.ui.intentmappers.IntentMapper
import com.kickstarter.ui.intentmappers.ProjectIntentMapper
import com.kickstarter.ui.viewholders.ProjectViewHolder
import rx.Observable
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject
import java.net.CookieManager

interface ProjectViewModel {
    interface Inputs {
        /** Call when the back project button is clicked.  */
        fun backProjectButtonClicked()

        /** Call when the blurb view is clicked.  */
        fun blurbTextViewClicked()

        /** Call when the comments text view is clicked.  */
        fun commentsTextViewClicked()

        /** Call when the creator name is clicked.  */
        fun creatorNameTextViewClicked()

        /** Call when the heart button is clicked.  */
        fun heartButtonClicked()

        /** Call when horizontal rewards fragment should hide. */
        fun hideRewardsFragmentClicked()

        /** Call when the manage pledge button is clicked.  */
        fun managePledgeButtonClicked()

        /** Call when the native_project_action_button is clicked.  */
        fun nativeProjectActionButtonClicked()

        /** Call when the view has been laid out. */
        fun onGlobalLayout()

        /** Call when the play video button is clicked.  */
        fun playVideoButtonClicked()

        /** Call when the share button is clicked.  */
        fun shareButtonClicked()

        /** Call when the updates button is clicked.  */
        fun updatesTextViewClicked()

        /** Call when the view pledge button is clicked.  */
        fun viewPledgeButtonClicked()
    }

    interface Outputs {
        /** Emits a string with the backing details to be displayed on the manage pledge view */
        fun backingDetails(): Observable<String>

        /** Emits a boolean that determines if the backing details should be visible. */
        fun backingDetailsIsVisible(): Observable<Boolean>

        /** Emits a drawable id that corresponds to whether the project is saved. */
        fun heartDrawableId(): Observable<Int>

        /** Emits a project,country, and the native checkout feature flag. If the view model is created with a full project
         * model, this observable will emit that project immediately, and then again when it has updated from the api.*/
        fun projectAndUserCountryAndIsFeatureEnabled(): Observable<Pair<Pair<Project, String>, Boolean>>

        /** Emits the color resource ID for the reward button based on (View, Manage, or Back this project). */
        fun rewardsButtonColor(): Observable<Int>

        /** Emits the proper string resource ID for the reward button. */
        fun rewardsButtonText(): Observable<Int>

        /** Emits the back, manage, view pledge button, or null. */
        fun setActionButtonId(): Observable<Int>

        /** Emits when we should set the Y position of the rewards container. */
        fun setInitialRewardsContainerY(): Observable<Void>

        /** Emits when rewards fragment should expand. */
        fun showRewardsFragment(): Observable<Boolean>

        /** Emits when the success prompt for saving should be displayed.  */
        fun showSavedPrompt(): Observable<Void>

        /** Emits when we should show the share sheet.  */
        fun showShareSheet(): Observable<Project>

        /** Emits when we should start the [BackingActivity].  */
        fun startBackingActivity(): Observable<Pair<Project, User>>

        /** Emits when we should start the campaign [com.kickstarter.ui.activities.WebViewActivity].  */
        fun startCampaignWebViewActivity(): Observable<Project>

        /** Emits when we should start the [com.kickstarter.ui.activities.CheckoutActivity].  */
        fun startCheckoutActivity(): Observable<Project>

        /** Emits when we should start [com.kickstarter.ui.activities.CommentsActivity].  */
        fun startCommentsActivity(): Observable<Project>

        /** Emits when we should start the creator bio [com.kickstarter.ui.activities.CreatorBioActivity].  */
        fun startCreatorBioWebViewActivity(): Observable<Project>

        /** Emits when we should start [com.kickstarter.ui.activities.LoginToutActivity].  */
        fun startLoginToutActivity(): Observable<Void>

        /** Emits when we should start the [com.kickstarter.ui.activities.CheckoutActivity] to manage the pledge.  */
        fun startManagePledgeActivity(): Observable<Project>

        /** Emits when we should start [com.kickstarter.ui.activities.ProjectUpdatesActivity].  */
        fun startProjectUpdatesActivity(): Observable<Project>

        /** Emits when we should start the [com.kickstarter.ui.activities.VideoActivity].  */
        fun startVideoActivity(): Observable<Project>
    }

    class ViewModel(@NonNull val environment: Environment) : ActivityViewModel<ProjectActivity>(environment), ProjectAdapter.Delegate, Inputs, Outputs {
        private val client: ApiClientType = environment.apiClient()
        private val currentUser: CurrentUserType = environment.currentUser()
        private val cookieManager: CookieManager = environment.cookieManager()
        private val currentConfig: CurrentConfigType = environment.currentConfig()
        private val ksCurrency: KSCurrency = environment.ksCurrency()
        private val sharedPreferences: SharedPreferences = environment.sharedPreferences()

        private val backProjectButtonClicked = PublishSubject.create<Void>()
        private val blurbTextViewClicked = PublishSubject.create<Void>()
        private val commentsTextViewClicked = PublishSubject.create<Void>()
        private val creatorNameTextViewClicked = PublishSubject.create<Void>()
        private val heartButtonClicked = PublishSubject.create<Void>()
        private val hideRewardsFragment = PublishSubject.create<Void>()
        private val managePledgeButtonClicked = PublishSubject.create<Void>()
        private val nativeProjectActionButtonClicked = PublishSubject.create<Void>()
        private val onGlobalLayout = PublishSubject.create<Void>()
        private val playVideoButtonClicked = PublishSubject.create<Void>()
        private val shareButtonClicked = PublishSubject.create<Void>()
        private val updatesTextViewClicked = PublishSubject.create<Void>()
        private val viewPledgeButtonClicked = PublishSubject.create<Void>()

        private val backingDetails = BehaviorSubject.create<String>()
        private val backingDetailsIsVisible = BehaviorSubject.create<Boolean>()
        private val heartDrawableId = BehaviorSubject.create<Int>()
        private val projectAndUserCountryAndIsFeatureEnabled = BehaviorSubject.create<Pair<Pair<Project, String>, Boolean>>()
        private val rewardsButtonColor = BehaviorSubject.create<Int>()
        private val rewardsButtonText = BehaviorSubject.create<Int>()
        private val setActionButtonId = BehaviorSubject.create<Int>()
        private val setInitialRewardPosition = BehaviorSubject.create<Void>()
        private val showRewardsFragment = BehaviorSubject.create<Boolean>()
        private val startLoginToutActivity = PublishSubject.create<Void>()
        private val showShareSheet = PublishSubject.create<Project>()
        private val showSavedPrompt = PublishSubject.create<Void>()
        private val startCampaignWebViewActivity = PublishSubject.create<Project>()
        private val startCheckoutActivity = PublishSubject.create<Project>()
        private val startCommentsActivity = PublishSubject.create<Project>()
        private val startCreatorBioWebViewActivity = PublishSubject.create<Project>()
        private val startManagePledgeActivity = PublishSubject.create<Project>()
        private val startProjectUpdatesActivity = PublishSubject.create<Project>()
        private val startVideoActivity = PublishSubject.create<Project>()
        private val startBackingActivity = PublishSubject.create<Pair<Project, User>>()

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {

            val initialProject = intent()
                    .flatMap { i -> ProjectIntentMapper.project(i, this.client) }
                    .share()

            // An observable of the ref tag stored in the cookie for the project. Can emit `null`.
            val cookieRefTag = initialProject
                    .take(1)
                    .map { p -> RefTagUtils.storedCookieRefTagForProject(p, this.cookieManager, this.sharedPreferences) }

            val refTag = intent()
                    .flatMap { ProjectIntentMapper.refTag(it) }

            val pushNotificationEnvelope = intent()
                    .flatMap { ProjectIntentMapper.pushNotificationEnvelope(it) }

            val loggedInUserOnHeartClick = this.currentUser.observable()
                    .compose<User>(takeWhen(this.heartButtonClicked))
                    .filter { u -> u != null }

            val loggedOutUserOnHeartClick = this.currentUser.observable()
                    .compose<User>(takeWhen(this.heartButtonClicked))
                    .filter { u -> u == null }

            val projectOnUserChangeSave = initialProject
                    .compose(takeWhen<Project, User>(loggedInUserOnHeartClick))
                    .switchMap { this.toggleProjectSave(it) }
                    .share()

            loggedOutUserOnHeartClick
                    .compose(ignoreValues())
                    .subscribe(this.startLoginToutActivity)

            val savedProjectOnLoginSuccess = this.startLoginToutActivity
                    .compose<Pair<Void, User>>(combineLatestPair(this.currentUser.observable()))
                    .filter { su -> su.second != null }
                    .withLatestFrom<Project, Project>(initialProject) { _, p -> p }
                    .take(1)
                    .switchMap { this.saveProject(it) }
                    .share()

            val currentProject = Observable.merge(
                    initialProject,
                    projectOnUserChangeSave,
                    savedProjectOnLoginSuccess
            )

            val horizontalRewardsEnabled = Observable.just(environment.horizontalRewardsEnabled().get())

            projectOnUserChangeSave.mergeWith(savedProjectOnLoginSuccess)
                    .filter { p -> p.isStarred && p.isLive && !p.isApproachingDeadline }
                    .compose(ignoreValues())
                    .subscribe(this.showSavedPrompt)

            currentProject
                    .compose<Pair<Project, String>>(combineLatestPair(this.currentConfig.observable().map { it.countryCode() }))
                    .compose<Pair<Pair<Project, String>, Boolean>>(combineLatestPair(horizontalRewardsEnabled))
                    .subscribe(this.projectAndUserCountryAndIsFeatureEnabled)

            this.projectAndUserCountryAndIsFeatureEnabled
                    .filter { BooleanUtils.isTrue(it.second) }
                    .map<Project> { it.first.first }
                    .map { it.isBacking && it.isLive }
                    .distinctUntilChanged()
                    .compose(bindToLifecycle())
                    .subscribe(this.backingDetailsIsVisible)

            currentProject
                    .compose<Project>(takeWhen(this.shareButtonClicked))
                    .subscribe(this.showShareSheet)

            currentProject
                    .compose<Project>(takeWhen(this.blurbTextViewClicked))
                    .subscribe(this.startCampaignWebViewActivity)

            currentProject
                    .compose<Project>(takeWhen(this.backProjectButtonClicked))
                    .subscribe(this.startCheckoutActivity)

            currentProject
                    .compose<Project>(takeWhen(this.creatorNameTextViewClicked))
                    .subscribe(this.startCreatorBioWebViewActivity)

            currentProject
                    .compose<Project>(takeWhen(this.commentsTextViewClicked))
                    .subscribe(this.startCommentsActivity)

            currentProject
                    .compose<Project>(takeWhen(this.managePledgeButtonClicked))
                    .subscribe(this.startManagePledgeActivity)

            currentProject
                    .compose<Project>(takeWhen(this.updatesTextViewClicked))
                    .subscribe(this.startProjectUpdatesActivity)

            currentProject
                    .compose<Project>(takeWhen(this.playVideoButtonClicked))
                    .subscribe(this.startVideoActivity)

            Observable.combineLatest<Project, User, Pair<Project, User>>(currentProject, this.currentUser.observable())
            { project, user -> Pair.create(project, user) }
                    .compose<Pair<Project, User>>(takeWhen(this.viewPledgeButtonClicked))
                    .subscribe(this.startBackingActivity)

            this.onGlobalLayout
                    .compose(bindToLifecycle())
                    .subscribe(this.setInitialRewardPosition)

            this.nativeProjectActionButtonClicked
                    .map { true }
                    .compose(bindToLifecycle())
                    .subscribe(this.showRewardsFragment)

            this.hideRewardsFragment
                    .map { false }
                    .compose(bindToLifecycle())
                    .subscribe(this.showRewardsFragment)

            this.projectAndUserCountryAndIsFeatureEnabled
                    .filter { BooleanUtils.isTrue(it.first.first.isBacking) && BooleanUtils.isTrue(it.second) }
                    .map { setBackingDetails(it.first.first) }
                    .distinctUntilChanged()
                    .compose(bindToLifecycle())
                    .subscribe(this.backingDetails)

            this.showShareSheet
                    .compose(bindToLifecycle())
                    .subscribe { this.koala.trackShowProjectShareSheet(it) }

            this.startVideoActivity
                    .compose(bindToLifecycle())
                    .subscribe { this.koala.trackVideoStart(it) }

            currentProject
                    .map { p -> if (p.isStarred) R.drawable.icon__heart else R.drawable.icon__heart_outline }
                    .subscribe(this.heartDrawableId)

            projectOnUserChangeSave
                    .mergeWith(savedProjectOnLoginSuccess)
                    .compose(bindToLifecycle())
                    .subscribe { this.koala.trackProjectStar(it) }

            Observable.combineLatest<RefTag, RefTag, Project, RefTagsAndProject>(refTag, cookieRefTag, currentProject)
            { refTagFromIntent, refTagFromCookie, project -> RefTagsAndProject(refTagFromIntent, refTagFromCookie, project) }
                    .take(1)
                    .compose(bindToLifecycle())
                    .subscribe { data ->
                        // If a cookie hasn't been set for this ref+project then do so.
                        if (data.refTagFromCookie == null && data.refTagFromIntent != null) {
                            RefTagUtils.storeCookie(data.refTagFromIntent, data.project, this.cookieManager, this.sharedPreferences)
                        }

                        this.koala.trackProjectShow(
                                data.project,
                                data.refTagFromIntent,
                                RefTagUtils.storedCookieRefTagForProject(data.project, this.cookieManager, this.sharedPreferences)
                        )
                    }

            pushNotificationEnvelope
                    .take(1)
                    .compose(bindToLifecycle())
                    .subscribe { this.koala.trackPushNotification(it) }

            intent()
                    .filter { IntentMapper.appBannerIsSet(it) }
                    .compose(bindToLifecycle())
                    .subscribe { _ -> this.koala.trackOpenedAppBanner() }

            currentProject
                    .map { getActionButtons(it) }
                    .take(1)
                    .compose(bindToLifecycle())
                    .subscribe { this.setActionButtonId.onNext(it) }

            currentProject
                    .map { getRewardButtonText(it) }
                    .distinctUntilChanged()
                    .compose(bindToLifecycle())
                    .subscribe { this.rewardsButtonText.onNext(it) }

            currentProject
                    .map { ProjectUtils.pledgeButtonColor(it) }
                    .distinctUntilChanged()
                    .compose(bindToLifecycle())
                    .subscribe(this.rewardsButtonColor)

        }

        /**
         * A light-weight value to hold two ref tags and a project. Two ref tags are stored: one comes from parceled
         * data in the activity and the other comes from the ref stored in a cookie associated to the project.
         */
        private inner class RefTagsAndProject internal constructor(val refTagFromIntent: RefTag?, val refTagFromCookie: RefTag?,
                                                                   val project: Project)

        override fun backProjectButtonClicked() {
            this.backProjectButtonClicked.onNext(null)
        }

        override fun blurbTextViewClicked() {
            this.blurbTextViewClicked.onNext(null)
        }

        override fun commentsTextViewClicked() {
            this.commentsTextViewClicked.onNext(null)
        }

        override fun creatorNameTextViewClicked() {
            this.creatorNameTextViewClicked.onNext(null)
        }

        override fun heartButtonClicked() {
            this.heartButtonClicked.onNext(null)
        }

        override fun hideRewardsFragmentClicked() {
            this.hideRewardsFragment.onNext(null)
        }

        override fun managePledgeButtonClicked() {
            this.managePledgeButtonClicked.onNext(null)
        }

        override fun nativeProjectActionButtonClicked() {
            this.nativeProjectActionButtonClicked.onNext(null)
        }

        override fun onGlobalLayout() {
            this.onGlobalLayout.onNext(null)
        }

        override fun playVideoButtonClicked() {
            this.playVideoButtonClicked.onNext(null)
        }

        override fun projectViewHolderBackProjectClicked(viewHolder: ProjectViewHolder) {
            this.backProjectButtonClicked()
        }

        override fun projectViewHolderBlurbClicked(viewHolder: ProjectViewHolder) {
            this.blurbTextViewClicked()
        }

        override fun projectViewHolderCommentsClicked(viewHolder: ProjectViewHolder) {
            this.commentsTextViewClicked()
        }

        override fun projectViewHolderCreatorClicked(viewHolder: ProjectViewHolder) {
            this.creatorNameTextViewClicked()
        }

        override fun projectViewHolderManagePledgeClicked(viewHolder: ProjectViewHolder) {
            this.managePledgeButtonClicked()
        }

        override fun projectViewHolderVideoStarted(viewHolder: ProjectViewHolder) {
            this.playVideoButtonClicked()
        }

        override fun projectViewHolderViewPledgeClicked(viewHolder: ProjectViewHolder) {
            this.viewPledgeButtonClicked()
        }

        override fun projectViewHolderUpdatesClicked(viewHolder: ProjectViewHolder) {
            this.updatesTextViewClicked()
        }

        override fun shareButtonClicked() {
            this.shareButtonClicked.onNext(null)
        }

        override fun showRewardsFragment(): Observable<Boolean> {
            return this.showRewardsFragment
        }

        override fun updatesTextViewClicked() {
            this.updatesTextViewClicked.onNext(null)
        }

        override fun viewPledgeButtonClicked() {
            this.viewPledgeButtonClicked.onNext(null)
        }

        override fun heartDrawableId(): Observable<Int> {
            return this.heartDrawableId
        }

        override fun projectAndUserCountryAndIsFeatureEnabled(): Observable<Pair<Pair<Project, String>, Boolean>> {
            return this.projectAndUserCountryAndIsFeatureEnabled
        }

        override fun rewardsButtonColor(): Observable<Int> = this.rewardsButtonColor

        override fun rewardsButtonText(): Observable<Int> = this.rewardsButtonText

        @NonNull
        override fun backingDetailsIsVisible(): Observable<Boolean> = this.backingDetailsIsVisible

        @NonNull
        override fun backingDetails(): Observable<String> = this.backingDetails

        @NonNull
        override fun setActionButtonId(): Observable<Int> = this.setActionButtonId

        @NonNull
        override fun setInitialRewardsContainerY(): Observable<Void> = this.setInitialRewardPosition

        @NonNull
        override fun showSavedPrompt(): Observable<Void> = this.showSavedPrompt

        @NonNull
        override fun showShareSheet(): Observable<Project> = this.showShareSheet

        @NonNull
        override fun startBackingActivity(): Observable<Pair<Project, User>> = this.startBackingActivity

        @NonNull
        override fun startCampaignWebViewActivity(): Observable<Project> = this.startCampaignWebViewActivity

        @NonNull
        override fun startCheckoutActivity(): Observable<Project> = this.startCheckoutActivity

        @NonNull
        override fun startCommentsActivity(): Observable<Project> = this.startCommentsActivity

        @NonNull
        override fun startCreatorBioWebViewActivity(): Observable<Project> = this.startCreatorBioWebViewActivity

        @NonNull
        override fun startLoginToutActivity(): Observable<Void> = this.startLoginToutActivity

        @NonNull
        override fun startManagePledgeActivity(): Observable<Project> = this.startManagePledgeActivity

        @NonNull
        override fun startProjectUpdatesActivity(): Observable<Project> = this.startProjectUpdatesActivity

        @NonNull
        override fun startVideoActivity(): Observable<Project> = this.startVideoActivity

        private fun getActionButtons(project: Project): Int? {
            return if (!project.isBacking && project.isLive) {
                R.id.back_project_button
            } else if (project.isBacking && project.isLive) {
                R.id.manage_pledge_button
            } else if (project.isBacking && !project.isLive) {
                R.id.view_pledge_button
            } else {
                return null
            }
        }

        private fun getRewardButtonText(project: Project): Int? {
            return if (!project.isBacking && project.isLive) {
                R.string.Back_this_project
            } else if (project.isBacking && project.isLive) {
                R.string.Manage
            } else if (project.isBacking && !project.isLive) {
                R.string.View_your_pledge
            } else {
                return null
            }
        }

        private fun setBackingDetails(project: Project): String {
            val backing = project.backing()
            when {
                backing != null -> backing.let {
                    val backingAmount = it.amount() - it.shippingAmount()
                    val reward = project.rewards()?.firstOrNull { it.id() == backing.rewardId() }
                    val title = reward?.let { "• ${it.title()}" }?: ""
                    val formattedAmount = this.ksCurrency.format(backingAmount, project)

                    return "$formattedAmount $title"
                }
                else -> return ""
            }
        }

        private fun saveProject(project: Project): Observable<Project> {
            return this.client.saveProject(project)
                    .compose(neverError())
        }

        private fun toggleProjectSave(project: Project): Observable<Project> {
            return this.client.toggleProjectSave(project)
                    .compose(neverError())
        }
    }
}
