package neo4j.tests;

import static org.assertj.neo4j.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.impl.util.StringLogger;
import org.neo4j.test.TestGraphDatabaseFactory;

import scala.collection.Iterator;

import com.graphaware.test.unit.GraphUnit;

public class Neo4jTest {

	private GraphDatabaseService graphDb;
	
	@Before
	public  void createTestDatabase() throws Exception{
		try {

			System.out.println("Set up Invoked");
			graphDb = new TestGraphDatabaseFactory().newImpermanentDatabase();
	
		} catch (Exception e) {
			System.err.println("Cannot setup the Test Database....check Logs for Stack Traces");
			throw e;
		}

	}

	@After
	public void cleanupTestDatabase() throws Exception {
		try {

			System.out.println("Clean up Invoked1");			
			graphDb.shutdown();
			

		} catch (Exception e) {
			System.err.println("Cannot cleanup Test Database....check Logs for Stack Traces");
			throw e;
		}
	}


	@org.junit.Test
	public void nodeCreationWithLabel() {
		//Open a Transaction 
		try(Transaction transaction=graphDb.beginTx()){
			String testLabel="TestNode";
			//Create a Node with Label in the neo4j Database
			graphDb.createNode(DynamicLabel.label(testLabel));
			
			//Execute Cypher query and retrieve all Nodes from Neo4j Database 
			ExecutionEngine engine = new ExecutionEngine(graphDb, StringLogger.SYSTEM);
			String query = "MATCH (n) return n";
			ExecutionResult result = engine.execute(query);
			Iterator <Object> objResult = result.columnAs("n");
			
			//Check that Database has only 1 Node and not more than 1 node
			Assert.assertTrue(objResult.size()==1);
			
			while(objResult.hasNext()){
				Node cypherNode = (Node)objResult.next();
				//Check that Label matches with the same Label what was created initially
				Assert.assertTrue(cypherNode.hasLabel(DynamicLabel.label(testLabel)));
			}
			
			transaction.success();
		}		
			
	}
	/**
	 * 
	 */
	
	/**
	 * This is with GraphUnit
	 * http://graphaware.com/neo4j/2014/05/29/graph-unit-neo4j-unit-testing.html
	 */
	@org.junit.Test
	public void compareNeo4jDatabase() {
		//Open a Transaction 
		try(Transaction transaction=graphDb.beginTx()){
			String testLabel="TestNode";
			//Create a Node with Label in the neo4j Database
			graphDb.createNode(DynamicLabel.label(testLabel));
			transaction.success();			
		}		
		
		GraphUnit.assertSameGraph(graphDb,"create (n:TestNode) return n");
			
	}
	
	/**
	 * This is through AssertJ 
	 */
	@org.junit.Test
	public void compareProperties(){
		
		//Open a Transaction 
				try(Transaction transaction=graphDb.beginTx()){
					String testLabel="TestNode";
					String testKey="key";
					String testValue="value";
					//Create a Node with Label in the neo4j test Database
					Node node = graphDb.createNode(DynamicLabel.label(testLabel));
					node.setProperty(testKey, testValue);
					
					//Check the Assertion
					assertThat(node).hasLabel(DynamicLabel.label(testLabel)).hasProperty(testKey, testValue);
					transaction.success();		
					
					
				}
				
		
	}
		

}
