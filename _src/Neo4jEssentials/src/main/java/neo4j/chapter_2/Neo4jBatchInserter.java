package neo4j.chapter_2;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;

public class Neo4jBatchInserter {

	public void batchInsert() throws Exception {
		// Getting Object of BatchInserter.
		// Should be called only once for Batch of statements
		// ”/graph.db” should be replaced with the path of your Neo4j Database.
		// Neo4j Database should be located at following path
		// <$NEO4J_HOME>/data/graph.db
		BatchInserter inserter = BatchInserters.inserter("D:\\myWork\\Neo4J\\neo4j-community-2.1.5\\data\\graph.db");

		try {
			// Create a Label for a Node
			Label personLabel = DynamicLabel.label("Cinestars");

			// Enabling Index for a Label and Property
			inserter.createDeferredSchemaIndex(personLabel).on("name").create();
			// Creating Properties and the Node
			// ”<>” or generics are available with Java>=1.5
			// But it is recommended to use Java>=1.7 with Neo4j API’s
			Map<String, Object> properties = new HashMap<>();
			properties.put("Name", "Cate Blanchett");
			long cateNode = inserter.createNode(properties, personLabel);

			// Creating Properties and the Node
			properties.put("Name", "Russell Crowe ");
			long russellNode = inserter.createNode(properties, personLabel);

			// Creating Relationship
			RelationshipType knows = DynamicRelationshipType.withName("KNOWS");

			// To set properties on the relationship, use a properties map
			// instead of null as the last parameter.
			inserter.createRelationship(cateNode, russellNode, knows, null);
		} catch (Exception e) {
			// Print Exception on Console and shutdown the Inserter
			e.printStackTrace();
			// Shutdown the Inserter, so that your database is not corrupted.
			inserter.shutdown();
		}
		// Should be called only once for Batch of statements
		inserter.shutdown();
	}

	public static void main(String[] args) {
		try {
			new Neo4jBatchInserter().batchInsert();
		} catch (Exception e) {
			// Print all and any kind of Exception on Console.
			e.printStackTrace();
		}

	}

}
