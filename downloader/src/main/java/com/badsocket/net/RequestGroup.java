package com.badsocket.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by skyrim on 2017/11/7.
 */
public class RequestGroup {

	private List<Request> requests = new ArrayList<>();

	private List<Response> responses = new ArrayList<>();

	private RequestGroup parent;

	private boolean isRoot;

	public void addRequest(Request req) {
		requests.add(req);
	}

	public void addResponse(Response rep) {
		responses.add(rep);
	}

	public void addRequests(Collection<? extends Request> rs) {
		requests.addAll(rs);
	}

	public void addRequests(Request[] rs) {
		for (int i = 0; i < rs.length; i++) {
			requests.add(rs[i]);
		}
	}

	public void removeRequest(Request req) {
		requests.remove(req);
	}

	public Request[] getRequests() {
		return requests.toArray(new Request[0]);
	}

	public Request getRequest(int idx) {
		return requests.get(idx);
	}

	public RequestGroup getParent() {
		return parent;
	}

	public void openRequests() throws IOException {
		for (Request r : requests) {
			if (r != null && !r.connected()) {
				r.open();
			}
		}
	}

	public void sendRequests() throws IOException {
		for (Request r : requests) {
			if (r != null && !r.sent()) {
				r.send();
			}
		}
	}

	public Response[] getResponses() throws IOException {
		/*List<Response> list = new ArrayList<>();
		for (Request r : requests) {
			if (r != null && !r.closed()) {
				list.add(r.response());
			}
		}*/

//		return list.toArray(new Response[0]);
		return responses.toArray(new Response[0]);
	}

	public int activeCount() {
		int activeCount = requests.size();
		for (Request r : requests) {
			if (r != null && r.closed()) {
				--activeCount;
			}
		}
		return activeCount;
	}

	public void closeAll() throws IOException {
		for (Request r : requests) {
			if (r != null && !r.closed()) {
				r.close();
			}
		}
	}

	public void clear() {
		requests.clear();
	}

	boolean isRoot() {
		return parent == null;
	}

}
