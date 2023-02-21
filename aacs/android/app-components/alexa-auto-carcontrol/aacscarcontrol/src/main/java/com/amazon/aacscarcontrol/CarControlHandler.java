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
import android.util.Log;
import android.widget.Toast;

import com.amazon.aacsconstants.AACSConstants;

public class CarControlHandler {
    private static final String TAG = AACSConstants.AACS + "-" + CarControlHandler.class.getCanonicalName();
    private Context mContext;

    public CarControlHandler(Context context) {
        mContext = context;
    }


    public boolean changePowerController(String endpointId, boolean turnOn) {
        Log.e("cc_alexa","changePowerController:endpointId:"+endpointId);
        Log.e("cc_alexa","changePowerController:turnOn:"+turnOn);
        Toast.makeText(mContext,"changePowerController:endpointId:"+endpointId+"  turnOn:"+turnOn,Toast.LENGTH_LONG).show();
        return true;
    }


    public boolean changeToggleController(String endpointId, String instance, boolean turnOn) {
        Log.e("cc_alexa","changeToggleController:endpointId:"+endpointId);
        Log.e("cc_alexa","changeToggleController:instance:"+instance);
        Log.e("cc_alexa","changeToggleController:turnOn:"+turnOn);
        Toast.makeText(mContext,"changeToggleController:endpointId:"+endpointId+"  instance:"+instance+"  turnOn:"+turnOn,Toast.LENGTH_LONG).show();
        return true;
    }


    public boolean setRangeControllerValue(String endpointId, String instance, double value) {
        Log.e("cc_alexa","setRangeControllerValue:endpointId:"+endpointId);
        Log.e("cc_alexa","setRangeControllerValue:instance:"+instance);
        Log.e("cc_alexa","setRangeControllerValue:value:"+value);
        Toast.makeText(mContext,"setRangeControllerValue:endpointId:"+endpointId+"  instance:"+instance+"  value:"+value,Toast.LENGTH_LONG).show();
        return true;
    }

    public boolean adjustRangeControllerValue(String endpointId, String instance, double delta) {
        Log.e("cc_alexa","adjustRangeControllerValue:endpointId:"+endpointId);
        Log.e("cc_alexa","adjustRangeControllerValue:instance:"+instance);
        Log.e("cc_alexa","adjustRangeControllerValue:delta:"+delta);
        Toast.makeText(mContext,"adjustRangeControllerValue:endpointId:"+endpointId+"  instance:"+instance+"  delta:"+delta,Toast.LENGTH_LONG).show();
        return true;
    }

    public boolean setModeControllerValue(String endpointId, String instance, String value) {
        Log.e("cc_alexa","setModeControllerValue:endpointId:"+endpointId);
        Log.e("cc_alexa","setModeControllerValue:instance:"+instance);
        Log.e("cc_alexa","setModeControllerValue:value:"+value);
        Toast.makeText(mContext,"setModeControllerValue:endpointId:"+endpointId+"  instance:"+instance+"  value:"+value,Toast.LENGTH_LONG).show();
        return true;
    }

    public boolean adjustModeControllerValue(String endpointId, String instance, int delta) {
        Log.e("cc_alexa","adjustModeControllerValue:endpointId:"+endpointId);
        Log.e("cc_alexa","adjustModeControllerValue:instance:"+instance);
        Log.e("cc_alexa","adjustModeControllerValue:delta:"+delta);
        Toast.makeText(mContext,"adjustModeControllerValue:endpointId:"+endpointId+"  instance:"+instance+"  delta:"+delta,Toast.LENGTH_LONG).show();
        return true;
    }

}