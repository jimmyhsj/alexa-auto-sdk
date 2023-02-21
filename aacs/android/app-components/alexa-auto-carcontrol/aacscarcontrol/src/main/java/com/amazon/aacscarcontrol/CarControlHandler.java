/*
 * Copyright 2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *     http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.amazon.aacscarcontrol;

import android.content.Context;

import com.amazon.aacsconstants.AACSConstants;

public class CarControlHandler {
    private static final String TAG = AACSConstants.AACS + "-" + CarControlHandler.class.getCanonicalName();
    private Context mContext;

    public CarControlHandler(Context context) {
        mContext = context;
    }


    public boolean changePowerController(String endpointId, boolean turnOn) {
        return true;
    }


    public boolean changeToggleController(String endpointId, String instance, boolean turnOn) {
        return true;
    }


    public boolean setRangeControllerValue(String endpointId, String instance, double value) {
        return true;
    }

    public boolean adjustRangeControllerValue(String endpointId, String instance, double delta) {
        return true;
    }

    public boolean setModeControllerValue(String endpointId, String instance, String value) {
        return true;
    }

    public boolean adjustModeControllerValue(String endpointId, String instance, int delta) {
        return true;
    }

}