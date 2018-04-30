package org.openecomp.sdc.asdctool.impl.validator.tasks.artifacts;

import org.junit.Test;


public class ArtifactsVertexResultTest {

	private ArtifactsVertexResult createTestSubject() {
		return new ArtifactsVertexResult();
	}

	
	@Test
	public void testAddNotFoundArtifact() {
		ArtifactsVertexResult testSubject;
		String artifactId = "";

		// default test
		testSubject = createTestSubject();
		testSubject.addNotFoundArtifact(artifactId);
	}

	
	@Test
	public void testGetResult() {
		ArtifactsVertexResult testSubject;
		String result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.getResult();
	}
}