package traverser;

import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.impl.util.StringLogger;

import scala.collection.Iterator;

public class Traversals {

	// This should contain the path of your Neo4j Datbase which is
	// generally found at <$NEO4J_HOME>/data/graph.db
	// private static final String MOVIE_DB = “$NEO4J_HOME/data/graph.db";
	private static final String MOVIE_DB = "D:\\myWork\\Neo4J\\neo4j-community-2.1.5\\data\\graph.db";
	private GraphDatabaseService graphDb;

	public static void main(String[] args) {
		Traversals movies = new Traversals();
		movies.startTraversing();

	}

	private void startTraversing() {
		// Initialize Graph Database
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(MOVIE_DB);

		// Start a Transaction
		try (Transaction tx = graphDb.beginTx()) {

			// get the Traversal Descriptor from instance of GGraph DB
			TraversalDescription trvDesc = graphDb.traversalDescription();
			// Defining Traversals need to use Depth First Approach
			trvDesc = trvDesc.depthFirst();

			// Instructing to exclude the Start Position and include all others
			// while Traversing
			trvDesc = trvDesc.evaluator(Evaluators.excludeStartPosition());
			// Defines the depth of the Traversals. Higher the Integer, more
			// deep would be traversals.
			// Default value would be to traverse complete Tree
			trvDesc = trvDesc.evaluator(Evaluators.toDepth(3));

			// Get a Traverser from Descriptor
			Traverser traverser = trvDesc.traverse(getStartNode());

			// Let us get the Paths from Traverser and start iterating or moving
			// along the Path
			for (Path path : traverser) {
				// Print ID of Start Node
				System.out.println("Start Node ID = " + path.startNode().getId());
				// Print number of relationships between Start and End //Node
				System.out.println("No of Relationships = " + path.length());
				// Print ID of End Node
				System.out.println("End Node ID = " + path.endNode().getId());

			}
		}
		graphDb.shutdown();
	}

	private Node getStartNode() {
		try (Transaction tx = graphDb.beginTx()) {
			ExecutionEngine engine = new ExecutionEngine(graphDb, StringLogger.SYSTEM);
			ExecutionResult result = engine.execute("match (n)-[r]->() where n.Name=\"Sylvester Stallone\" return n as RootNode");
			Iterator<Object> iter = result.columnAs("RootNode");

			return (Node) iter.next();

		}
	}

}
