package neo4j.embedded.myserver;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class EmbeddedServer {

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {

		// Registers a shutdown hook for the Neo4j.
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				// Shutdown the Database
				System.out.println("Server is shutting down");
				graphDb.shutdown();
			}
		});
	}

	public static void main(String[] args) {
	  // Create a new Object of Graph Database
	  GraphDatabaseService graphDb = new GraphDatabaseFactory()
	  .newEmbeddedDatabase("Location of Storing Neo4j Database Files");
	  System.out.println("Server is up and Running");
	  // Register a Shutdown Hook
	  registerShutdownHook(graphDb);
	}
}
