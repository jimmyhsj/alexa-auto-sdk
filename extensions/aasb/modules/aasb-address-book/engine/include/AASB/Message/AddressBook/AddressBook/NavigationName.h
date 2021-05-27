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

#ifndef ADDRESSBOOK_NAVIGATIONNAME_H
#define ADDRESSBOOK_NAVIGATIONNAME_H

#include <string>
#include <vector>

#include <AACE/Engine/Utils/UUID/UUID.h>
#include <nlohmann/json.hpp>

namespace aasb {
namespace message {
namespace addressBook {
namespace addressBook {

//Class Definition
struct NavigationName {
    std::string toString() const;
    std::string entryId;
    std::string name;
    std::string phoneticName = "";
};

//JSON Serialization
inline void to_json(nlohmann::json& j, const NavigationName& c) {
    j = nlohmann::json{
        {"entryId", c.entryId},
        {"name", c.name},
        {"phoneticName", c.phoneticName},
    };
}
inline void from_json(const nlohmann::json& j, NavigationName& c) {
    j.at("entryId").get_to(c.entryId);
    j.at("name").get_to(c.name);
    if (j.contains("phoneticName")) {
        j.at("phoneticName").get_to(c.phoneticName);
    }
}

inline std::string NavigationName::toString() const {
    nlohmann::json j = *this;
    return j.dump(3);
}

}  // namespace addressBook
}  // namespace addressBook
}  // namespace message
}  // namespace aasb

#endif  // ADDRESSBOOK_NAVIGATIONNAME_H