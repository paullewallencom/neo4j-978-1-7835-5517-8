package neo4j.chapter_2;

import java.util.Map;

import org.neo4j.helpers.collection.MapUtil;
import org.neo4j.index.lucene.unsafe.batchinsert.LuceneBatchInserterIndexProvider;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserterIndex;
import org.neo4j.unsafe.batchinsert.BatchInserterIndexProvider;
import org.neo4j.unsafe.batchinsert.BatchInserters;

public class Neo4jBatchIndexer {

	public void batchIndexer() throws Exception {
		// ”/graph.db” should be replaced with the path of your Neo4j Database.
		// Neo4j Database should be located at following path
		// <$NEO4J_HOME>/data/graph.db
		BatchInserter inserter = BatchInserters.inserter("D:\\myWork\\Neo4J\\neo4j-community-2.1.5\\data\\graph.db");

		// Creating Object of Index Providers
		BatchInserterIndexProvider indexProvider = new LuceneBatchInserterIndexProvider(inserter);
		try {
			// Getting reference of Index
			BatchInserterIndex actors = indexProvider.nodeIndex("cinestars", MapUtil.stringMap("type", "exact"));
			// Enabling Caching on Nodes
			actors.setCacheCapacity("name", 100000);
			// Creating Node and setting Properties
			// ”<>” or generics are available with Java>=1.5
			// But it is recommended to use Java>=1.7 with Neo4j API’s
			Map<String, Object> properties = MapUtil.map("name", "Keanu Reeves");
			long node = inserter.createNode(properties);
			// Adding Node to Index
			actors.add(node, properties);
			// make the changes visible for reading, use Carefully, requires IO!
			actors.flush();
			// Shut down the index provider and inserter
		} catch (Exception e) {
			// Print Exception on Console and shutdown the Inserter and Indexer
			e.printStackTrace();
			// Shutdown the Index and Inserter, so that your database is not
			// corrupted.
			indexProvider.shutdown();
			inserter.shutdown();
		}
		indexProvider.shutdown();
		inserter.shutdown();
	}

	public static void main(String[] args) {
		try {
			new Neo4jBatchIndexer().batchIndexer();
		} catch (Exception e) {
			// Print all and any kind of Exception on Console
			e.printStackTrace();

		}

	}
}
