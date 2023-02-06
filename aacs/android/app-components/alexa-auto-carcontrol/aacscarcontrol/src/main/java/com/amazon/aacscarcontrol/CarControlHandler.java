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
import android.content.Intent;
import android.util.Log;


public class CarControlHandler {
//    private static final String TAG = AACSConstants.AACS + "-" + CarControlHandler.class.getCanonicalName();
    private static final String TAG = "cc_alexa_car_control";
    private Context mContext;


    public CarControlHandler(Context context) {
        mContext = context;

    }

    public boolean changePowerController(String endpointId, boolean turnOn) {
        Log.e(TAG,"changePowerController:endpointId:"+endpointId);
        Log.e(TAG,"changePowerController:turnOn:"+turnOn);
        boolean ret = false;
        switch (endpointId){
            case "default.ac":{
                Intent intent = new Intent();
                intent.setAction(AmazonConst.ACTION_SET_AC_STATUS);
                if(turnOn){
                    intent.putExtra("value","ON");

                }else{
                    intent.putExtra("value","OFF");
                }
                mContext.sendBroadcast(intent);
                ret = true;
            }
            case "":{

            }
            default:

        }
        return ret;
    }


    public boolean isPowerControllerOn(String endpointId) {
        return false;
    }

    public boolean changeToggleController(String endpointId, String instance, boolean turnOn) {
        Log.e(TAG,"changeToggleController:endpointId:"+endpointId);
        Log.e(TAG,"changeToggleController:instance:"+instance);
        Log.e(TAG,"changeToggleController:turnOn:"+turnOn);
        return false;
    }

    public boolean isToggleControllerOn(String endpointId, String instance) {
        return false;
    }

    public boolean setRangeControllerValue(String endpointId, String instance, double value) {
        Log.e(TAG, "setRangeControllerValue:endpointId:" + endpointId);
        Log.e(TAG, "setRangeControllerValue:instance:" + instance);
        Log.e(TAG, "setRangeControllerValue:value:" + value);
        boolean ret = true;
        if (endpointId.equals("default.heater") && instance.equals("temperature")) {
            Intent intent = new Intent();
            intent.setAction(AmazonConst.ACTION_SET_AC_TEMP);
            intent.putExtra("value",String.valueOf(value));
            mContext.sendBroadcast(intent);

        }
        if (endpointId.equals("default.fan") && instance.equals("speed")) {
            Intent intent = new Intent();
            intent.setAction(AmazonConst.ACTION_SET_AC_FAN);
            intent.putExtra("value",String.valueOf(value));
            mContext.sendBroadcast(intent);
            Log.e(TAG,"ACTION_SET_AC_FAN send");
        }
        return ret;
    }

    public boolean adjustRangeControllerValue(String endpointId, String instance, double delta) {
        Log.e(TAG,"adjustRangeControllerValue:endpointId:"+endpointId);
        Log.e(TAG,"adjustRangeControllerValue:instance:"+instance);
        Log.e(TAG,"adjustRangeControllerValue:delta:"+delta);
        return false;
    }

    public double getRangeControllerValue(String endpointId, String instance) {
        return 0;
    }

    public boolean setModeControllerValue(String endpointId, String instance, String value) {
        Log.e(TAG,"setModeControllerValue:endpointId:"+endpointId);
        Log.e(TAG,"setModeControllerValue:instance:"+instance);
        Log.e(TAG,"setModeControllerValue:value:"+value);
        return false;
    }

    public boolean adjustModeControllerValue(String endpointId, String instance, int delta) {
        Log.e(TAG,"adjustModeControllerValue:endpointId:"+endpointId);
        Log.e(TAG,"adjustModeControllerValue:instance:"+instance);
        Log.e(TAG,"adjustModeControllerValue:value:"+delta);
        return false;
    }

    public String getModeControllerValue(String endpointId, String instance) {
        return "";
    }



}