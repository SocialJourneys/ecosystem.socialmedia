package uk.ac.dotrural.irp.ecosystem.socialmedia;

import twitter4j.Status;

public class TweetRdfConverter {

	public String tweetRdf = "prefix bot: <http://www.dotrural.ac.uk/irp/uploads/ontologies/bottari#>\n"
			+ "prefix sioc: <http://rdfs.org/sioc/ns#>\n"
			+ "prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n"
			+ "insert data {"
			+ "<%s> a bot:Tweet;bot:messageId \"%s\""
			+ "sioc:content \"%s\"^^xsd:string;"
			+ "sioc:has_creator <%s>."
			+ "}";
	
	public String convert(Status tweet, String uriBase){
		String msg = tweet.getText();
		String tweetUri = uriBase+tweet.getId();
		String authorUri = uriBase+tweet.getUser().getId();
		
		String update = String.format(tweetRdf, 
				tweetUri, tweet.getId(), msg, authorUri
				
				);
		return update;
				
	}
	
}



