package com.lightning.demo;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.lightning.LightningHelper;
import com.lightning.adapter.ContactsAdapter;
import com.lightning.adapter.UserAdapter;
import com.lightning.db.DatabaseHelper;
import com.lightning.model.Contact;
import com.lightning.model.Phone;
import com.lightning.model.User;
import com.lightning.presenter.MainPresenter;
import com.lightning.table.LightningTable;
import com.lightning.view.MainView;

public class MainActivity extends Activity implements OnClickListener, MainView {
	private EditText editName;
	private EditText editEmail;
	private EditText editWhere;
	private ListView list;
	private LightningTable<Contact> contactTable;
	private LightningTable<Phone> phoneTable;
	private MainPresenter presenter;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LightningHelper.init(getApplicationContext(), new DatabaseHelper(getApplicationContext()));

		editName = (EditText)findViewById(R.id.edit_name);
		editEmail = (EditText)findViewById(R.id.edit_email);
		editWhere = (EditText)findViewById(R.id.edit_where);
		list = (ListView) findViewById(R.id.list);
		
		findViewById(R.id.btn_insert).setOnClickListener(this);
		findViewById(R.id.btn_update).setOnClickListener(this);
		findViewById(R.id.btn_delete).setOnClickListener(this);
		findViewById(R.id.btn_fetch).setOnClickListener(this);
		findViewById(R.id.btn_request).setOnClickListener(this);
		
		contactTable = new LightningTable<Contact>(Contact.class);
		phoneTable = new LightningTable<Phone>(Phone.class);
		contactTable.setDebug(true);
		phoneTable.setDebug(true);
		dialog = new ProgressDialog(this);
		dialog.setMessage("Please Wait...");
		presenter = new com.lightning.implement.MainPresenter(this);
	}

	@Override
	public void onClick(View v) {
		User user = new User();
		switch (v.getId()) {
		case R.id.btn_insert:
			user.setName(editName.getText().toString());
			user.setEmail(editEmail.getText().toString());
			presenter.insertUser(user);
			break;
		case R.id.btn_update:
			user.setName(editName.getText().toString());
			user.setEmail(editEmail.getText().toString());
			presenter.updateUser(user, editWhere.getText().toString());
			break;
		case R.id.btn_delete:
			presenter.deleteUser(editWhere.getText().toString());
			break;
		case R.id.btn_fetch:
			presenter.getUsers();
			break;
		case R.id.btn_request:
			presenter.getContacts(this);
			break;
		}
	}
	
	@Override
	public void showDialog() {
		dialog.show();
	}
	
	@Override
	public void hideDialog() {
		dialog.dismiss();
	}
	
	@Override
	public void setNameError(int resId) {
		editName.requestFocus();
		editName.setError(getResources().getString(resId));
	}

	@Override
	public void setEmailError(int resId) {
		editEmail.requestFocus();
		editEmail.setError(getResources().getString(resId));
	}
	
	@Override
	public void setWhereError(int resId) {
		editWhere.requestFocus();
		editWhere.setError(getResources().getString(resId));
	}

	@Override
	public void showMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void setUsers(List<User> users) {
		list.setAdapter(new UserAdapter(this, users));
	}
	
	@Override
	public void setContacts(List<Contact> contacts) {
		list.setAdapter(new ContactsAdapter(this, contacts));
	}
	
	@Override
	protected void onDestroy() {
		LightningHelper.closeHelper();
		super.onDestroy();
	}
}