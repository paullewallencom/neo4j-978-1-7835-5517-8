package neo4j.chapter_4;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.*;
import org.neo4j.graphdb.schema.*;

public class CreateOrUpdateSchema {

	public static void main(String[] args) {
		CreateOrUpdateSchema schema = new CreateOrUpdateSchema();
		schema.createSchema();
		schema.fetchSchema();
		schema.deleteSchema();
	}

	private void createSchema() {
		// String DBLocation = "$Neo4J_HOME/data/graph.db";
		String DBLocation = "D:\\myWork\\Neo4J\\neo4j-community-2.1.5\\data\\graph.db";
		GraphDatabaseService graphDb = new GraphDatabaseFactory()
		.newEmbeddedDatabase(DBLocation);
		// Start the Transaction
		// try-with-resources is available only with Java 1.7 and above.
		try (Transaction tx = graphDb.beginTx()) {
			// Get the Schema from the Graph DB Service
			Schema schema = graphDb.schema();
			// Create Index on the provided Label and property
			IndexDefinition indexDefinition = schema.indexFor(DynamicLabel.label("Movie")).on("Title").create();
			tx.success();
			tx.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		graphDb.shutdown();

	}

	private void fetchSchema() {
		// String DBLocation = "$Neo4J_HOME/data/graph.db";
		String DBLocation = "D:\\myWork\\Neo4J\\neo4j-community-2.1.5\\data\\graph.db";
		GraphDatabaseService graphDb = new GraphDatabaseFactory()
		.newEmbeddedDatabase(DBLocation);

		// Start the Transaction
		// try-with-resources is available only with Java 1.7 and above.
		try (Transaction tx = graphDb.beginTx()) {
			// Get the Schema from the Graph DB Service
			Schema schema = graphDb.schema();
			// Iterate through all Indexes and Print the Labels and Properties
			for (IndexDefinition inDef : schema.getIndexes()) {
				System.out.println("Label = " + inDef.getLabel().name() + ", Property Keys = " + inDef.getPropertyKeys());
			}
			tx.success();
			tx.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		graphDb.shutdown();

	}

	private void deleteSchema() {
		// String DBLocation = "$Neo4J_HOME/data/graph.db";
		String DBLocation = "D:\\myWork\\Neo4J\\neo4j-community-2.1.5\\data\\graph.db";
		GraphDatabaseService graphDb = new GraphDatabaseFactory()
		.newEmbeddedDatabase(DBLocation);

		// Start the Transaction
		// try-with-resources is available only with Java 1.7 and above.
		try (Transaction tx = graphDb.beginTx()) {
			// Get the Schema from the Graph DB Service
			Schema schema = graphDb.schema();
			// Iterate through all Indexes on a Label Movies and delete them
			for (IndexDefinition inDef : schema.getIndexes(DynamicLabel.label("Movie"))) {
				inDef.drop();

			}
			tx.success();
			tx.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		graphDb.shutdown();

	}
}
