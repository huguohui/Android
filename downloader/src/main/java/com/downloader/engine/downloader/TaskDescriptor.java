package com.downloader.engine.downloader;

/**
 * @since 2017/10/5.
 */
public abstract class TaskDescriptor extends Descriptor {

	protected String name;

	public String getName() {
		return name;
	}


	public Descriptor setName(String name) {
		this.name = name;
		return this;
	}
}
