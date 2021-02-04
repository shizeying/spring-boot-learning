package com.example

object MongodbUtils extends App {
	
	import org.mongodb.scala.{MongoClient, MongoDatabase, ToSingleObservablePublisher}
	
	
	val client: MongoClient = MongoClient ("mongodb://192.169.4.3:19130")
	val database: MongoDatabase = client.getDatabase ("kgms_default_user_graph_174c4e9a4a8")
	val map = database.getCollection (collectionName = "basic_info")
		.find ().map (_.toMap)
		.toObservable ().publisher.toString
}
