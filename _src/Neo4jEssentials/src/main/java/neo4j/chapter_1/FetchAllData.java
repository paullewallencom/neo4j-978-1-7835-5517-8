package neo4j.chapter_1;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.tooling.GlobalGraphOperations;

public class FetchAllData {

	public static void main(String[] args) {
		// Complete path of the database files on local System
		// Use the same path “$NEO4J_HOME/data/graph.db”
		//String location = "$NEO4J_HOME/data/graph.db";
		String location = "D:\\myWork\\Neo4J\\neo4j-community-2.1.5\\data\\graph.db";

		// Create instance of Graph Database.
		GraphDatabaseFactory fac = new GraphDatabaseFactory();
		GraphDatabaseBuilder build = fac.newEmbeddedDatabaseBuilder(location);
		GraphDatabaseService dbService = build.newGraphDatabase();

		// Starting a Transaction...All CRUD operations should be in Transaction
		try (Transaction tx = dbService.beginTx()) {
			GlobalGraphOperations operations = GlobalGraphOperations.at(dbService);

			// Getting All Nodes
			for (Node node : operations.getAllNodes()) {
				System.out.println("Node Labels.....");

				// Get All Labels associated with that Node
				for (Label label : node.getLabels()) {
					System.out.println(label.name());
				}
				System.out.println("Node Properties = ");

				// Get All Keys/ Value associated with that Node
				for (String key : node.getPropertyKeys()) {
					System.out.println(key + " = " + node.getProperty(key));
				}

			}

		}
		dbService.shutdown();

	}

}
