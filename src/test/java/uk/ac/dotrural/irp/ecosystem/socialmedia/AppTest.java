package uk.ac.dotrural.irp.ecosystem.socialmedia;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.List;
import java.util.UUID;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.ontotext.kim.client.corpora.KIMAnnotation;
import com.ontotext.kim.client.corpora.KIMFeatureMap;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 * 
	 * @throws IOException
	 */
	public void testApp() throws NotBoundException, IOException {
		String tweetId = UUID.randomUUID().toString();

		// identify the entities
		EntityExtractor ee = new EntityExtractor();
		List<KIMAnnotation> annotations = ee
				.identifyEntities(EntityExtractorTest.srcMsg);
		System.out.println("Identified Entities");
		for (KIMAnnotation a : annotations) {
			System.out.println(a.toString());
		}

		// create the annotations
		AnnotationCreator ac = new AnnotationCreator();
		List<String> updates = ac.createAnnotations(
				"http://www.example.com/tweet/" + tweetId, annotations,
				AnnotationCreatorTest.TEST_NS);

		System.out.println("\n\n\nAnnotation updates");
		for (String update : updates)
			System.out.println(update);

		String tweetUpdate = "prefix bot: <http://www.dotrural.ac.uk/irp/uploads/ontologies/bottari#>\n"
				+ "prefix sioc: <http://rdfs.org/sioc/ns#>\n"
				+ "prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n"
				+ "insert data {"
				+ "<http://www.example.com/tweet/"
				+ tweetId
				+ "> a bot:Tweet;bot:messageId \"123456789\";"
				+ "sioc:content \""
				+ EntityExtractorTest.srcMsg
				+ "\"^^xsd:string." + "}";
		updates.add(tweetUpdate);

		// instantiate a model
		System.out.println("\n\n\nBuilding models");
		SpinRuleRunner runner = new SpinRuleRunner();
		OntModel baseModel = runner.createBaseModel();
		OntModel m = runner.createDataModel(updates);
		baseModel.add(m);

		Model infModel = runner
				.executeRules("resources/data/sampleTweet.n3", m);
		System.out.println("\n\n\nInferred Model");
		
		infModel.write(System.out);

	}
}
