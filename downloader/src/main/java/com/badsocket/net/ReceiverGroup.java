package com.badsocket.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by skyrim on 2017/11/7.
 */
public class ReceiverGroup {

	private List<Receiver> receivers = new ArrayList<>();

	private ReceiverGroup parent;

	private boolean isRoot;


	public void addReceiver(Receiver req) {
		receivers.add(req);
	}


	public void addReceivers(Collection<? extends Receiver> rs) {
		receivers.addAll(rs);
	}


	public void addReceivers(Receiver[] rs) {
		for (int i = 0; i < rs.length; i++) {
			receivers.add(rs[i]);
		}
	}


	public void removeReceiver(Receiver req) {
		receivers.remove(req);
	}


	public void clear() {
		receivers.clear();
	}


	public Receiver[] getReceivers() {
		return receivers.toArray(new Receiver[0]);
	}


	public Receiver getReceiver(int idx) {
		return receivers.get(idx);
	}


	public ReceiverGroup getParent() {
		return parent;
	}


	public void receive(int idx) throws IOException {
		receivers.get(idx).receive();
	}


	public void stop(int idx) throws IOException {
		receivers.get(idx).stop();
	}


	public void stopAll() throws IOException {
		for (Receiver r : receivers) {
			if (r != null && !r.isStoped()) {
				r.stop();
			}
		}
	}


	public int activeCount() {
		int activeCount = receivers.size();
		for (Receiver r : receivers) {
			if (r != null && r.isStoped()) {
				--activeCount;
			}
		}
		return activeCount;
	}


	public long getTotalReceivedLength() {
		long length = 0;
		for (Receiver r : receivers) {
			if (r != null && r != null) {
				length += r.getReceivedLength();
			}
		}
		return length;
	}


	boolean isRoot() {
		return parent == null;
	}

}
