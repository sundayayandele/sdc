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

package org.openecomp.sdc.ci.tests.utils.general;

import static org.testng.AssertJUnit.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.aventstack.extentreports.Status;
import com.clearspring.analytics.util.Pair;
import com.google.gson.Gson;
import fj.data.Either;

import org.apache.commons.codec.binary.Base64;
import org.openecomp.sdc.be.model.Component;
import org.openecomp.sdc.be.model.Resource;
import org.openecomp.sdc.be.model.User;
import org.openecomp.sdc.ci.tests.api.Urls;
import org.openecomp.sdc.ci.tests.config.Config;
import org.openecomp.sdc.ci.tests.datatypes.AmdocsLicenseMembers;
import org.openecomp.sdc.ci.tests.datatypes.ResourceReqDetails;
import org.openecomp.sdc.ci.tests.datatypes.ServiceReqDetails;
import org.openecomp.sdc.ci.tests.datatypes.VendorSoftwareProductObject;
import org.openecomp.sdc.ci.tests.datatypes.enums.ServiceCategoriesEnum;
import org.openecomp.sdc.ci.tests.datatypes.enums.UserRoleEnum;
import org.openecomp.sdc.ci.tests.datatypes.http.HttpHeaderEnum;
import org.openecomp.sdc.ci.tests.datatypes.http.HttpRequest;
import org.openecomp.sdc.ci.tests.datatypes.http.RestResponse;
import org.openecomp.sdc.ci.tests.utils.Utils;
import org.openecomp.sdc.ci.tests.utils.rest.BaseRestUtils;

import com.clearspring.analytics.util.Pair;
import com.google.gson.Gson;

import fj.data.Either;

public class OnboardingUtillViaApis {

//	protected static Map<String, String> prepareHeadersMap(String userId) {
//		Map<String, String> headersMap = new HashMap<String, String>();
//		headersMap.put(HttpHeaderEnum.CONTENT_TYPE.getValue(), "application/json");
//		headersMap.put(HttpHeaderEnum.ACCEPT.getValue(), "application/json");
//		headersMap.put(HttpHeaderEnum.USER_ID.getValue(), userId);
//		return headersMap;
//	}

	public static Pair<String, VendorSoftwareProductObject> createVspViaApis(ResourceReqDetails resourceReqDetails, String filepath, String vnfFile, User user) throws Exception {

		VendorSoftwareProductObject vendorSoftwareProductObject = new VendorSoftwareProductObject();

		AmdocsLicenseMembers amdocsLicenseMembers = VendorLicenseModelRestUtils.createVendorLicense(user);
		Pair<String, VendorSoftwareProductObject> createVendorSoftwareProduct = VendorSoftwareProductRestUtils.createVendorSoftwareProduct(resourceReqDetails, vnfFile, filepath, user, amdocsLicenseMembers);
		VendorSoftwareProductObject map = createVendorSoftwareProduct.right;
		vendorSoftwareProductObject.setAttContact(map.getAttContact());
		vendorSoftwareProductObject.setCategory(map.getCategory());
		vendorSoftwareProductObject.setComponentId(map.getComponentId());
		vendorSoftwareProductObject.setDescription(map.getDescription());
		vendorSoftwareProductObject.setSubCategory(map.getSubCategory());
		vendorSoftwareProductObject.setVendorName(map.getVendorName());
		vendorSoftwareProductObject.setVspId(map.getVspId());
		Pair<String, VendorSoftwareProductObject> pair = new Pair<String, VendorSoftwareProductObject>(createVendorSoftwareProduct.left, vendorSoftwareProductObject);
		return pair;
	}
	
/*	public static Resource createResourceFromVSP(Pair<String, Map<String, String>> createVendorSoftwareProduct, String vspName) throws Exception {
		List<String> tags = new ArrayList<>();
		tags.add(vspName);
		Map<String, String> map = createVendorSoftwareProduct.right;
		ResourceReqDetails resourceDetails = new ResourceReqDetails();
		resourceDetails.setCsarUUID(map.get("vspId"));
		resourceDetails.setCsarVersion("1.0");
		resourceDetails.setName(vspName);
		resourceDetails.setTags(tags);
		resourceDetails.setDescription(map.get("description"));
		resourceDetails.setResourceType(map.get("componentType"));
		resourceDetails.addCategoryChain(ResourceCategoryEnum.GENERIC_DATABASE.getCategory(), ResourceCategoryEnum.GENERIC_DATABASE.getSubCategory());
		resourceDetails.setVendorName(map.get("vendorName"));
		resourceDetails.setVendorRelease("1.0");
		resourceDetails.setResourceType("VF");
		resourceDetails.setResourceVendorModelNumber("666");
		resourceDetails.setContactId(map.get("attContact"));
		resourceDetails.setIcon("defaulticon");
		Resource resource = AtomicOperationUtils.createResourceByResourceDetails(resourceDetails, UserRoleEnum.DESIGNER, true).left().value();
		
		return resource; 
	}*/
	public static Resource createResourceFromVSP(ResourceReqDetails resourceDetails) throws Exception {
		Resource resource = AtomicOperationUtils.createResourceByResourceDetails(resourceDetails, UserRoleEnum.DESIGNER, true).left().value();
		return resource;

	}
	
	public static Resource createResourceFromVSP(ResourceReqDetails resourceDetails, UserRoleEnum userRole) throws Exception {
		Resource resource = AtomicOperationUtils.createResourceByResourceDetails(resourceDetails, userRole, true).left().value();
		return resource;

	}
	
	public static void downloadToscaCsarToDirectory(Component component, File file) {
		try {
			Either<String, RestResponse> componentToscaArtifactPayload = AtomicOperationUtils.getComponenetArtifactPayload(component, "assettoscacsar");
			if(componentToscaArtifactPayload.left().value() != null){
				convertPayloadToFile(componentToscaArtifactPayload.left().value(), file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static void convertPayloadToFile(String payload, File file, boolean isBased64, boolean isSdcFormat) throws IOException{
//		
//		Gson gson = new Gson();
//		byte[] byteArray = null;
//		Map<String, String> fromJson;
//		@SuppressWarnings("unchecked")
//		String string = null;// = fromJson.get("base64Contents").toString();
//		if(isSdcFormat){
//			fromJson = gson.fromJson(payload, Map.class);
//			string = fromJson.get("base64Contents").toString();
//		}else if (isBased64) {
//			byteArray = Base64.decode(string.getBytes(StandardCharsets.UTF_8));
//		}else{
//			byteArray = payload.getBytes(StandardCharsets.UTF_8);
//		}
//		File downloadedFile = new File(file.getAbsolutePath());
//		FileOutputStream fos = new FileOutputStream(downloadedFile);
//		fos.write(byteArray);
//		fos.flush();
//		fos.close();
//		
//	}

	public static void convertPayloadToFile(String payload, File file) throws IOException{
		
		Gson gson = new Gson();
		@SuppressWarnings("unchecked")
		Map<String, String> fromJson = gson.fromJson(payload, Map.class);
		String string = fromJson.get("base64Contents").toString();
		byte[] byteArray = Base64.decodeBase64(string.getBytes(StandardCharsets.UTF_8));
		File downloadedFile = new File(file.getAbsolutePath());
		FileOutputStream fos = new FileOutputStream(downloadedFile);
		fos.write(byteArray);
		fos.flush();
		fos.close();
	}
	
	
	public static void convertPayloadToZipFile(String payload, File file) throws IOException{
		
		byte[] byteArray = payload.getBytes(StandardCharsets.ISO_8859_1);
		File downloadedFile = new File(file.getAbsolutePath());
		FileOutputStream fos = new FileOutputStream(downloadedFile);
		fos.write(byteArray);
		fos.flush();
		fos.close();
		
		
//		ZipOutputStream fos = null;
//		
//		
//		for (Charset charset : Charset.availableCharsets().values()) {
//			try{
//		//		System.out.println("How to do it???");
//				File downloadedFile = new File(file.getAbsolutePath() + "_" + charset +".csar");
//				fos = new ZipOutputStream(new FileOutputStream(downloadedFile));
//				byte[] byteArray = payload.getBytes(charset);
//				fos.write(byteArray);
//				fos.flush();
//				
//			}
//			catch(Exception e){
//				fos.close();
//			}
//		}
		System.out.println("");
		
//		ZipInputStream zipStream = new ZipInputStream(new ByteArrayInputStream(byteArray));
//		ZipEntry entry = null;
//		while ((entry = zipStream.getNextEntry()) != null) {
//
//		    String entryName = entry.getName();
//
//		    FileOutputStream out = new FileOutputStream(file+"/"+entryName);
//
//		    byte[] byteBuff = new byte[4096];
//		    int bytesRead = 0;
//		    while ((bytesRead = zipStream.read(byteBuff)) != -1)
//		    {
//		        out.write(byteBuff, 0, bytesRead);
//		    }
//
//		    out.close();
//		    zipStream.closeEntry();
//		}
//		zipStream.close();
//		
		
		
		
		BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(payload.getBytes(StandardCharsets.ISO_8859_1)));
		String filePath = file.toString();
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
		int inByte;
		while((inByte = bis.read()) != -1) bos.write(inByte);
		bis.close();
		bos.close();
	}
	
	public static Either<String, RestResponse> getVendorSoftwareProduct(String vspId, User user, Boolean validateState) throws Exception {

		Config config = Utils.getConfig();
		String url = String.format(Urls.GET_VENDOR_SOFTWARE_PRODUCT, config.getOnboardingBeHost(), config.getOnboardingBePort(), vspId);
		String userId = user.getUserId();
		Map<String, String> headersMap = OnboardingUtils.prepareHeadersMap(userId);
		headersMap.put(HttpHeaderEnum.X_ECOMP_REQUEST_ID_HEADER.getValue(), "123456");
		headersMap.put(HttpHeaderEnum.ACCEPT.getValue(), "*/*");
		headersMap.put("Accept-Encoding", "gzip, deflate, br");
		HttpRequest http = new HttpRequest();
		RestResponse response = http.httpSendGet(url, headersMap);
		if (validateState) {
			assertTrue("add property to resource failed: " + response.getResponseMessage(), response.getErrorCode() == BaseRestUtils.STATUS_CODE_SUCCESS);
		}
		if (response.getErrorCode() != BaseRestUtils.STATUS_CODE_SUCCESS && response.getResponse().getBytes() == null && response.getResponse().getBytes().length == 0) {
			return Either.right(response);
		}
		return Either.left(response.getResponse());
		
	}

	public static ResourceReqDetails prepareOnboardedResourceDetailsBeforeCreate(ResourceReqDetails resourceDetails, VendorSoftwareProductObject vendorSoftwareProductObject) {

		List<String> tags = new ArrayList<>();
		tags.add(vendorSoftwareProductObject.getName());
//		ResourceReqDetails resourceDetails = new ResourceReqDetails();
		resourceDetails.setCsarUUID(vendorSoftwareProductObject.getVspId());
		resourceDetails.setCsarVersion(vendorSoftwareProductObject.getVersion());
		resourceDetails.setName(vendorSoftwareProductObject.getName());
		resourceDetails.setTags(tags);
		resourceDetails.setDescription(vendorSoftwareProductObject.getDescription());
//		resourceDetails.addCategoryChain(ResourceCategoryEnum.GENERIC_DATABASE.getCategory(), ResourceCategoryEnum.GENERIC_DATABASE.getSubCategory());
		resourceDetails.setVendorName(vendorSoftwareProductObject.getVendorName());
//		resourceDetails.setVendorRelease("1.0");
		resourceDetails.setResourceType("VF");
		resourceDetails.setResourceVendorModelNumber("666");
		resourceDetails.setContactId(vendorSoftwareProductObject.getAttContact());
//		resourceDetails.setIcon("defaulticon");

		return resourceDetails;
	}
	
	public static ServiceReqDetails prepareServiceDetailsBeforeCreate(User user) {

		ServiceReqDetails serviceDetails = ElementFactory.getDefaultService(ServiceCategoriesEnum.NETWORK_L4, user);
		serviceDetails.setServiceType("MyServiceType");
		serviceDetails.setServiceRole("MyServiceRole");
		serviceDetails.setNamingPolicy("MyServiceNamingPolicy");
		serviceDetails.setEcompGeneratedNaming(false);
		
		return serviceDetails;
	}
}
