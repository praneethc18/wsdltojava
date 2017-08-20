package com.wsdltoJava;

import java.net.URL;

import net.webservicex.WeatherForecast;

public class Test {

	public static void main(String[] args) {
		  URL url = WeatherForecast.class.getClassLoader().getResource("wsdl/WeatherForecast.wsdl");
		  System.out.println(url.toString());
//		  File file = new File (url.toURI());
		  
		  
	}

}
