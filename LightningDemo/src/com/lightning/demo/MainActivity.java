package com.lightning.demo;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.lightning.LightningHelper;
import com.lightning.adapter.ContactsAdapter;
import com.lightning.db.DatabaseHelper;
import com.lightning.engine.PriodicCallback;
import com.lightning.engine.RequestCallback;
import com.lightning.model.Contact;
import com.lightning.model.Phone;
import com.lightning.model.Response;
import com.lightning.sync.PriodicSync;
import com.lightning.sync.ResponseRequest;
import com.lightning.table.LightningTable;

public class MainActivity extends Activity implements PriodicCallback,
		OnClickListener, OnCheckedChangeListener {
	private ListView	listContacts;
	private PriodicSync	priodicSync;
	LightningTable<Contact> contactTable;
	LightningTable<Phone>   phoneTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LightningHelper.init(getApplicationContext(), new DatabaseHelper(getApplicationContext()));

		initUI();

		priodicSync = PriodicSync.getInstance();
		priodicSync.setCallback(this);
		
		contactTable = new LightningTable<Contact>(Contact.class);
		phoneTable = new LightningTable<Phone>(Phone.class);      
	}

	private void initUI() {
		listContacts = (ListView)findViewById(R.id.list_contacts);
		findViewById(R.id.btn_request).setOnClickListener(this);
		((ToggleButton) findViewById(R.id.tbtn_sync))
				.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_request:
			requestType();
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.tbtn_sync:
			if (isChecked) {
				priodicSync.startSync(this);
				// DirtySync.getInstance().startSync(this);
			} else {
				priodicSync.stopSync(this);
				// DirtySync.getInstance().stopSync(this);
			}
			break;
		}
	}

	@Override
	public void onPriodicSyncStart() {
		showToast("Sync Started");
	}

	@Override
	public void onPriodicSyncDone() {
		showToast("Sync Stopped");
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				List<Contact> contacts = contactTable.getList();
				for(Contact contact : contacts) {
					Phone phone = phoneTable.getList("id = '"+contact.getId()+"'").get(0);
					contact.setPhone(phone);
				}
				listContacts.setAdapter(new ContactsAdapter(MainActivity.this, contacts));
				showToast("Total: "+contacts.size());
			}
		});
	}

	private void requestType() {
		new ResponseRequest().setCallback(new RequestCallback<Response>() {

			@Override
			public void onResponse(Response response, Exception e) {
				if (response == null) {
					showToast(e.getMessage());
					return;
				}
				contactTable.setDebug(true);
				phoneTable.setDebug(true);
				contactTable.clearTable();
				phoneTable.clearTable();
				List<Contact> contacts = response.getContacts();
				for (Contact contact : contacts) {
					contactTable.insert(contact);
					Phone phone = contact.getPhone();
					phone.setId(contact.getId());
					phoneTable.insert(phone);
				}
				List<Contact> list = contactTable.getList();
				for(Contact contact : list) {
					Phone phone = phoneTable.getList("id = '"+contact.getId()+"'").get(0);
					contact.setPhone(phone);
				}
				listContacts.setAdapter(new ContactsAdapter(MainActivity.this, list));				
			}
		}).get(this);
	}
	
	private void showToast(final String message) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT).show();
			}
		});
	}	
}