package com.kickstarter.libs;

import com.kickstarter.libs.utils.KoalaUtils;
import com.kickstarter.models.Activity;
import com.kickstarter.models.Project;
import com.kickstarter.models.Update;
import com.kickstarter.models.User;
import com.kickstarter.services.DiscoveryParams;
import com.kickstarter.services.apiresponses.PushNotificationEnvelope;
import com.kickstarter.ui.data.LoginReason;
import com.kickstarter.ui.data.Mailbox;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class Koala {
  private final @NonNull TrackingClientType client;

  public Koala(final @NonNull TrackingClientType client) {
    this.client = client;
  }

  public @NonNull TrackingClientType client() {
    return this.client;
  }

  // APPLICATION LIFECYCLE
  public void trackAppOpen() {
    this.client.track("App Open");
  }

  public void trackAppClose() {
    this.client.track("App Close");
  }

  public void trackMemoryWarning() {
    this.client.track("App Memory Warning");
  }

  public void trackOpenedAppBanner() {
    this.client.track("Opened App Banner");
  }

  // ANDROID PAY
  public void trackShowAndroidPaySheet() {
    this.client.track("Android Pay Show Sheet");
  }

  public void trackAndroidPayFinished() {
    this.client.track("Android Pay Finished");
  }

  public void trackAndroidPayCanceled() {
    this.client.track("Android Pay Canceled");
  }

  // BACKING
  public void trackViewedPledgeInfo(final @NonNull Project project) {
    this.client.track(KoalaEvent.VIEWED_PLEDGE_INFO, KoalaUtils.projectProperties(project, this.client.loggedInUser()));
  }

  // DISCOVERY
  public void trackDiscovery(final @NonNull DiscoveryParams params, final boolean isOnboardingVisible) {
    final Map<String, Object> props = KoalaUtils.discoveryParamsProperties(params);
    props.put("discover_onboarding_is_visible", isOnboardingVisible);
    this.client.track("Discover List View", props);
  }

  public void trackDiscoveryFilters() {
    this.client.track("Discover Switch Modal", new HashMap<String, Object>() {
      {
        put("modal_type", "filters");
      }
    });
  }

  public void trackDiscoveryFilterSelected(final @NonNull DiscoveryParams params) {
    this.client.track("Discover Modal Selected Filter", KoalaUtils.discoveryParamsProperties(params));
  }

  public void trackDiscoveryRefreshTriggered() {
    this.client.track(KoalaEvent.TRIGGERED_REFRESH, new HashMap<String, Object>() {
      {
        put("type", "swipe");
      }
    });
  }

  /**
   * Tracks a project show event.
   *
   * @param intentRefTag (nullable) The ref tag present in the activity upon displaying the project.
   * @param cookieRefTag (nullable) The ref tag extracted from the cookie store upon viewing the project.
   */
  public void trackProjectShow(final @NonNull Project project, final @Nullable RefTag intentRefTag, final @Nullable RefTag cookieRefTag) {

    final Map<String, Object> properties = KoalaUtils.projectProperties(project, this.client.loggedInUser());

    if (intentRefTag != null) {
      properties.put("ref_tag", intentRefTag.tag());
    }

    if (cookieRefTag != null) {
      properties.put("referrer_credit", cookieRefTag.tag());
    }

    // Deprecated event
    this.client.track(KoalaEvent.PROJECT_PAGE, properties);

    this.client.track(KoalaEvent.VIEWED_PROJECT_PAGE, properties);
  }

  // PROJECT STAR
  public void trackProjectStar(final @NonNull Project project) {
    final Map<String, Object> props = KoalaUtils.projectProperties(project, this.client.loggedInUser());

    // Deprecated events
    this.client.track(project.isStarred() ? KoalaEvent.PROJECT_STAR : KoalaEvent.PROJECT_UNSTAR, props);

    this.client.track(project.isStarred() ? KoalaEvent.STARRED_PROJECT : KoalaEvent.UNSTARRED_PROJECT, props);
  }

  // PROJECT CREATOR BIO
  public void trackViewedCreatorBioModal(final @NonNull Project project) {
    final User loggedInUser = this.client.loggedInUser();
    final Map<String, Object> props = KoalaUtils.projectProperties(project, loggedInUser);
    props.put("modal_title", "creatorBioModal");

    this.client.track(KoalaEvent.MODAL_DIALOG_VIEW, props);
  }

  public void trackViewedMessageCreatorModal(final @NonNull Project project) {
    final User loggedInUser = this.client.loggedInUser();
    final Map<String, Object> props = KoalaUtils.projectProperties(project, loggedInUser);
    props.put("modal_title", "messageCreatorModal");

    this.client.track(KoalaEvent.MODAL_DIALOG_VIEW, props);
  }

  // COMMENTS
  public void trackLoadedOlderComments(final @NonNull Project project, final @Nullable Update update,
    final @NonNull KoalaContext.Comments context) {

    final User loggedInUser = this.client.loggedInUser();
    final Map<String, Object> props = update == null
      ? KoalaUtils.projectProperties(project, loggedInUser)
      : KoalaUtils.updateProperties(project, update, loggedInUser);
    props.put("context", context.getTrackingString());

    this.client.track(KoalaEvent.LOADED_OLDER_COMMENTS, props);
  }

  /**
   * @deprecated Use {@link #trackLoadedOlderComments(Project, Update, KoalaContext.Comments)} instead.
   */
  @Deprecated
  public void trackLoadedOlderProjectComments(final @NonNull Project project) {
    this.client.track(KoalaEvent.PROJECT_COMMENT_LOAD_OLDER, KoalaUtils.projectProperties(project, this.client.loggedInUser()));
  }

  public void trackPostedComment(final @NonNull Project project, final @Nullable Update update,
    final @NonNull KoalaContext.CommentDialog context) {

    final User loggedInUser = this.client.loggedInUser();
    final Map<String, Object> props = update == null
      ? KoalaUtils.projectProperties(project, loggedInUser)
      : KoalaUtils.updateProperties(project, update, loggedInUser);
    props.put("context", context.getTrackingString());

    this.client.track(KoalaEvent.POSTED_COMMENT, props);
  }

  /**
   * @deprecated Use {@link #trackPostedComment(Project, Update, KoalaContext.CommentDialog)} instead.
   */
  @Deprecated
  public void trackProjectCommentCreate(final @NonNull Project project) {
    this.client.track(KoalaEvent.PROJECT_COMMENT_CREATE, KoalaUtils.projectProperties(project, this.client.loggedInUser()));
  }

  /**
   * @deprecated Use {@link #trackViewedComments(Project, Update, KoalaContext.Comments)} instead.
   */
  @Deprecated
  public void trackProjectCommentsView(final @NonNull Project project) {
    this.client.track(KoalaEvent.PROJECT_COMMENT_VIEW, KoalaUtils.projectProperties(project, this.client.loggedInUser()));
  }

  public void trackViewedComments(final @NonNull Project project, final @Nullable Update update,
    final @NonNull KoalaContext.Comments context) {

    final User loggedInUser = this.client.loggedInUser();
    final Map<String, Object> props = update == null
      ? KoalaUtils.projectProperties(project, loggedInUser)
      : KoalaUtils.updateProperties(project, update, loggedInUser);

    props.put("context", context.getTrackingString());
    this.client.track(KoalaEvent.VIEWED_COMMENTS, props);
  }

  // ACTIVITY
  public void trackActivityView(final int pageCount) {
    if (pageCount == 0) {
      this.client.track(KoalaEvent.ACTIVITY_VIEW);
    } else {
      this.client.track(KoalaEvent.ACTIVITY_LOAD_MORE, new HashMap<String, Object>() {
        {
          put("page_count", pageCount);
        }
      });
    }
  }

  // SEARCH
  public void trackSearchView() {
    this.client.track(KoalaEvent.VIEWED_SEARCH);
    // deprecated
    this.client.track(KoalaEvent.DISCOVER_SEARCH_LEGACY);
  }

  public void trackSearchResults(final @NonNull String query, final int pageCount) {
    if (pageCount == 1) {
      final Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("search_term", query);
        }
      };
      this.client.track(KoalaEvent.LOADED_SEARCH_RESULTS, params);
      // deprecated
      this.client.track(KoalaEvent.DISCOVER_SEARCH_RESULTS_LEGACY, params);
    } else {
      final Map<String, Object> params = new HashMap<String, Object>() {
        {
          put("search_term", query);
          put("page_count", pageCount);
        }
      };
      this.client.track(KoalaEvent.LOADED_MORE_SEARCH_RESULTS, params);
      // deprecated
      this.client.track(KoalaEvent.DISCOVER_SEARCH_RESULTS_LOAD_MORE_LEGACY, params);
    }
  }

  public void trackClearedSearchTerm() {
    this.client.track(KoalaEvent.CLEARED_SEARCH_TERM);
  }

  public void trackActivityTapped(final @NonNull Activity activity) {
    this.client.track(KoalaEvent.ACTIVITY_VIEW_ITEM, KoalaUtils.activityProperties(activity, this.client.loggedInUser()));
  }

  // SESSION EVENTS
  public void trackLoginRegisterTout(final @NonNull LoginReason loginReason) {
    this.client.track("Application Login or Signup", new HashMap<String, Object>() {
      {
        put("intent", loginReason.trackingString());
      }
    });
  }

  public void trackLoginSuccess() {
    this.client.track(KoalaEvent.LOGIN);
  }

  public void trackLoginError() {
    this.client.track(KoalaEvent.ERRORED_USER_LOGIN);
  }

  public void trackTwoFactorAuthView() {
    this.client.track(KoalaEvent.TWO_FACTOR_AUTH_CONFIRM_VIEW);
  }

  public void trackTwoFactorResendCode() {
    this.client.track(KoalaEvent.TWO_FACTOR_AUTH_RESEND_CODE);
  }

  public void trackRegisterFormView() {
    this.client.track(KoalaEvent.USER_SIGNUP);
  }

  public void trackRegisterError() {
    this.client.track(KoalaEvent.ERRORED_USER_SIGNUP);
  }

  public void trackRegisterSuccess() {
    this.client.track(KoalaEvent.NEW_USER);
  }

  public void trackResetPasswordFormView() {
    this.client.track("Forgot Password View");
  }

  public void trackResetPasswordSuccess() {
    this.client.track("Forgot Password Requested");
  }

  public void trackResetPasswordError() {
    this.client.track("Forgot Password Errored");
  }

  public void trackFacebookConfirmation() {
    this.client.track(KoalaEvent.FACEBOOK_CONFIRM);
  }

  public void trackFacebookLoginSuccess() {
    this.client.track("Facebook Login");
  }

  public void trackFacebookLoginError() {
    this.client.track("Errored Facebook Login");
  }

  public void trackLogout() {
    this.client.track("Logout");
  }

  public void trackSignupNewsletterToggle(final boolean sendNewsletters) {
    this.client.track(KoalaEvent.SIGNUP_NEWSLETTER_TOGGLE, new HashMap<String, Object>() {
      {
        put("send_newsletters", sendNewsletters);
      }
    });
  }

  // SETTINGS
  public void trackChangedEmail() {
    this.client.track(KoalaEvent.CHANGED_EMAIL);
  }

  public void trackChangedPassword() {
    this.client.track(KoalaEvent.CHANGED_PASSWORD);
  }
  public void trackContactEmailClicked() {
    this.client.track("Contact Email Clicked");
  }

  public void trackCreatedPassword() {
    this.client.track(KoalaEvent.CREATED_PASSWORD);
  }

  public void trackDeletePaymentMethod() {
    this.client.track(KoalaEvent.DELETED_PAYMENT_METHOD);
  }

  public void trackErroredDeletePaymentMethod() {
    this.client.track(KoalaEvent.ERRORED_DELETE_PAYMENT_METHOD);
  }

  public void trackFailedPaymentMethodCreation() {
    this.client.track(KoalaEvent.FAILED_PAYMENT_METHOD_CREATION);
  }

  public void trackNewsletterToggle(final boolean sendNewsletter) {
    if (sendNewsletter) {
      this.client.track("Newsletter Subscribe");
    } else {
      this.client.track("Newsletter Unsubscribe");
    }
  }

  public void trackResentVerificationEmail() {
    this.client.track(KoalaEvent.RESENT_VERIFICATION_EMAIL);
  }

  public void trackSavedPaymentMethod() {
    this.client.track(KoalaEvent.SAVED_PAYMENT_METHOD);
  }

  public void trackSelectedChosenCurrency(final String selectedCurrency) {
    this.client.track(KoalaEvent.SELECTED_CHOSEN_CURRENCY, new HashMap<String, Object>() {
      {
        put("user_chosen_currency", selectedCurrency);
      }
    });
  }

  public void trackSettingsView() {
    this.client.track(KoalaEvent.VIEWED_SETTINGS);
  }

  public void trackViewedAccount() {
    this.client.track(KoalaEvent.VIEWED_ACCOUNT);
  }

  public void trackViewedAddNewCard() {
    this.client.track(KoalaEvent.VIEWED_ADD_NEW_CARD);
  }

  public void trackViewedChangedEmail() {
    this.client.track(KoalaEvent.VIEWED_CHANGE_EMAIL);
  }

  public void trackViewedChangedPassword() {
    this.client.track(KoalaEvent.VIEWED_CHANGE_PASSWORD);
  }

  public void trackViewedCreatedPassword() {
    this.client.track(KoalaEvent.VIEWED_CREATE_PASSWORD);
  }

  public void trackViewedNotifications() {
    this.client.track(KoalaEvent.VIEWED_NOTIFICATIONS);
  }

  public void trackViewedNewsletter() {
    this.client.track(KoalaEvent.VIEWED_NEWSLETTER);
  }

  public void trackViewedPaymentMethods() {
    this.client.track(KoalaEvent.VIEWED_PAYMENT_METHODS);
  }

  public void trackViewedPrivacy() {
    this.client.track(KoalaEvent.VIEWED_PRIVACY);
  }

  // CHECKOUT
  public void trackCheckoutNext() { // rewards webview and top nav
    this.client.track("Checkout Next");
  }
  public void trackCheckoutCancel() {
    this.client.track("Checkout Cancel");
  }

  public void trackCheckoutLoadFailed() {
    // TODO: set up error props
  }

  public void trackCheckoutShowShareSheet() {
    this.client.track("Checkout Show Share Sheet");
  }

  public void trackCheckoutCancelShareSheet() {
    this.client.track("Checkout Cancel Share Sheet");
  }

  public void trackCheckoutShowFacebookShareView() {
    this.client.track("Checkout Show Share", new HashMap<String, Object>() {
      {
        put("share_type", "facebook");
      }
    });
  }

  public void trackCheckoutShowTwitterShareView() {
    this.client.track("Checkout Show Share", new HashMap<String, Object>() {
      {
        put("share_type", "twitter");
      }
    });
  }

  public void trackCheckoutShareFinished() {
    this.client.track("Checkout Share Finished");
  }

  public void trackCheckoutFinishJumpToDiscovery() {
    this.client.track("Checkout Finished Discover More");
  }

  public void trackCheckoutFinishJumpToProject(final @NonNull Project project) {
    final Map<String, Object> props = KoalaUtils.projectProperties(project, this.client.loggedInUser());
    this.client.track("Checkout Finished Discover Open Project", props);
  }

  // SHARE
  public void trackShowProjectShareSheet(final @NonNull Project project) {
    final Map<String, Object> props = KoalaUtils.projectProperties(project, this.client.loggedInUser());
    props.put("context", KoalaContext.Share.PROJECT);

    // deprecated
    this.client.track(KoalaEvent.PROJECT_SHOW_SHARE_SHEET_LEGACY);

    this.client.track(KoalaEvent.SHOWED_SHARE_SHEET, props);
  }

  public void trackCancelProjectShareSheet() {
    this.client.track("Project Cancel Share Sheet");
  }

  public void trackShowProjectFacebookShareView() {
    this.client.track("Project Show Share", new HashMap<String, Object>() {
      {
        put("share_type", "facebook");
      }
    });
  }

  public void trackShowProjectTwitterShareView() {
    this.client.track("Project Show Share", new HashMap<String, Object>() {
      {
        put("share_type", "twitter");
      }
    });
  }

  public void trackProjectFacebookShare() {
    this.client.track("Project Share", new HashMap<String, Object>() {
      {
        put("share_type", "facebook");
      }
    });
  }

  public void trackProjectTwitterShare() {
    this.client.track("Project Share", new HashMap<String, Object>() {
      {
        put("share_type", "twitter");
      }
    });
  }

  // MESSAGES
  public void trackSentMessage(final @NonNull Project project, final @NonNull KoalaContext.Message context) {
    final Map<String, Object> props = KoalaUtils.projectProperties(project, this.client.loggedInUser());
    props.put("context", context.getTrackingString());

    this.client.track(KoalaEvent.SENT_MESSAGE, props);
  }

  public void trackViewedMailbox(final @NonNull Mailbox mailbox, final @Nullable Project project,
    final @Nullable RefTag intentRefTag, final @NonNull KoalaContext.Mailbox context) {
    final Map<String, Object> props = project == null ? new HashMap<>() : KoalaUtils.projectProperties(project, this.client.loggedInUser());

    props.put("context", context.getTrackingString());
    if (intentRefTag != null) {
      props.put("ref_tag", intentRefTag.tag());
    }

    switch (mailbox) {
      case INBOX:
        this.client.track(KoalaEvent.VIEWED_MESSAGE_INBOX, props);
        break;
      case SENT:
        this.client.track(KoalaEvent.VIEWED_SENT_MESSAGES, props);
        break;
    }
  }

  public void trackViewedMessageThread(final @NonNull Project project) {
    this.client.track(KoalaEvent.VIEWED_MESSAGE_THREAD, KoalaUtils.projectProperties(project, this.client.loggedInUser()));
  }

  // PROFILE
  public void trackProfileView() {
    // deprecated
    this.client.track(KoalaEvent.PROFILE_VIEW_MY);

    this.client.track(KoalaEvent.VIEWED_PROFILE);
  }

  // RATING
  public void trackAppRatingNow() {
    this.client.track("Checkout Finished Alert App Store Rating Rate Now");
  }

  public void trackAppRatingRemindLater() {
    this.client.track("Checkout Finished Alert App Store Rating Remind Later");
  }

  public void trackAppRatingNoThanks() {
    this.client.track("Checkout Finished Alert App Store Rating No Thanks");
  }

  // VIDEO
  public void trackVideoStart(final @NonNull Project project) {
    this.client.track("Project Video Start", KoalaUtils.projectProperties(project, this.client.loggedInUser()));
  }

  // PROJECT UPDATES
  public void trackViewedUpdate(final @NonNull Project project, final @NonNull KoalaContext.Update context) {
    final Map<String, Object> props = KoalaUtils.projectProperties(project, this.client.loggedInUser());
    props.put("context", context.getTrackingString());
    this.client.track(KoalaEvent.VIEWED_UPDATE, props);
  }

  public void trackViewedUpdates(final @NonNull Project project) {
    this.client.track(KoalaEvent.VIEWED_UPDATES, KoalaUtils.projectProperties(project, this.client.loggedInUser()));
  }

  // PUSH NOTIFICATIONS
  public void trackPushNotification(final @NonNull PushNotificationEnvelope envelope) {
    final Map<String, Object> properties = new HashMap<String, Object>() {
      {
        put("notification_type", "push");

        if (envelope.activity() != null) {
          put("notification_subject", "activity");
          put("notification_activity_category", envelope.activity().category());
        }
      }
    };

    // deprecated
    this.client.track(KoalaEvent.NOTIFICATION_OPENED_LEGACY, properties);

    this.client.track(KoalaEvent.OPENED_NOTIFICATION, properties);
  }

  // WEBVIEWS
  public void trackOpenedExternalLink(final @NonNull Project project, final @NonNull KoalaContext.ExternalLink context) {
    final Map<String, Object> props = KoalaUtils.projectProperties(project, this.client.loggedInUser());
    props.put("context", context.getTrackingString());

    this.client.track(KoalaEvent.OPENED_EXTERNAL_LINK, props);
  }

  // DEEP LINK
  public void trackContinueUserActivityAndOpenedDeepLink() {
    this.client.track(KoalaEvent.CONTINUE_USER_ACTIVITY);

    this.client.track(KoalaEvent.OPENED_DEEP_LINK);
  }

  // CREATOR DASHBOARD
  public void trackOpenedProjectSwitcher() {
    this.client.track(KoalaEvent.OPENED_PROJECT_SWITCHER);
  }

  public void trackSwitchedProjects(final @NonNull Project project) {
    final Map<String, Object> properties = KoalaUtils.projectProperties(project, this.client.loggedInUser());

    this.client.track(KoalaEvent.SWITCHED_PROJECTS, properties);
  }

  public void trackViewedProjectDashboard(final @NonNull Project project) {
    final Map<String, Object> properties = KoalaUtils.projectProperties(project, this.client.loggedInUser());

    this.client.track(KoalaEvent.VIEWED_PROJECT_DASHBOARD, properties);
  }
}
