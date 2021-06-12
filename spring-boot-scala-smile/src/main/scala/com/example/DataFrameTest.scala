package com.example


import smile.read

import scala.language.postfixOps

/**
	* @program: spring-boot-learning->DataFrameTest
	* @description: ${description}
	* @author: shizeying
	* @create: 2021-02-06 11:42
	* */
object DataFrameTest extends App {
	
	import breeze.linalg._
	import breeze.plot._
	
	val f = Figure()
	val p = f.subplot(0)
	val x = linspace(0.0,1.0)
	p += plot(x, x ^:^ 2.0)
	p += plot(x, x ^:^ 3.0, '.')
	p.xlabel = "x axis"
	p.ylabel = "y axis"
	f.saveas("lines.png")

	
}
