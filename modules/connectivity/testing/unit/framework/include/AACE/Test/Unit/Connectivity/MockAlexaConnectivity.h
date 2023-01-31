/*
 * Copyright 2020-2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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

#ifndef AACE_TEST_UNIT_CONNECTIVITY_MOCK_CONNECTIVITY_H
#define AACE_TEST_UNIT_CONNECTIVITY_MOCK_CONNECTIVITY_H

#include <AACE/Connectivity/AlexaConnectivity.h>
#include <gmock/gmock.h>

namespace aace {
namespace test {
namespace unit {
namespace connectivity {

class MockAlexaConnectivity : public aace::connectivity::AlexaConnectivity {
public:
    MOCK_METHOD0(getConnectivityState, std::string());
    MOCK_METHOD0(getIdentifier, std::string());
    MOCK_METHOD2(connectivityEventResponse, void(const std::string& token, StatusCode statusCode));
};

}  // namespace connectivity
}  // namespace unit
}  // namespace test
}  // namespace aace

#endif  // AACE_TEST_UNIT_CONNECTIVITY_MOCK_CONNECTIVITY_H
