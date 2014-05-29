package uk.ac.dotrural.irp.ecosystem.socialmedia;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ontotext.kim.client.corpora.KIMAnnotation;
import com.ontotext.kim.client.corpora.KIMFeatureMap;

public class AnnotationCreator {

	private String TEMPLATE = "prefix oa: <http://www.w3.org/ns/oa#> INSERT DATA"
			+ "{<%s> a <http://www.dotrural.ac.uk/irp/uploads/ontologies/KIMEntity#KIMAnnotation>;"
			+ "oa:hasBody <%s>;oa:hasTarget <%s>;oa:start \"%s\";oa:end \"%s\";"
			+ "}";

	public List<String> createAnnotations(String tweetUri,
			List<KIMAnnotation> annotations, String baseNs) {

		List<String> updates = new ArrayList<String>();
		for (KIMAnnotation a : annotations) {
			KIMFeatureMap map = a.getFeatures();
			String aType = (String)map.get("class");
			if (aType.equals("http://proton.semanticweb.org/2006/05/protont#TimeInterval")) {
				
			} else {
				updates.add(String.format(TEMPLATE, baseNs
						+ UUID.randomUUID().toString(), map.get("inst").toString(), tweetUri,
						a.getStartOffset(), a.getEndOffset()));
			}
		}
		return updates;
	}
}
