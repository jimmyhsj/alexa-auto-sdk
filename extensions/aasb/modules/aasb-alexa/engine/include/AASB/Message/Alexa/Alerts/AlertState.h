/*
 * Copyright 2017-2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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

/*********************************************************
**********************************************************
**********************************************************

THIS FILE IS AUTOGENERATED. DO NOT EDIT

**********************************************************
**********************************************************
*********************************************************/

#ifndef ALERTS_ALERTSTATE_H
#define ALERTS_ALERTSTATE_H

#include <string>
#include <vector>

#include <unordered_map>
#include <AACE/Engine/Utils/UUID/UUID.h>
#include <nlohmann/json.hpp>

namespace aasb {
namespace message {
namespace alexa {

//Enum Definition
enum class AlertState {
    READY,
    STARTED,
    STOPPED,
    SNOOZED,
    COMPLETED,
    PAST_DUE,
    FOCUS_ENTERED_FOREGROUND,
    FOCUS_ENTERED_BACKGROUND,
    ERROR,
    DELETED,
    SCHEDULED_FOR_LATER,
};

inline std::string toString(AlertState enumValue) {
    switch (enumValue) {
        case (AlertState::READY):
            return "READY";
        case (AlertState::STARTED):
            return "STARTED";
        case (AlertState::STOPPED):
            return "STOPPED";
        case (AlertState::SNOOZED):
            return "SNOOZED";
        case (AlertState::COMPLETED):
            return "COMPLETED";
        case (AlertState::PAST_DUE):
            return "PAST_DUE";
        case (AlertState::FOCUS_ENTERED_FOREGROUND):
            return "FOCUS_ENTERED_FOREGROUND";
        case (AlertState::FOCUS_ENTERED_BACKGROUND):
            return "FOCUS_ENTERED_BACKGROUND";
        case (AlertState::ERROR):
            return "ERROR";
        case (AlertState::DELETED):
            return "DELETED";
        case (AlertState::SCHEDULED_FOR_LATER):
            return "SCHEDULED_FOR_LATER";
    }
    throw std::runtime_error("invalidAlertStateType");
}

inline AlertState toAlertState(const std::string& stringValue) {
    static std::unordered_map<std::string, AlertState> map = {
        {"READY", AlertState::READY},
        {"STARTED", AlertState::STARTED},
        {"STOPPED", AlertState::STOPPED},
        {"SNOOZED", AlertState::SNOOZED},
        {"COMPLETED", AlertState::COMPLETED},
        {"PAST_DUE", AlertState::PAST_DUE},
        {"FOCUS_ENTERED_FOREGROUND", AlertState::FOCUS_ENTERED_FOREGROUND},
        {"FOCUS_ENTERED_BACKGROUND", AlertState::FOCUS_ENTERED_BACKGROUND},
        {"ERROR", AlertState::ERROR},
        {"DELETED", AlertState::DELETED},
        {"SCHEDULED_FOR_LATER", AlertState::SCHEDULED_FOR_LATER},
    };

    auto search = map.find(stringValue);
    if (search != map.end()) {
        return search->second;
    }
    throw std::runtime_error("invalidAlertStateType");
}

inline void to_json(nlohmann::json& j, const AlertState& c) {
    j = toString(c);
}

inline void from_json(const nlohmann::json& j, AlertState& c) {
    c = toAlertState(j);
}

}  // namespace alexa
}  // namespace message
}  // namespace aasb

#endif  // ALERTS_ALERTSTATE_H