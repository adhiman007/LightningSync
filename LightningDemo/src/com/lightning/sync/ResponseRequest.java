package com.lightning.sync;

import java.util.List;

import com.lightning.model.Contact;
import com.lightning.model.Phone;
import com.lightning.model.Response;
import com.lightning.table.LightningTable;

public class ResponseRequest extends LightningGetRequest<Response> {

	public ResponseRequest() {
		super(Response.class);
	}

	@Override
	protected String getServiceUrl() {
		return RequestUrl.URL;
	}
	
	@Override
	protected void onResponse(Response response) {
		if(isPriodic()) {
			LightningTable<Contact> contactTable = new LightningTable<Contact>(Contact.class);
			LightningTable<Phone> phoneTable = new LightningTable<Phone>(Phone.class);
			List<Contact> contacts = response.getContacts();
			for(Contact contact : contacts) {
				contactTable.insert(contact);
				Phone phone = contact.getPhone();
				phone.setId(contact.getId());
				phoneTable.insert(phone);
			}
		}
	}
}