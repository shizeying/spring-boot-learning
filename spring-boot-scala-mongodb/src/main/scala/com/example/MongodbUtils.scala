package com.example

object MongodbUtils extends App {
	
	import org.mongodb.scala.{MongoClient, MongoDatabase}

	import java.lang.Thread.sleep
	import scala.concurrent.ExecutionContext.Implicits.global
	
	
	val client: MongoClient = MongoClient ("mongodb://192.169.4.3:19130")
	val database: MongoDatabase = client.getDatabase ("kgms_default_user_graph_174c4e9a4a8")
	val future = database.getCollection (collectionName = "basic_info").find ().toFuture ()
	future.onComplete(_.ma)
	if (future.isCompleted){
		future.value.get.get.map (_.toJson).foreach (println)
	} 
	
	Thread.sleep(50000)
	
	
}
