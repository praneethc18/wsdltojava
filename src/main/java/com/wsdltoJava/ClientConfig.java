package com.wsdltoJava;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;

import com.lca.eas.ims.asg.ws.WeatherForecast;

@Configuration
public class ClientConfig {

//	  String serviceUrl = "http://localhost:8080/weblog4jdemo/bookshelfservice";
	@Value("${client.weatherForecast.address}")
	private String address;

	@Value("${client.weatherForecast.user-name}")
	private String userName;

	@Value("${client.weatherForecast.password}")
	private String password;

	@Bean(name = "weatherForecastClient")
	public WeatherForecast weatherForecastProxy() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(WeatherForecast.class);
		factory.setAddress(address);

		// add an interceptor to log the outgoing request messages
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		// add an interceptor to log the incoming response messages
		factory.getInInterceptors().add(new LoggingInInterceptor());
		// add an interceptor to log the incoming fault messages
		factory.getInFaultInterceptors().add(new LoggingInInterceptor());

		WeatherForecast clientService = (WeatherForecast) factory.create();
		return clientService;
	}

	@Bean
	public Client weatherForecastClientProxy() {
		return ClientProxy.getClient(weatherForecastProxy());
	}

	@Bean
	public HTTPConduit conduit() {
		HTTPConduit httpConduit = (HTTPConduit) weatherForecastClientProxy().getConduit();
		httpConduit.setAuthorization(basicAuthorization());
		
		
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		 
		httpClientPolicy.setConnectionTimeout(36000);
//		httpClientPolicy.setAllowChunking(false);
		httpClientPolicy.setReceiveTimeout(32000);
		 
		httpConduit.setClient(httpClientPolicy);
		
		return httpConduit;
	}

	@Bean
	public AuthorizationPolicy basicAuthorization() {
		AuthorizationPolicy authorizationPolicy = new AuthorizationPolicy();
		authorizationPolicy.setUserName(userName);
		authorizationPolicy.setPassword(password);
		authorizationPolicy.setAuthorizationType("Basic");
		return authorizationPolicy;
	}
}