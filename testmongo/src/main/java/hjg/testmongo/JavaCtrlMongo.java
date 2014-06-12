package hjg.testmongo;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

@SuppressWarnings("unused")
public class JavaCtrlMongo {

	void testConn() throws UnknownHostException{
		// Old version, uses Mongo
//		Mongo mongo = new Mongo("localhost", 27017);
	 
		// Since 2.10.0, uses MongoClient
		MongoClient mongo = new MongoClient( "localhost" , 27017 );
	}
	
	void testAuth() throws UnknownHostException{
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("database name");
		boolean auth = db.authenticate("username", "password".toCharArray());
	}
	
	
	void testDisplayDataBase() throws UnknownHostException{
		MongoClient mongoClient = new MongoClient();
		
		List<String> dbs = mongoClient.getDatabaseNames();
		for(String tmp : dbs){
			System.out.println(tmp);
		}
	}
	void testDisplayTable() throws UnknownHostException{
		MongoClient mongo = new MongoClient();
//		Get collection / table.
		DB db = mongo.getDB("testdb");
		DBCollection table = db.getCollection("user");
	}
	void testDisplayCollection() throws UnknownHostException{
		MongoClient mongo = new MongoClient();
		//	Display all collections from selected database.
		DB db = mongo.getDB("testdb");
		Set<String> tables = db.getCollectionNames();
		
		for(String coll : tables){
			System.out.println(coll);
		}
	}
	void testSave() throws UnknownHostException{
		MongoClient mongo = new MongoClient();
		DB db = mongo.getDB("testdb");
		
		DBCollection table = db.getCollection("user");
		BasicDBObject document = new BasicDBObject();
		document.put("name", "mkyong");
		document.put("age", 30);
		document.put("createdDate", new Date());
		table.insert(document);
	}
	void testUpdate() throws UnknownHostException{
		MongoClient mongo = new MongoClient();
		DB db = mongo.getDB("testdb");
		
		DBCollection table = db.getCollection("user");
		 
		BasicDBObject query = new BasicDBObject();
		query.put("name", "mkyong");
	 
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("name", "mkyong-updated");
	 
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);
	 
		table.update(query, updateObj);
	}
	void testFind() throws UnknownHostException{
		MongoClient mongo = new MongoClient();
		DB db = mongo.getDB("testdb");
		
		DBCollection table = db.getCollection("user");
		 
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", "mkyong");
	 
		DBCursor cursor = table.find(searchQuery);
	 
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}
	void testDelete() throws UnknownHostException{
		MongoClient mongo = new MongoClient();
		DB db = mongo.getDB("testdb");
		
		DBCollection table = db.getCollection("user");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", "mkyong");
		table.remove(searchQuery);
	}
		
}
