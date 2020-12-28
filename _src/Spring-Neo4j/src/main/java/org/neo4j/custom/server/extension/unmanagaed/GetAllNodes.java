package org.neo4j.custom.server.extension.unmanagaed;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.shell.util.json.JSONObject;
import org.neo4j.tooling.GlobalGraphOperations;

//Context Path for exposing it as REST endpoint 
@Path("/getAllNodes")
public class GetAllNodes {

	private final GraphDatabaseService graphDb;

	//Injected by the Neo4j Server
	public GetAllNodes(@Context GraphDatabaseService graphDb) {
		this.graphDb = graphDb;
	}

	//Implementation Logic for exposing this as a GET response 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response nodeIDs() {
	
		Map<String, String> nodeIDs = new HashMap<String,String>();
		
		try (Transaction tx = graphDb.beginTx()) {
			//Get all Node ID's and put them into the MAP.
			//Key of Map needs to be appended by Node_ID, so that every entry is Unique
			for (Node node : GlobalGraphOperations.at(graphDb).getAllNodes()) {
				nodeIDs.put("Node_ID_"+Long.toString(node.getId()),Long.toString(node.getId()));
			}
			tx.success();
		}
		//Converting the Map Object into JSON String
		JSONObject jsonObj = new JSONObject(nodeIDs);
		
		//Returning a Success Status, along with the JSON response. 
		return Response
				.status(Status.OK)
				.entity(jsonObj.toString().getBytes(Charset.forName("UTF-8"))).build();
	}

}
