package com.lightning.implemented;

import java.util.List;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Patterns;

import com.lightning.demo.R;
import com.lightning.engine.CallBack;
import com.lightning.engine.NoInternetException;
import com.lightning.interacted.OnMainInteractedListener;
import com.lightning.model.Contact;
import com.lightning.model.Phone;
import com.lightning.model.Response;
import com.lightning.model.User;
import com.lightning.sync.ResponseRequest;
import com.lightning.table.LightningTable;

public class MainInteractor implements com.lightning.interactor.MainInteractor {
	private LightningTable<User> userTable;
	private LightningTable<Contact> contactTable;
	private LightningTable<Phone> phoneTable;
	
	public MainInteractor() {
		userTable = new LightningTable<User>(User.class);
		contactTable = new LightningTable<Contact>(Contact.class);
		phoneTable = new LightningTable<Phone>(Phone.class);
		userTable.setDebug(true);
		contactTable.setDebug(true);
		phoneTable.setDebug(true);
	}

	@Override
	public void insert(User user, OnMainInteractedListener listener) {
		if(checkUser(user, listener))
			return;
		userTable.insert(user);
		listener.clearFields();
		listener.showMessage("User inserted");
	}

	@Override
	public void update(User user, String where, OnMainInteractedListener listener) {
		if(TextUtils.isEmpty(where)) {
			listener.onWhereError(R.string.enter_where);
			getUsers(listener);
			return;
		}
		userTable.update(user, where);
		listener.clearFields();
		listener.showMessage("User updated");
	}

	@Override
	public void delete(String where, OnMainInteractedListener listener) {
		if(TextUtils.isEmpty(where)) {
			listener.onWhereError(R.string.enter_where);
			getUsers(listener);
			return;
		}
		userTable.delete(where);
		listener.clearFields();
		listener.showMessage("User deleted");
	}

	@Override
	public void getUsers(OnMainInteractedListener listener) {
		listener.onUsersFetched(userTable.getList());
	}
	
	@Override
	public void getContacts(Activity activity, final OnMainInteractedListener listener) {
		new ResponseRequest(activity).setType(ResponseRequest.GET).setDebug(true).setCallback(
				new CallBack<Response>() {

					@Override
					public void onResponse(Response result, Exception e) {
						if (result == null) {
							listener.hideDialog();
							if(e instanceof NoInternetException)
								listener.showMessage("No Internet Connection");
							return;
						}
						contactTable.clearTable();
						phoneTable.clearTable();
						List<Contact> contacts = result.getContacts();
						for (Contact contact : contacts) {
							contactTable.insert(contact);
							Phone phone = contact.getPhone();
							phone.setId(contact.getId());
							phoneTable.insert(phone);
						}
						List<Contact> list = contactTable.getList();
						for (Contact contact : list) {
							Phone phone = phoneTable.getList("id = '" + contact.getId() + "'").get(0);
							contact.setPhone(phone);
						}
						listener.onContactsFetched(list);
					}
				}).hit(null);
	}
	
	private boolean checkUser(User user, OnMainInteractedListener listener) {
		if(TextUtils.isEmpty(user.getName())) {
			listener.onNameError(R.string.enter_name);
			return true;
		}
		if(TextUtils.isEmpty(user.getEmail())) {
			listener.onEmailError(R.string.enter_email);
			return true;
		}
		if(!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
			listener.onEmailError(R.string.valid_email);
			return true;
		}
		if(userTable.exists("name = '" + user.getName() + "' AND email = '" + user.getEmail() + "'")) {
			listener.clearFields();
			listener.showMessage("User already exists");
			return true;
		}
		return false;
	}
}