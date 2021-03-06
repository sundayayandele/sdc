/*-
 * ============LICENSE_START=======================================================
 * SDC
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights reserved.
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

package org.openecomp.sdc.be.model;

import org.openecomp.sdc.be.datatypes.elements.ArtifactDataDefinition;
import org.openecomp.sdc.be.datatypes.elements.HeatParameterDataDefinition;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArtifactDefinition extends ArtifactDataDefinition {

    /**
     * Base64 encoded Artifact file data
     */
    private byte[] payloadData;

    private Boolean isHeatParamUpdated = false;

    public Boolean getHeatParamUpdated() {
        return isHeatParamUpdated;
    }

    public void setHeatParamUpdated(Boolean heatParamUpdated) {
        isHeatParamUpdated = heatParamUpdated;
    }

    public ArtifactDefinition() {
    }

    public ArtifactDefinition(Map<String, Object> art) {
        super(art);
    }

    public ArtifactDefinition(ArtifactDataDefinition a) {
        super(a);
    }

    public ArtifactDefinition(ArtifactDefinition a) {
        super(a);
        this.payloadData = a.payloadData;
    }

    public ArtifactDefinition(ArtifactDataDefinition a, String payloadData) {
        super(a);
        setPayloadData(payloadData);
    }

    public byte[] getPayloadData() {
        return payloadData;
    }

    public void setPayload(byte[] payloadData) {
        this.payloadData = payloadData;
    }

    public void setPayloadData(String payloadData) {
        if (payloadData != null) {
            this.payloadData = payloadData.getBytes();
        }
    }

    public List<HeatParameterDefinition> getListHeatParameters() {
        List<HeatParameterDefinition> res = null;
        List<HeatParameterDataDefinition> heatParameters = super.getHeatParameters();
        if (heatParameters != null) {
            res = heatParameters.stream().map(HeatParameterDefinition::new).collect(Collectors.toList());
        }
        return res;
    }

    public void setListHeatParameters(List<HeatParameterDefinition> properties) {
        List<HeatParameterDataDefinition> res = null;

        if (properties != null) {
            res = properties.stream().map(HeatParameterDataDefinition::new).collect(Collectors.toList());
        }
        super.setHeatParameters(res);
    }



	public boolean checkEsIdExist() {
		return (getEsId() != null) && (!getEsId().trim().isEmpty());
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();

        result = prime * result + Arrays.hashCode(payloadData);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ArtifactDefinition other = (ArtifactDefinition) obj;

        if (payloadData == null) {
            if (other.payloadData != null) {
                return false;
            }
        }
        else if (!Arrays.equals(payloadData, other.payloadData)) {
            return false;
        }
        return true;
    }
}
