package com.lightning.sync;

import java.util.List;

import org.json.JSONObject;

import com.lightning.model.Contact;
import com.lightning.table.ContactTable;
import com.lightning.table.LightningTable;

public class DummyDirtyRequest extends LightningJSONDirtySync<Contact> {

	public DummyDirtyRequest() {
		super(new ContactTable());
	}

	@Override
	protected String getServiceUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void populateParams(Contact model, JSONObject params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<Contact> getDirtyList(LightningTable<Contact> table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onResponse(String response) {
		// TODO Auto-generated method stub
		
	}
}