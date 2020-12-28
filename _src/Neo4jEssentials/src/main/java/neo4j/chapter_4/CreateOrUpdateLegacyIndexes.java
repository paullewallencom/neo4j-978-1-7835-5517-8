package neo4j.chapter_4;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.*;
import org.neo4j.graphdb.index.*;

public class CreateOrUpdateLegacyIndexes {

	public static void main(String[] args) {
		new CreateOrUpdateLegacyIndexes().addDataToNodeIndex();
	}

	public void addDataToNodeIndex() {
		// String DBLocation = "$Neo4J_HOME/data/graph.db";
		String DBLocation = "D:\\myWork\\Neo4J\\neo4j-community-2.1.5\\data\\graph.db";
		// Get the reference of Graph Service
		GraphDatabaseService graphDb = new GraphDatabaseFactory()
		.newEmbeddedDatabase(DBLocation);
		// Start the Transaction.
		// try-with-resources is available only with Java 1.7 and above.
		try (Transaction tx = graphDb.beginTx()) {
			// Getting reference of Index Manager API
			IndexManager indexMgr = graphDb.index();
			// Creating New Index for Nodes and Relationship
			Index<Node> actorsIndex = indexMgr.forNodes("ArtistIndex");
			RelationshipIndex rolesIndex = indexMgr.forRelationships("RolesIndex");
			// Creating a Node
			Node node = graphDb.createNode();
			// Adding Label and properties to Node
			node.addLabel(DynamicLabel.label("Actors"));
			node.setProperty("Name", "Ralph Macchio");
			// Adding Node and its properties to the Index
			actorsIndex.add(node, "Name", node.getProperty("Name"));

			// Creating new node and Adding Label and properties
			Node movieNode = graphDb.createNode();
			movieNode.addLabel(DynamicLabel.label("Movie"));
			movieNode.setProperty("Title", "The Karate Kid II");
			// Define a relationship Type
			DynamicRelationshipType actsIn = DynamicRelationshipType.withName("ACTS_IN");
			// Create a relationship between Actor and Movie
			// and define it's property
			Relationship role1 = node.createRelationshipTo(movieNode, actsIn);
			role1.setProperty("Role", "Daniel LaRusso");
			rolesIndex.add(role1, "Role", role1.getProperty("Role"));

			// Search the ActorsIndex by using the exact Match of Key and value
			IndexHits<Node> hits = actorsIndex.get("Name", "Ralph Macchio");
			Node searchNode = hits.next();
			System.out.println("Node ID = " + searchNode.getId() + ", Name =  "	+ searchNode.getProperty("Name"));
			// Search the ActorsIndex for more than one property
			hits = actorsIndex.query("Name", "*a*");
			Node searchNode1 = hits.next();
			System.out.println("Node ID = " + searchNode1.getId() + ", Name =  " + searchNode1.getProperty("Name"));

			tx.success();
			tx.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		graphDb.shutdown();
	}
}
