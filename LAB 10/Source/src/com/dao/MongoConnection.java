package com.dao;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.operation.MapReduceWithInlineResultsOperation;

public class MongoConnection {
	public static void main(String[] args) {
		MongoConnection c = new MongoConnection();
		c.createConnnect();
	}
	public DB createConnnect()
	{
		
		MongoClientURI uri = new MongoClientURI("mongodb://root:123456@dbh35.mlab.com:27357/asedemo");
		MongoClient client = new MongoClient(uri);
		DB db = client.getDB(uri.getDatabase());
		if(db != null)
		{
			System.out.println("connection succeedd");
		}
		return db;
	}
}
