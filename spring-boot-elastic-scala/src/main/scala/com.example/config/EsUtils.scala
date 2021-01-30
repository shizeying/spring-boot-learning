package com.example.config

import com.sksamuel.elastic4s._
import com.sksamuel.elastic4s.ElasticDsl._
import com.example.domain.RsRelationData

import com.sksamuel.elastic4s.JacksonSupport
case class BuildPrefix (sortField: java.lang.String, sorts: java.lang.String,
                        pageSize: java.lang.Integer, pageNo: java.lang.Integer, isExplain: java.lang.Boolean)

object EsUtils {
	
	import com.example.domain.RsEntityData
	import com.sksamuel.elastic4s.requests.searches.{HighlightField, SearchRequest, SearchResponse}
	import com.sksamuel.elastic4s.requests.searches.aggs.AbstractAggregation
	import com.sksamuel.elastic4s.requests.searches.queries.BoolQuery
	
	import java.util.Objects
	import scala.Console.err
	
	
	def getClient (uris: String, username: String, password: String) = {
		import com.sksamuel.elastic4s.http.{JavaClient, NoOpRequestConfigCallback}
		import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback
		val properties = ElasticProperties (uris)
		val callback = new HttpClientConfigCallback {
			
			import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
			
			override def customizeHttpClient (httpClientBuilder: HttpAsyncClientBuilder): HttpAsyncClientBuilder = {
				import org.apache.http.auth.{AuthScope, UsernamePasswordCredentials}
				import org.apache.http.impl.client.BasicCredentialsProvider
				val creds = new BasicCredentialsProvider ()
				creds.setCredentials (AuthScope.ANY, new
						UsernamePasswordCredentials (username, password))
				httpClientBuilder.setDefaultCredentialsProvider (creds)
			}
		}
		ElasticClient (JavaClient (properties, requestConfigCallback
			= NoOpRequestConfigCallback, httpClientConfigCallback = callback))
	}
	
	
	def buildSearchRequest (indexes: java.util.List[String], highlightFields: java.util.List[HighlightField],
	                        aggregations: java.util.List[AbstractAggregation],
	                        coarseBoolQuery: BoolQuery, fineBoolQuery: BoolQuery,
	                        buildPrefix: BuildPrefix) = {
		
		
		val resWindow = 1000
		val offset = (buildPrefix.pageNo - 1) * buildPrefix.pageSize
		val size = buildPrefix.pageSize
		val sort = buildPrefix.sorts
		val sortField = buildPrefix.sortField
		import org.apache.commons.lang3.StringUtils
		
		import java.util.Objects
		import scala.collection.JavaConverters.iterableAsScalaIterableConverter
		var request: SearchRequest = search (indexes.asScala)
			.highlighting (highlightFields.asScala)
			.aggregations (aggregations.asScala)
			.size (size)
			.from (offset)
			.trackScores (enabled = true)
		if (buildPrefix.isExplain && buildPrefix.pageSize != 100) request = request.explain (enabled = true)
		if (StringUtils.isNotBlank (sort) && StringUtils.isNotBlank (sortField)) {
			request = sort match {
				case "0" => request.sortByFieldAsc (name = sortField)
				case "1" => request.sortByFieldDesc (name = sortField)
			}
		}
		if (Objects.nonNull (coarseBoolQuery)) request = request.query (q = coarseBoolQuery)
		
		if (Objects.nonNull (fineBoolQuery) && StringUtils.isNotBlank (buildPrefix.sorts)) {
			import com.sksamuel.elastic4s.requests.searches.Rescore
			val rescore = Rescore.apply (fineBoolQuery).window (size = resWindow)
			request = request.rescore (rescore)
		}
		request
	}
	
	def performSearch (request: SearchRequest, client: ElasticClient) = {
		val search = client.execute (request).await.result
		search
	}
	
	def convertRsData (searchResponse: SearchResponse) =
		searchResponse
			.hits
			.hits
			.iterator
			.map (res => if (res.index.replaceAll ("\\d+", "").contains ("relation")) {
				RsRelationData.unapply (res)
			} else
				RsEntityData.unapply (res)
			)
			.map (_.orNull)
			.filter (Objects.nonNull)
			.map (printJson)
			.foreach (err.println)
	
	
	def printJson (any: Any) = JacksonSupport.mapper.writerWithDefaultPrettyPrinter.writeValueAsString (any)
}
