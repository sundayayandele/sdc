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
package org.openecomp.sdc.versioning.errors;

import org.junit.Assert;
import org.junit.Test;
import org.openecomp.sdc.common.errors.ErrorCategory;
import org.openecomp.sdc.common.errors.ErrorCode;


public class EditOnEntityLockedByOtherErrorBuilderTest {
    @Test
    public void test() {
        EditOnEntityLockedByOtherErrorBuilder builder = new EditOnEntityLockedByOtherErrorBuilder("entityType",
                "entityId", "lockingUser");

        ErrorCode build = builder.build();
        Assert.assertEquals(VersioningErrorCodes.EDIT_ON_ENTITY_LOCKED_BY_OTHER_USER, build.id());
        Assert.assertEquals(ErrorCategory.APPLICATION, build.category());
        Assert.assertEquals(String.format(EditOnEntityLockedByOtherErrorBuilder.EDIT_ON_ENTITY_LOCKED_BY_OTHER_USER_MSG,
                "entityType", "entityId", "lockingUser"),build.message());
    }
}