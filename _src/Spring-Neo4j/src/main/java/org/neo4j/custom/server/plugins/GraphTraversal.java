package org.neo4j.custom.server.plugins;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.Description;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.graphdb.traversal.Uniqueness;
import org.neo4j.kernel.impl.util.StringLogger;
import org.neo4j.server.plugins.Name;
import org.neo4j.server.plugins.Parameter;
import org.neo4j.server.plugins.PluginTarget;
import org.neo4j.server.plugins.ServerPlugin;
import org.neo4j.server.plugins.Source;

import scala.collection.Iterator;

@Description("An extension to the Neo4j Server for Traversing graph till 3 levels in all directions from a given Node")
public class GraphTraversal extends ServerPlugin {
	@Name("graph_traversal")
	@org.neo4j.server.plugins.Description("Traversing Graphs from a given Node")
	@PluginTarget(GraphDatabaseService.class)
	public Iterable<Path> executeTraversal(
			@Source GraphDatabaseService graphDb,
			@org.neo4j.server.plugins.Description("Value of 'Name' property, considered as RootNode for searching ") 
			@Parameter(name = "name") String name) {

		return startTraversing(graphDb, name);

	}
	


	public enum RelTypes implements RelationshipType {
		ACTED_IN, DIRECTED 
	}

	private List<Path> startTraversing(GraphDatabaseService graphDb, String name) {
		List<Path> allPaths = new ArrayList<Path>();
		// Start a Transaction
		try (Transaction tx = graphDb.beginTx()) {
			// get the Traversal Descriptor from instance of GGraph DB
			TraversalDescription trvDesc = graphDb.traversalDescription();
			// Defining Traversals needs to use Depth First Approach
			trvDesc = trvDesc.depthFirst();
			// Instructing to exclude the Start Position and include all 
			// other while Traversing
			trvDesc = trvDesc.evaluator(Evaluators.excludeStartPosition());
			// Defines the depth of the Traversals. Higher the Integer, more
			// deep would be traversals.
			// Default value would be to traverse complete Tree
			trvDesc = trvDesc.evaluator(Evaluators.toDepth(3));
			//Define Uniqueness in visiting Nodes while Traversing
			trvDesc = trvDesc.uniqueness(Uniqueness.NODE_GLOBAL);
			//Traverse only specific type of relationship
			trvDesc = trvDesc.relationships(RelTypes.ACTED_IN, Direction.BOTH);
			
			// Get a Traverser from Descriptor
			Traverser traverser = trvDesc.traverse(getStartNode(graphDb,name));

			// Let us get the Paths from Traverser and start iterating or moving
			// along the Path
			for (Path path : traverser) {
				//Add Paths
				allPaths.add(path);
			}
			//Return Paths
			return allPaths;
		}
		
	}
	
	/**
	 * Get a Root Node as a Start Node for Traversal.
	 * @param graphDb
	 * @param name
	 * @return Root Node
	 */
	private Node getStartNode(GraphDatabaseService graphDb, String name) {
		try (Transaction tx = graphDb.beginTx()) {
			ExecutionEngine engine = new ExecutionEngine(graphDb, StringLogger.SYSTEM);
			String cypherQuery = "match (n)-[r]->() where n.Name=\""+name+"\" return n as RootNode";
			ExecutionResult result = engine.execute(cypherQuery);
			Iterator<Object> iter = result.columnAs("RootNode");
			//Check to see that we do not have empty Iterator
			if(iter==null || iter.isEmpty()){
				
				return null;
			}
			return (Node) iter.next();

		}
	}

}
