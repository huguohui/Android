package com.badsocket.net.newidea.http;

import com.badsocket.net.newidea.Entity;
import com.badsocket.net.newidea.Request;
import com.badsocket.net.newidea.URI;

import java.util.HashMap;

public abstract class HttpRequest implements Request {

	private Entity entity;

	private HashMap<String, String> headerMap = new HashMap<>();

	public HttpRequest(URI uri) {

	}

	public void setHeader(String name, String val) {
		headerMap.put(name, val);
	}

	public String getHeader(String name) {
		return headerMap.get(name);
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
}
