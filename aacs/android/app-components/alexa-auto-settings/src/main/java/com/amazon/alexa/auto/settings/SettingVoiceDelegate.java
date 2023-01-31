package com.amazon.alexa.auto.settings;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.service.voice.VoiceInteractionService;
import android.service.voice.VoiceInteractionSession;
import android.util.Log;

import com.amazon.aacsconstants.AASBConstants;
import com.amazon.aacsconstants.Action;
import com.amazon.aacsconstants.Topic;
import com.amazon.aacsipc.AACSSender;
import com.amazon.alexa.auto.aacs.common.AACSMessageSender;
import com.amazon.alexa.auto.apis.app.AlexaApp;
import com.amazon.alexa.auto.apis.auth.AuthController;
import com.amazon.alexa.auto.apis.setup.AlexaSetupController;
import com.amazon.alexa.auto.apps.common.aacs.AACSServiceController;
import com.amazon.alexa.auto.apps.common.util.UiThemeManager;
import com.amazon.alexa.auto.settings.config.AACSConfigurationPreferences;
import com.amazon.alexa.auto.settings.config.AACSConfigurator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;


/**
 * Alexa Auto Voice Interaction Service, extends top-level service of the current global voice interactor,
 * which is providing support for start/stop AACS, handle Alexa wakeword and the back-end of a VoiceInteractor.
 */
public class SettingVoiceDelegate {
    private static final String TAG = SettingVoiceDelegate.class.getCanonicalName();

    AuthController mAuthController;
    AlexaSetupController mAlexaSetupController;
    AACSConfigurator mAACSConfigurator;
    UiThemeManager mUiThemeManager;
    AACSSender mAACSSender;
    AACSMessageSender mMessageSender;

    private boolean isAlexaConnected;

    private static String[] AMAZONLITE_MODEL_FILES;

    public void onCreate(Context context) {
        Log.i(TAG, "onCreate");
        Log.e("cc_alexa", "onCreate:AutoVoiceInteractionService");
//        EventBus.getDefault().register(this);

        AlexaApp mApp = AlexaApp.from(context);
//        mApp.getRootComponent().activateScope(new SessionViewControllerImpl());
//        mApp.getRootComponent().activateScope(new SessionActivityControllerImpl());

        mAuthController = mApp.getRootComponent().getAuthController();
        mAlexaSetupController = mApp.getRootComponent().getAlexaSetupController();
        mAACSSender = new AACSSender();

        WeakReference<Context> ContextWk = new WeakReference<>(context.getApplicationContext());
        mAACSConfigurator = new AACSConfigurator(ContextWk, mAACSSender, new AACSConfigurationPreferences(ContextWk));
        mMessageSender = new AACSMessageSender(ContextWk, mAACSSender);
        mUiThemeManager = new UiThemeManager(context.getApplicationContext(), mMessageSender);
    }

    public void onReady(Context context) {
        Log.i(TAG, "OnReady");
        Log.e("cc_alexa", "OnReady:AutoVoiceInteractionService");
        // Temporary fix to start AACS 30 seconds after startup of VIS after recent
        // device boot. This allows system to settle down and deliver intents in
        // regular time window instead of rather large time window (up to 15 seconds
        // in delivery on emulator).
        final int aacs_start_delay_ms = 30 * 1000;
        final long device_boot_margin_ms = 5 * 1000 * 60;
        final long ms_since_device_bootup = SystemClock.elapsedRealtime();
        if (ms_since_device_bootup < device_boot_margin_ms) {
            Log.d(TAG, "Will start AACS after 30 seconds. Milliseconds since device bootup " + ms_since_device_bootup);
            new Handler().postDelayed(() -> {
                Log.d(TAG, "Starting AACS after delay. Milliseconds since device bootup " + ms_since_device_bootup);
                AACSServiceController.startAACS(context, true);
            }, aacs_start_delay_ms);
        } else {
            Log.d(TAG, "Starting AACS right away. Milliseconds since device bootup " + ms_since_device_bootup);
            AACSServiceController.startAACS(context, true);
        }

        // The order is important! Share files before configuring AACS.
        mAACSConfigurator.shareFilesWithAACS(context.getApplicationContext());
        // Configure AACS immediately without waiting for delay start to avoid consistent ANRs in AACS.
        mAACSConfigurator.configureAACSWithPreferenceOverrides();

        if (mAuthController.isAuthenticated()) {
            isAlexaConnected = true;
        }

        mUiThemeManager.init();
    }

    public void onShutdown(Context context) {
        Log.i(TAG, "onShutdown");
        AACSServiceController.stopAACS(context);
        mUiThemeManager.destroy();
    }

}
