package com.example.config
case class HighlightEntity (column: String, fragmentSize: Integer)

object EsUtils {
	
	import com.sksamuel.elastic4s.ElasticClient
	
	def getClient (username: String, password: String, uris: String) = {
		import com.sksamuel.elastic4s.{ElasticClient, ElasticProperties}
		import com.sksamuel.elastic4s.http.{JavaClient, NoOpRequestConfigCallback}
		import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback
		val callback = new HttpClientConfigCallback {
			
			import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
			
			override def customizeHttpClient (httpClientBuilder: HttpAsyncClientBuilder): HttpAsyncClientBuilder = {
				import org.apache.http.auth.{AuthScope, UsernamePasswordCredentials}
				import org.apache.http.impl.client.BasicCredentialsProvider
				val creds = new BasicCredentialsProvider ()
				creds.setCredentials (AuthScope.ANY, new UsernamePasswordCredentials (username, password))
				httpClientBuilder.setDefaultCredentialsProvider (creds)
			}
		}
		val props = ElasticProperties (uris)
		
		ElasticClient (JavaClient (props, requestConfigCallback = NoOpRequestConfigCallback, httpClientConfigCallback = callback))
	}
	
	def builderMulti (client: ElasticClient) = {
	
	
	}
	
	
	def builderHighlight (highlights: java.util.List[HighlightEntity]) = {
		import com.sksamuel.elastic4s.ElasticApi.highlight
		import scala.collection.JavaConverters.asScalaBufferConverter
		highlights.asScala
			.map (
				rest => {
					import java.util.Objects
					val hig = highlight (rest.column)
					if (Objects.nonNull (rest.fragmentSize)) {
						hig.fragmentOffset (rest.fragmentSize)
					}
					hig
				}
			)
		
	}
	
}
