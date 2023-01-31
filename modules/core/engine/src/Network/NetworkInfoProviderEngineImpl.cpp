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

#include <sstream>

#include "AACE/Engine/Network/NetworkInfoProviderEngineImpl.h"
#include "AACE/Engine/Core/EngineMacros.h"
#include <AACE/Engine/Utils/Metrics/Metrics.h>

#include <sstream>

namespace aace {
namespace engine {
namespace network {

using namespace aace::engine::utils::metrics;

// String to identify log entries originating from this file.
static const std::string TAG("aace.core.NetworkInfoProviderEngineImpl");

/// Program Name for Metrics
static const std::string METRIC_PROGRAM_NAME_SUFFIX = "NetworkInfoProviderEngineImpl";

/// Counter metrics for Network Info provider
static const std::string METRIC_NETWORK_INFO_PROVIDER_NETWORK_STATUS_CHANGED = "NetworkStatusChanged";

std::shared_ptr<NetworkInfoProviderEngineImpl> NetworkInfoProviderEngineImpl::create() {
    try {
        auto networkInfoProviderEngineImpl =
            std::shared_ptr<NetworkInfoProviderEngineImpl>(new NetworkInfoProviderEngineImpl());
        ThrowIfNull(networkInfoProviderEngineImpl, "networkInfoProviderEngineImplIsNull");
        return networkInfoProviderEngineImpl;
    } catch (std::exception& ex) {
        AACE_ERROR(LX(TAG).d("reason", ex.what()));
        return nullptr;
    }
}

void NetworkInfoProviderEngineImpl::addObserver(std::shared_ptr<NetworkInfoObserver> observer) {
    std::lock_guard<std::mutex> lock(m_mutex);
    m_observers.insert(observer);
}

void NetworkInfoProviderEngineImpl::removeObserver(std::shared_ptr<NetworkInfoObserver> observer) {
    std::lock_guard<std::mutex> lock(m_mutex);
    m_observers.erase(observer);
}

void NetworkInfoProviderEngineImpl::networkInfoChanged(NetworkStatus status, int wifiSignalStrength) {
    std::stringstream networkStatus;
    networkStatus << status;
    emitCounterMetrics(
        METRIC_PROGRAM_NAME_SUFFIX,
        "networkInfoChanged",
        {METRIC_NETWORK_INFO_PROVIDER_NETWORK_STATUS_CHANGED, networkStatus.str()});
    std::lock_guard<std::mutex> lock(m_mutex);

    for (const auto& next : m_observers) {
        next->onNetworkInfoChanged(status, wifiSignalStrength);
    }
}

bool NetworkInfoProviderEngineImpl::setNetworkInterface(const std::string& networkInterface) {
    try {
        AACE_INFO(LX(TAG).sensitive("networkInterface", networkInterface));

        std::lock_guard<std::mutex> lock(m_mutex);

        //Notify the begin
        for (const auto& next : m_observers) {
            next->onNetworkInterfaceChangeStatusChanged(
                networkInterface, NetworkInfoObserver::NetworkInterfaceChangeStatus::BEGIN);
        }

        //Notify to Change network interface
        for (const auto& next : m_observers) {
            next->onNetworkInterfaceChangeStatusChanged(
                networkInterface, NetworkInfoObserver::NetworkInterfaceChangeStatus::CHANGE);
        }

        // Notify Completed
        for (const auto& next : m_observers) {
            next->onNetworkInterfaceChangeStatusChanged(
                networkInterface, NetworkInfoObserver::NetworkInterfaceChangeStatus::COMPLETED);
        }

        return true;

    } catch (std::exception& ex) {
        AACE_ERROR(LX(TAG).d("reason", ex.what()));
        return false;
    }
}

bool NetworkInfoProviderEngineImpl::setNetworkHttpProxyHeader(const std::string& headers) {
    AACE_DEBUG(LX(TAG));
    try {
        ThrowIf(headers.empty(), "proxyHeadersEmpty");

        auto headersVector = std::vector<std::string>{};
        auto ss = std::stringstream{headers};

        for (std::string line; std::getline(ss, line, '\n');) {
            headersVector.push_back(line);
        }

        std::lock_guard<std::mutex> lock(m_mutex);
        for (const auto& next : m_observers) {
            next->onNetworkProxyHeadersAvailable(headersVector);
        }
        return true;
    } catch (std::exception& ex) {
        AACE_ERROR(LX(TAG).d("reason", ex.what()));
        return false;
    }
}

}  // namespace network
}  // namespace engine
}  // namespace aace
