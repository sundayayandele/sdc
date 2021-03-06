/*
 * Copyright © 2016-2018 European Support Limited
 *
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
 */

package org.openecomp.core.zusammen.db;

import com.amdocs.zusammen.adaptor.inbound.api.types.item.*;
import com.amdocs.zusammen.commons.health.data.HealthInfo;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.item.*;
import com.amdocs.zusammen.datatypes.itemversion.ItemVersionRevisions;
import com.amdocs.zusammen.datatypes.itemversion.Tag;

import java.util.Collection;

public interface ZusammenConnector {

  Collection<HealthInfo> checkHealth(SessionContext sessionContext);

  String getVersion(SessionContext sessionContext);

  Collection<Item> listItems(SessionContext context);

  Item getItem(SessionContext context, Id itemId);

  Id createItem(SessionContext context, Info info);

  void deleteItem(SessionContext context, Id itemId);

  void updateItem(SessionContext context, Id itemId, Info info);


  Collection<ItemVersion> listPublicVersions(SessionContext context, Id itemId);

  ItemVersion getPublicVersion(SessionContext context, Id itemId, Id versionId);

  Id createVersion(SessionContext context, Id itemId, Id baseVersionId,
                   ItemVersionData itemVersionData);

  void updateVersion(SessionContext context, Id itemId, Id versionId,
                     ItemVersionData itemVersionData);

  ItemVersion getVersion(SessionContext context, Id itemId, Id versionId);

  ItemVersionStatus getVersionStatus(SessionContext context, Id itemId, Id versionId);

  void tagVersion(SessionContext context, Id itemId, Id versionId, Tag tag);

  void resetVersionRevision(SessionContext context, Id itemId, Id versionId, Id revisionId);

  void revertVersionRevision(SessionContext context, Id itemId, Id versionId, Id revisionId);

  ItemVersionRevisions listVersionRevisions(SessionContext context, Id itemId, Id versionId);

  void publishVersion(SessionContext context, Id itemId, Id versionId, String message);

  void syncVersion(SessionContext context, Id itemId, Id versionId);

  void forceSyncVersion(SessionContext context, Id itemId, Id versionId);

  void cleanVersion(SessionContext context, Id itemId, Id versionId);

  ItemVersionConflict getVersionConflict(SessionContext context, Id itemId, Id versionId);


  Collection<ElementInfo> listElements(SessionContext context, ElementContext elementContext,
                                       Id parentElementId);

  ElementInfo getElementInfo(SessionContext context, ElementContext elementContext, Id elementId);

  Element getElement(SessionContext context, ElementContext elementContext, Id elementId);

  ElementConflict getElementConflict(SessionContext context, ElementContext elementContext,
                                     Id elementId);

  Element saveElement(SessionContext context, ElementContext elementContext,
                      Element element, String message);

  void resolveElementConflict(SessionContext context, ElementContext elementContext,
                              Element element, Resolution resolution);

  void resetVersionHistory(SessionContext context, Id itemId, Id versionId, String changeRef);
}
