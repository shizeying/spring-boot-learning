package com.example.domain

/**
	* @program: spring-boot-learning->RsEntityData
	* @description: ${description}
	* @author: shizeying
	* @create: 2021-01-16 21:53
	* */
case class RsEntityData (override val id: String, override val index: String,
                         name: String, abs: String, origin: String, image: String,
                         meaningTag: String, identifyTag: String, lon: String,
                         lat: String,
                         gisAddress: String, startTime: String, endTime: String,
                         entityTag: String, entityType: String, attrMap: Map[String, Any]
                        ) extends Result

object RsEntityData {
	
	import com.sksamuel.elastic4s.requests.searches.SearchHit
	
	private def convertMap (m: SearchHit): Map[String, Any] =
		m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("attr"))
			.values.headOption
			.map (_.asInstanceOf [Map[String, AnyRef]])
			.orNull
			.map (res => Tuple2 (res._1, m.highlightFragments (name = "attrMap." + res._1).headOption.getOrElse (res._2)))
	
	def unapply (m: SearchHit): Option[RsEntityData] = try {
		val name = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("name"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		val abs = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("abs"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		
		
		val origin = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("origin"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		
		val image = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("image"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		
		val meaningTag = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("meaningTag"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		
		val identifyTag = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("identifyTag"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		
		val lon = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("lon"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		
		val lat = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("lat"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		
		val gisAddress = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("gisAdress"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		val startTime = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("startTime"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		
		val endTime = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("endTime"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		
		
		val entityTag = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("entityTag"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		val entityType = m.sourceAsMap
			.filterKeys (res => res.equalsIgnoreCase ("entityType"))
			.map (res => m.highlightFragments (res._1).headOption.getOrElse (res._2).asInstanceOf [String]).headOption.orNull
		
		
		val attrMap = convertMap (m)
		Some (RsEntityData (
			id = m.id,
			index = m.index.replaceAll ("\\d+", ""),
			name = name,
			abs = abs,
			origin = origin,
			image = image,
			meaningTag = meaningTag,
			identifyTag = identifyTag,
			lon = lon,
			lat = lat,
			gisAddress = gisAddress,
			startTime = startTime,
			endTime = endTime,
			entityTag = entityTag,
			entityType = entityType,
			attrMap = attrMap
		))
	} catch {
		case ex: Exception => None
	}
}