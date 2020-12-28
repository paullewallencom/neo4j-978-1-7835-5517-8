package neo4j.chapter_4;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.*;
import org.neo4j.graphdb.schema.*;

public class CreateOrUpdateSchemaConstraints {

	public static void main(String[] args) {

		CreateOrUpdateSchemaConstraints constraints = new CreateOrUpdateSchemaConstraints();
		constraints.createConstraint();
		constraints.listConstraints();

	}

	private void createConstraint() {
		// String DBLocation = "$Neo4J_HOME/data/graph.db";
		String DBLocation = "D:\\myWork\\Neo4J\\neo4j-community-2.1.5\\data\\graph.db";
		// Get Object of Graph Service
		GraphDatabaseService graphDb = new GraphDatabaseFactory()
		.newEmbeddedDatabase(DBLocation);
		// Open a Transaction
		try (Transaction tx = graphDb.beginTx()) {
			// Get Schema from Graph Database Service
			Schema schema = graphDb.schema();
			// Define Unique Constraint and Label and Property
			ConstraintCreator creator = schema.constraintFor(DynamicLabel.label("Artist")).assertPropertyIsUnique("Age");
			// Create the Constraint
			creator.create();
			tx.success();
			tx.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		graphDb.shutdown();

	}

	private void listConstraints() {
		// String DBLocation = "$Neo4J_HOME/data/graph.db";
		String DBLocation = "D:\\myWork\\Neo4J\\neo4j-community-2.1.5\\data\\graph.db";
		// Get Object of Graph Service
		GraphDatabaseService graphDb = new GraphDatabaseFactory()
		.newEmbeddedDatabase(DBLocation);
		// Open a Transaction
		try (Transaction tx = graphDb.beginTx()) {
			Schema schema = graphDb.schema();
			for (ConstraintDefinition def : schema.getConstraints(DynamicLabel.label("Artist"))) {
				System.out.println("Label = Artist," + "Type of Constraint = " + def.getConstraintType());
				System.out.print("ON Properties = ");
				for (String keys : def.getPropertyKeys()) {
					System.out.print(keys);
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
