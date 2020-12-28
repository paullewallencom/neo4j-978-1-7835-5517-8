package neo4j.rest.client;

import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class MyRestClient {

	public static void main(String[] args) {
		MyRestClient jersey = new MyRestClient();
		jersey.createNode();
		jersey.sendCypher();

		
	}

	public void createNode(){
		
		//Create a REST Client
		Client client = Client.create();
		//Define a resource (REST Endpoint) which needs to be Invoked 
		//for creating a Node
		WebResource resource = client.resource("http://localhost:7474").path("/db/data/node");

		//Define properties for the node.
		JSONObject node = new JSONObject();
		node.append("Name", "John");
		
		System.out.println(node.toString());
		
		//Invoke the rest endpoint as JSON request
		ClientResponse res = resource.accept(MediaType.APPLICATION_JSON)
		.entity(node.toString())
		.post(ClientResponse.class);
		//Print the URI of the new Node
		System.out.println("URI of New Node = " + res.getLocation());		
	}

	public void sendCypher() {
		//Create a REST Client
		Client client = Client.create();
		//Define a resource (REST Endpoint) which needs to be Invoked 
		//for executing Cypher Query
		WebResource resource = client.resource("http://localhost:7474").path("/db/data/cypher");
		//Define JSON Object and Cypher Query
		JSONObject cypher = new JSONObject();
		cypher.accumulate("query", "match n return n limit 10");

		//Invoke the rest endpoint as JSON request
		ClientResponse res = resource.accept(MediaType.APPLICATION_JSON).entity(cypher.toString())
		.post(ClientResponse.class);
		//Print the response received from the Server
		System.out.println(res.getEntity(String.class));

    }
}
