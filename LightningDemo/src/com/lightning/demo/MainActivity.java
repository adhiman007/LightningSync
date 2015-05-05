package com.lightning.demo;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.lightning.LightningHelper;
import com.lightning.adapters.ContactsAdapter;
import com.lightning.adapters.UserAdapter;
import com.lightning.db.DatabaseHelper;
import com.lightning.model.Contact;
import com.lightning.model.User;
import com.lightning.presenter.MainPresenter;
import com.lightning.views.MainView;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener, MainView {
	private EditText editName;
	private EditText editEmail;
	private EditText editWhere;
	private ListView list;
	private MainPresenter presenter;
	private ProgressDialog dialog;
	private InputMethodManager manager;

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
		findViewById(R.id.btn_clear).setOnClickListener(this);
		findViewById(R.id.btn_fetch).setOnClickListener(this);
		findViewById(R.id.btn_request).setOnClickListener(this);
		list.setOnItemClickListener(this);
		
		manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		
		dialog = new ProgressDialog(this);
		dialog.setMessage("Please Wait...");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		presenter = new com.lightning.implemented.MainPresenter(this);
	}
	
	@Override
	protected void onPause() {
		presenter = null;
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
		case R.id.btn_clear:
			presenter.clearFields();
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		presenter.getUser((User) ((list.getAdapter() instanceof UserAdapter) ? ((UserAdapter)list.getAdapter()).getItem(arg2) : null));
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
	public void setUser(User user) {
		if(user == null)
			return;
		editName.setText(user.getName());
		editEmail.setText(user.getEmail());
		editWhere.setText("userId = "+user.getUserId());
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
	public void clearFields() {
		editName.setText("");
		editEmail.setText("");
		editWhere.setText("");
	}
	
	@Override
	public void clearErrors() {
		editName.setError(null);
		editEmail.setError(null);
		editWhere.setError(null);		
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