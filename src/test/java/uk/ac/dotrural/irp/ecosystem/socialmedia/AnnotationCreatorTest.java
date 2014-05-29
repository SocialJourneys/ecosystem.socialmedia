package uk.ac.dotrural.irp.ecosystem.socialmedia;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

import com.ontotext.kim.client.corpora.KIMAnnotation;

import junit.framework.TestCase;

public class AnnotationCreatorTest extends TestCase {

	public static final String TEST_NS = "http://www.example.com/";

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCreateAnnotations() throws RemoteException,
			NotBoundException {
		EntityExtractor ee = new EntityExtractor();
		List<KIMAnnotation> annotations = ee
				.identifyEntities(EntityExtractorTest.srcMsg);

		AnnotationCreator ac = new AnnotationCreator();
		List<String> updates = ac.createAnnotations(
				"http://www.example.com/tweet/" + UUID.randomUUID().toString(),
				annotations, TEST_NS);
		for (String s: updates){
			System.out.println(s);
		}

	}

}
