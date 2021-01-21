package com.example
object MongodbUtils extends App {
	
	import com.mongodb.reactivestreams.client.MongoDatabase
	import org.mongodb.scala.MongoClient
	
	
	val client: MongoClient  = MongoClient("mongodb://192.169.4.3:19130")
	val  database:MongoDatabase =client.getDatabase("kgms_default_user_graph_174c4e9a4a8")
	database.getCollection(collectionName="basic_info")
	
}
