package uk.ac.dotrural.irp.ecosystem.socialmedia;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import com.ontotext.kim.client.corpora.KIMAnnotation;
import com.ontotext.kim.client.corpora.KIMFeatureMap;

import junit.framework.TestCase;

public class EntityExtractorTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public static final String clrMsg = "M8 J23 - J22 West of M77 - Hazard, Aberdeen 2 lanes restricted Westbound for up to 15 minutes has been cleared";
	public static final String srcMsg = "M8 J23 - J22 West of M77 - Hazard, Lockerbie 2 lanes restricted Westbound for up to 15 minutes http://tscot.org/01c53539";
 
	public void testIdentifyEntities() throws RemoteException, NotBoundException {
		EntityExtractor ee = new EntityExtractor();
		List<KIMAnnotation> annotations = ee.identifyEntities(srcMsg);
		for (KIMAnnotation a : annotations){
			KIMFeatureMap map = a.getFeatures();
			for (String key : map.keySet()){
				System.out.print(key + ":" + map.get(key) + "; ");
			}
			System.out.println(a.toString());
		}
	}
}
