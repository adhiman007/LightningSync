package com.lightning.sync;

import java.util.ArrayList;
import java.util.List;

import com.lightning.sync.LightningDirtySync;
import com.lightning.sync.LightningJSONDirtySync;
import com.lightning.sync.LightningNVDirtySync;

public class DirtySync extends LightningDirtySync {
	private static DirtySync	instance;

	public static DirtySync getInstance() {
		if (instance == null)
			instance = new DirtySync();
		return instance;
	}

	@Override
	public List<LightningJSONDirtySync<?>> getJSONDirtyList() {
		List<LightningJSONDirtySync<?>> list = new ArrayList<LightningJSONDirtySync<?>>();
		list.add(new DummyDirtyRequest().setPriodic(true));
		return list;
	}

	@Override
	public List<LightningNVDirtySync<?>> getNVDirtyList() {
		// TODO Auto-generated method stub
		return null;
	}
}