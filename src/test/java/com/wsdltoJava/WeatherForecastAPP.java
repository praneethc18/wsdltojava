package com.wsdltoJava;

import org.apache.cxf.jaxws.JaxWsClientFactoryBean;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.lca.eas.ims.asg.ws.WeatherForecast;

public class WeatherForecastAPP {

	
	public static void main(String[] args) {

			String serviceUrl = "http://localhost:8080/weblog4jdemo/bookshelfservice";
	        			
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	        factory.setServiceClass(WeatherForecast.class);
	        factory.setAddress(serviceUrl);
	        
	        WeatherForecast clientService = (WeatherForecast) factory.create();

			System.out.println("Server said: " ); 

			clientService.getWeatherForecastHttpGet();
			clientService.getWeatherForecastSoap12(null);
			clientService.getWeatherForecastSoap(null);
			clientService.getWeatherForecastHttpGet(null);
			

	}
}
