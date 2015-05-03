package com.lightning.view;

import java.util.List;

import com.lightning.model.Contact;
import com.lightning.model.User;

public interface MainView {
	void showDialog();
	void hideDialog();
	void setNameError(int resId);
	void setEmailError(int resId);
	void setWhereError(int resId);
	void showMessage(String message);
	void setUsers(List<User> users);
	void setContacts(List<Contact> contacts);
}