/*-
 * ============LICENSE_START=======================================================
 * SDC
 * ================================================================================
 * Copyright (C) 2019 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.openecomp.sdc.be.auditing.impl.externalapi;

import org.openecomp.sdc.be.resources.data.auditing.AuditingActionEnum;
import org.openecomp.sdc.be.resources.data.auditing.model.CommonAuditData;
import org.openecomp.sdc.be.resources.data.auditing.model.DistributionData;
import org.openecomp.sdc.be.resources.data.auditing.model.ResourceCommonInfo;
import org.openecomp.sdc.be.resources.data.auditing.model.ResourceVersionInfo;

public class AuditAssetListExternalApiEventFactory extends AuditExternalApiEventFactory {

    private static final String LOG_STR = "ACTION = \"%s\" CONSUMER_ID = \"%s\" RESOURCE_URL = \"%s\" STATUS = \"%s\" DESC = \"%s\"";

    public AuditAssetListExternalApiEventFactory(AuditingActionEnum action, CommonAuditData commonFields,
                                                  DistributionData distributionData) {
         super(action, commonFields, new ResourceCommonInfo(), distributionData,
                 ResourceVersionInfo.newBuilder()
                         .build(),
                 ResourceVersionInfo.newBuilder()
                         .build(),
                 null, null, null);
    }

    @Override
    public String getLogPattern() {
        return LOG_STR;
    }

    @Override
    public String[] getLogMessageParams() {
        return new String[] {event.getAction(), event.getConsumerId(), event.getResourceURL(),
                event.getStatus(), event.getDesc()};
    }
}
