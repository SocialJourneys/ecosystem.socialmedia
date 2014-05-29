package uk.ac.dotrural.irp.ecosystem.socialmedia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class SpinRuleRunnerTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInference() throws IOException {
		SpinRuleRunner srr = new SpinRuleRunner();
		OntModel baseModel = srr.createBaseModel();
		OntModel dataModel = createTestDataModel();
		baseModel.add(dataModel);
 
		Model infModel = srr.executeRules(
				"resources/spinrules/disruptionInf.n3", baseModel);
		infModel.write(System.out, "N3");
	}

	private OntModel createTestDataModel() throws FileNotFoundException {
		OntModel model = ModelFactory.createOntologyModel();
		model.read(new FileInputStream(
				new File("resources/data/sampleTweet.n3")), null, "N3");
		return model;
	}
}



