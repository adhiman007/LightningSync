package com.lightning.sync;

import java.util.ArrayList;
import java.util.List;

import com.lightning.sync.LightningGetRequest;
import com.lightning.sync.LightningPriodicSync;

public class PriodicSync extends LightningPriodicSync {
	private static PriodicSync	instance;

	public static PriodicSync getInstance() {
		if (instance == null)
			instance = new PriodicSync();
		return instance;
	}

	@Override
	public List<LightningGetRequest<?>> getDirtyList() {
		List<LightningGetRequest<?>> list = new ArrayList<LightningGetRequest<?>>();
		list.add(new ContactsRequest().setPriodic(true));
		return list;
	}
}