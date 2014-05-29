package uk.ac.dotrural.irp.ecosystem.socialmedia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.topbraid.spin.inference.SPINInferences;
import org.topbraid.spin.system.SPINModuleRegistry;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.update.UpdateAction;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateRequest;

public class SpinRuleRunner {

	public OntModel createBaseModel() throws IOException {
		OntModel model = ModelFactory.createOntologyModel();
		for (File f : new File("resources/ontologies").listFiles()) {
			FileInputStream fis = new FileInputStream(f);
			String format = "RDF/XML";
			if (f.getName().endsWith(".n3")) {
				format = "N3";
			} else if (f.getName().endsWith(".ttl")) {
				format = "TTL";
			}
			String base = null;
			if (f.getName().equals("protons.owl")) {
				base = "http://proton.semanticweb.org/2006/05/protons#";
			} else
				base = null;
			//if (!("spl.spin.ttl".equals(f.getName()))){
				System.out.println(f.getName());
				model.read(fis, base, format); 
			//}
			fis.close();
		}
		return model;
	}

	public Model executeRules(String rulesFile, OntModel model)
			throws FileNotFoundException {

		System.out.println(rulesFile);
		model.read(new FileInputStream(rulesFile), null, "N3");
		System.out.println("done");

		// create and add model for inferred triples
		Model newTriples = ModelFactory.createDefaultModel();
		model.addSubModel(newTriples);

		// init spin registry
		SPINModuleRegistry.get().init();
		
		// register locally defined functions
		SPINModuleRegistry.get().registerAll(model, null);

		// Run all inferences
		int i = SPINInferences.run(model, newTriples, null, null, false, null);
		System.out.println("run returned " + i);

		return newTriples;

	}

	public OntModel createDataModel(List<String> updates) {
		OntModel model = ModelFactory.createOntologyModel();

		for (String update : updates){
			UpdateRequest ur = UpdateFactory.create();
			System.out.println(update);
			ur.add(update);
			UpdateAction.execute(ur, model.getBaseModel());
		}


		return model;
	}

}


