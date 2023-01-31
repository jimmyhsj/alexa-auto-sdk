/*
 * Copyright 2017-2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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

package com.amazon.aace.cbl.config;

import com.amazon.aace.core.config.EngineConfiguration;

/**
 * A factory interface for creating CBL configuration objects
 */
public class CBLConfiguration {
    private static final String TAG = "CBLConfiguration";

    /**
     * Factory method used to programmatically generate cbl configuration data.
     * The data generated by this method is equivalent to providing the following JSON
     * values in a configuration file:
     *
     * @code{.json}
     * {
     *   "aace.cbl": {
     *     "requestTimeout" : <REQUEST_TIMEOUT_IN_SECONDS>,
     *     "enableUserProfile" : false
     *   }
     * }
     * @endcode
     *
     * @param  requestTimeout The timeout used for requesting code pair
     * The default configuration of 60 seconds will be overridden with this value when configured.
     */
    public static EngineConfiguration createCBLConfig(final int seconds) {
        return new EngineConfiguration() {
            @Override
            protected long createNativeRef() {
                return createCBLConfigBinder(seconds, false);
            }
        };
    }

    /**
     * Factory method used to programmatically generate cbl configuration data.
     * The data generated by this method is equivalent to providing the following JSON
     * values in a configuration file:
     *
     * @code{.json}
     * {
     *   "aace.cbl": {
     *     "requestTimeout" : <REQUEST_TIMEOUT_IN_SECONDS>,
     *     "enableUserProfile" : <true/false>
     *   }
     * }
     * @endcode
     *
     * @param requestTimeout The timeout used for requesting code pair. The default value is 60s
     * @param enableUserProfile Enable functionality to request user profile. The default value is @c false.
     */
    public static EngineConfiguration createCBLConfig(final int seconds, final boolean enableUserProfile) {
        return new EngineConfiguration() {
            @Override
            protected long createNativeRef() {
                return createCBLConfigBinder(seconds, enableUserProfile);
            }
        };
    }

    // Native Engine JNI methods
    static private native long createCBLConfigBinder(int seconds, boolean enableUserProfile);

    /**
     * @deprecated This method is deprecated, use @c createCBLConfig instead.
     * Factory method used to programmatically generate cbl configuration data.
     * The data generated by this method is equivalent to providing the following JSON
     * values in a configuration file:
     *
     * @code{.json}
     * {
     *   "aace.cbl": {
     *     "enableUserProfile": <true/false>
     *   }
     * }
     * @endcode
     *
     * @param [in] enableUserProfile Enable functionality to request user profile
     */
    public static EngineConfiguration createCBLUserProfileConfig(final boolean enableUserProfile) {
        return new EngineConfiguration() {
            @Override
            protected long createNativeRef() {
                return createCBLUserProfileConfigBinder(enableUserProfile);
            }
        };
    }

    static private native long createCBLUserProfileConfigBinder(boolean enableUserProfile);
}
