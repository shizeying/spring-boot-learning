package com.example.domain


/**
	* @program: spring-boot-learning->RsRelationData
	* @description: ${description}
	* @author: shizeying
	* @create: 2021-01-16 21:54
	* */
case class RsRelationData (override val id: String, override val index: String,
                           entityName: String, entityType: String,
                           relName: String,
                           valueName: String, valueType: String,
                           timeFrom: String, timeTo: String, edgeMap: Map[String, Any])
	extends Result

object RsRelationData {
	
	import com.sksamuel.elastic4s.requests.searches.SearchHit
	
	private def convertMap (m: SearchHit): Map[String, Any] = m.sourceAsMap
		.filterKeys (res => res.equalsIgnoreCase ("edgeMap"))
		.values.headOption
		.map (_.asInstanceOf [Map[String, AnyRef]])
		.orNull
		.map (res => Tuple2 (res._1, m.highlightFragments (name = "edgeMap." + res._1)
			.headOption.getOrElse (res._2)))
	
	def unapply (m: SearchHit): Option[RsRelationData] = try {
		
		val entityName = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("startEntityName"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		val entityType = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("startEntityType"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		val valueName = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("endValueName"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		val valueType = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("endValueType"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		val timeFrom = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("timeFrom"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		val timeTo = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("timeTo"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		val relName = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("relName"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		val edgeMap = convertMap (m)
		Some (RsRelationData (
			id = m.id,
			index = m.index.replaceAll ("\\d+", ""),
			entityName = entityName,
			entityType = entityType,
			valueName = valueName,
			valueType = valueType,
			timeFrom = timeFrom,
			timeTo = timeTo,
			relName = relName,
			edgeMap = edgeMap
		))
	} catch {
		case ex: Exception => None
	}
}