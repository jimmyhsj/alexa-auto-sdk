package com.amazon.alexaautoclientservice.unittest;

import android.content.Context;
import android.os.Build;

import com.amazon.aacsconstants.Action;
import com.amazon.aacsconstants.Topic;
import com.amazon.alexaautoclientservice.AASBHandler;
import com.amazon.alexaautoclientservice.util.AASBUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.UUID;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.P})
public class TestAASBHandler {
    private AASBHandler mAASBHandler;

    @Before
    public void setup() {
        mAASBHandler = Mockito.mock(AASBHandler.class);
    }

    @Test
    public void testMessageReceived() {
        String message = AASBUtil.constructAASBMessage(
                "", Topic.SPEECH_RECOGNIZER, Action.SpeechRecognizer.WAKEWORD_DETECTED, "");
        mAASBHandler.messageReceived(message);
    }

    @Test
    public void testMessageReceivedWithWrongMessage() {
        String message = "{}";
        mAASBHandler.messageReceived(message);
    }
}
