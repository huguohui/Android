package com.downloader.client.downloader;

/**
 * Abstract descriptor for some thing.
 */
public abstract class AbstractDescriptor {
	protected String name;


	public String getName() {
		return name;
	}


	public AbstractDescriptor setName(String name) {
		this.name = name;
		return this;
	}
}
