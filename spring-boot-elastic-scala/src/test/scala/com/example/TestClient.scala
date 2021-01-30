package com.example

/**
	* @program: spring-boot-learning->TestClient
	* @description: ${description}
	* @author: shizeying
	* @create: 2021-01-15 13:22
	* */
object TestClient extends App {
	
	import com.example.config.EsUtils
	import com.example.domain.RsEntityData
	import com.example.utils.{AttrAgg, AttrAggK}
	import com.google.common.collect.Lists
	import com.sksamuel.elastic4s.ElasticDsl._
	import com.sksamuel.elastic4s.JacksonSupport
	
	import java.util.Objects
	import scala.Console.err
	
	
	val uris = "http://192.169.4.245:9200,http://192.169.4.245:9201,http://192.169.4.245:9202"
	val username = ""
	val password = ""
	val client = EsUtils.getClient (uris, username, password)
	val responder = client.execute {
		import com.sksamuel.elastic4s.ElasticApi.search
		search ("common_relation_20200101").query ("data").highlighting (highlight ("*"))
			//search ("my_index2")
			.aggs {
				import com.sksamuel.elastic4s.requests.searches.aggs.SigTermsAggregation
				//termsAgg ("key", "name")
				//termsAgg ("my_time", "startTime")
				SigTermsAggregation ("keyabc").field ("endValueType").minDocCount (1)
				//DateHistogramAggregation.apply ("keyabc").field ("date").format ("yyyy-mm-dd hh:mm:ss").calendarInterval (DateHistogramInterval.Day)
				//	.format ("yyyy-mm-dd hh:mm:ss")
				
				
			}
	}.await
	val attrAggK = AttrAggK ("abc", "endValueType", null, "1", "1",
		Lists.newArrayList[String](), Lists.newArrayList[String](), "1", 1, "1", 1, Lists.newArrayList[String]())
	val a = AttrAgg (attrAggK, Lists.newArrayList (), "0")
	val total = responder.result.hits.total.value
	//println (total)
	//err.println(	responder
	//.result
	//.hits
	//.hits
	//.iterator
	//.map(_.highlight).mkString(","))
	responder
		.result
		.hits
		.hits
		.iterator
		.map (_.sourceAsMap)
		.foreach (println)
	
	
	responder
		.result
		.hits
		.hits
		.iterator
		.map (res => if (res.index.replaceAll ("\\d+", "").contains ("relation")) {
			import com.example.domain.RsRelationData
			println (res.index.replaceAll ("\\d+", ""))
			RsRelationData.unapply (res)
		} else
			RsEntityData.unapply (res)
		)
		.map (_.orNull)
		.filter (Objects.nonNull)
		.map (JacksonSupport.mapper.writeValueAsString)
		.foreach (err.println)
	
	responder
		.result
		.hits
		.hits
		.iterator
	
	
	client.close ()
	
	val string = "这是一个伸手不见五指的黑夜。"
	
	//val resultList = SegmentBs.newInstance.segmentMode (SegmentModes.search).segment (string)
	
	//SegmentBs.newInstance
	//	.segmentMode (SegmentModes.dict)
	//	.segment (string)
	//	.asScala
	//	.map (_.word)
	//.foreach (err.println)
	
	
}
