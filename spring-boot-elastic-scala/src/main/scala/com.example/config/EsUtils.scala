package com.example.config

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
	
	

}
