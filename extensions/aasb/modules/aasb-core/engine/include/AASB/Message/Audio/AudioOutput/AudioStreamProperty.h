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

#ifndef AUDIOOUTPUT_AUDIOSTREAMPROPERTY_H
#define AUDIOOUTPUT_AUDIOSTREAMPROPERTY_H

#include <string>
#include <vector>

#include <AACE/Engine/Utils/UUID/UUID.h>
#include <nlohmann/json.hpp>

namespace aasb {
namespace message {
namespace audio {

//Class Definition
struct AudioStreamProperty {
    std::string toString() const;
    std::string name;
    std::string value;
};

//JSON Serialization
inline void to_json(nlohmann::json& j, const AudioStreamProperty& c) {
    j = nlohmann::json{
        {"name", c.name},
        {"value", c.value},
    };
}
inline void from_json(const nlohmann::json& j, AudioStreamProperty& c) {
    j.at("name").get_to(c.name);
    j.at("value").get_to(c.value);
}

inline std::string AudioStreamProperty::toString() const {
    nlohmann::json j = *this;
    return j.dump(3);
}

}  // namespace audio
}  // namespace message
}  // namespace aasb

#endif  // AUDIOOUTPUT_AUDIOSTREAMPROPERTY_H