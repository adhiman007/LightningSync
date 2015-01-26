package com.lightning.sync;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.lightning.model.Contact;
import com.lightning.model.Phone;
import com.lightning.table.ContactTable;
import com.lightning.table.PhoneTable;

public class ContactsRequest extends LightningGetRequest<List<Contact>> {

	@Override
	protected String getServiceUrl() {
		return RequestUrl.URL;
	}

	@Override
	protected List<Contact> parseResponse(String result) {
		if(result == null)
			return null;
		try {
			JSONArray array = new JSONObject(result).getJSONArray("contacts");
			Gson gson = new Gson();
			List<Contact> contacts = Arrays.asList(gson.fromJson(array.toString(), Contact[].class));
			if(isPriodic()) {
				ContactTable contactTable = new ContactTable();
				PhoneTable phoneTable = new PhoneTable();
				contactTable.setDebug(true);
				phoneTable.setDebug(true);
				for (Contact contact : contacts) {
					contactTable.insert(contact);
					Phone phone = contact.getPhone();
					phone.setId(contact.getId());
					phoneTable.insert(phone);
				}
			}
			return contacts;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}