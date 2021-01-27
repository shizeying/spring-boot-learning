package com.example


import org.mongodb.scala._

object MongodbUtils {
	
	
	//scalastyle:off method.length
	
	/**
		* Run this main method to see the output of this quick example.
		*
		* @param args takes an optional single argument for the connection string
		*
		* @throws Throwable if an operation fails
		*/
	def main (args: Array[String]): Unit = {
		import scala.util.{Success, Failure}
		import scala.util.Try
		import scala.concurrent.ExecutionContext.Implicits.global
		val mongo = MongoClient ("")
		
		val loginfo = mongo.getDatabase ("").getCollection ("log_info").find ()
		
	val list=	loginfo.toFuture().onComplete{
		
		case Success(Document.fromSeq())=>
		case Failure(ex)=> None
	}
	
		if (loginfo.toFuture ().isCompleted) {
			import scala.Console.err
			loginfo
				.foreach (res =>
					err.println ("打印一下：" + res.toJson))
			
		}
		mongo.close ()
	}
}
