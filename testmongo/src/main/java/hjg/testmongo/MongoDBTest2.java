package hjg.testmongo;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class MongoDBTest2 {

	public static void main(String[] args) {

	}

	public void test0() throws UnknownHostException {
		// Old version, uses Mongo
		Mongo mongo = new Mongo("localhost", 27017);
	}

	public void test1() throws UnknownHostException {
		// Since 2.10.0, uses MongoClient
		MongoClient mongo = new MongoClient("localhost", 27017);
	}

//	If MongoDB in secure mode, authentication is required.
	public void test2() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("database name");
		boolean auth = db.authenticate("username", "password".toCharArray());
	}

}
