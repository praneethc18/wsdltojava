package com.wsdltoJava;

import java.net.URI;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.auth.HttpAuthSupplier;

public class ServiceConfigurator {

	private String basicAuthUser;
	private String basicAuthPassword;

	public void configure(Object service) {

		Client client = ClientProxy.getClient(service);

		HTTPConduit httpConduit = (HTTPConduit) client.getConduit();
		httpConduit.setAuthSupplier(new HttpAuthSupplier() {

			@Override
			public boolean requiresRequestCaching() {
				return false;
			}

			@Override
			public String getAuthorization(AuthorizationPolicy authPolicy, URI url, Message message,
					String fullHeader) {
				return null;
			}
		});

		client.getOutInterceptors().add(new LoggingOutInterceptor());
		client.getInInterceptors().add(new LoggingInInterceptor());
		client.getInFaultInterceptors().add(new LoggingInInterceptor());
	}

	public String getBasicAuthPassword() {
		return basicAuthPassword;
	}

	public void setBasicAuthPassword(String basicAuthPassword) {
		this.basicAuthPassword = basicAuthPassword;
	}

	public String getBasicAuthUser() {
		return basicAuthUser;
	}

	public void setBasicAuthUser(String basicAuthUser) {
		this.basicAuthUser = basicAuthUser;
	}
}