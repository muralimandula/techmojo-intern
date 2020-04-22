package io.javabrains.moviecatalogservice;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("attributes")  // Captures Set of fields with prefix "attributes" in configuration file (application.properties)
public class AttributesProperties {
	private String profile;				// field names must be matching
	private String user;
	private int port;

	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	
}
