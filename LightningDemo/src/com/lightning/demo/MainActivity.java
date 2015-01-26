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
import com.lightning.engine.PriodicCallback;
import com.lightning.engine.RequestCallback;
import com.lightning.model.Contact;
import com.lightning.model.Phone;
import com.lightning.model.Response;
import com.lightning.sync.ContactsRequest;
import com.lightning.sync.PriodicSync;
import com.lightning.sync.ResponseRequest;
import com.lightning.table.ContactTable;
import com.lightning.table.PhoneTable;

public class MainActivity extends Activity implements PriodicCallback,
		OnClickListener, OnCheckedChangeListener {
	private ListView	listContacts;
	private PriodicSync	priodicSync;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LightningHelper.init(getApplicationContext());

		initUI();

		priodicSync = PriodicSync.getInstance();
		priodicSync.setCallback(this);
	}

	private void initUI() {
		listContacts = (ListView)findViewById(R.id.list_contacts);
		findViewById(R.id.btn_request1).setOnClickListener(this);
		findViewById(R.id.btn_request2).setOnClickListener(this);
		((ToggleButton) findViewById(R.id.tbtn_sync))
				.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_request1:
			requestType1();
			break;
		case R.id.btn_request2:
			requestType2();
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
				ContactTable contactParser = new ContactTable();
				List<Contact> contacts = contactParser.getList();
				listContacts.setAdapter(new ContactsAdapter(MainActivity.this, contacts));
				showToast("Total: "+contacts.size());
			}
		});
	}

	private void requestType1() {
		new ContactsRequest().setCallback(new RequestCallback<List<Contact>>() {

			@Override
			public void onResponse(List<Contact> contacts) {
				if (contacts == null)
					return;
				ContactTable contactTable = new ContactTable();
				PhoneTable phoneTable = new PhoneTable();
				contactTable.setDebug(true);
				phoneTable.setDebug(true);
				contactTable.clearTable();
				phoneTable.clearTable();
				for (Contact contact : contacts) {
					contactTable.insert(contact);
					Phone phone = contact.getPhone();
					phone.setId(contact.getId());
					phoneTable.insert(phone);
				}
				listContacts.setAdapter(new ContactsAdapter(MainActivity.this, contactTable.getList()));
			}

			@Override
			public void onError(Exception e) {
				showToast(e.getMessage());
			}
		}).get(getApplicationContext());
	}

	private void requestType2() {
		new ResponseRequest().setCallback(new RequestCallback<Response>() {

			@Override
			public void onResponse(Response response) {
				if (response == null)
					return;
				ContactTable contactTable = new ContactTable();
				PhoneTable phoneTable = new PhoneTable();
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
				listContacts.setAdapter(new ContactsAdapter(MainActivity.this, contactTable.getList()));
			}

			@Override
			public void onError(Exception e) {
				showToast(e.getMessage());
			}
		}).get(getApplicationContext());
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