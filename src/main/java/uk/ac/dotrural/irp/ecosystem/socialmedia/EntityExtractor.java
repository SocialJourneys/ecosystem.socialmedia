package uk.ac.dotrural.irp.ecosystem.socialmedia;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ontotext.kim.client.GetService;
import com.ontotext.kim.client.KIMService;
import com.ontotext.kim.client.corpora.KIMAnnotation;
import com.ontotext.kim.client.corpora.KIMAnnotationSet;
import com.ontotext.kim.client.semanticannotation.SemanticAnnotationAPI;

/**
 * Responsible for using Ontotext KIM platform to attempt to extract entities
 * from text string
 * 
 * @author david
 * 
 */
public class EntityExtractor {

	public List<KIMAnnotation> identifyEntities(String txt)
			throws RemoteException, NotBoundException {
		List<KIMAnnotation> annotations = new ArrayList<KIMAnnotation>();

		// this will try connect to KIM service on RMI_HOST and RMI_PORT
		// specified within
		// "./kim_connection.properties" file located in your application
		// running directory
		// if no such file or no such properties are defined the defaults are
		// "localhost:1099"
		KIMService serviceKim = GetService.from();
		SemanticAnnotationAPI apiSemAnn = serviceKim.getSemanticAnnotationAPI();
		KIMAnnotationSet kimASet = apiSemAnn.execute(txt);

		Iterator annIterator = kimASet.iterator();
		while (annIterator.hasNext()) {
			annotations.add((KIMAnnotation) annIterator.next());
		}

		return annotations;
	}

}
