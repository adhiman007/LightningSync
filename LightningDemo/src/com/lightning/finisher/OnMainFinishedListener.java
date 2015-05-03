package com.lightning.finisher;

import java.util.List;

import com.lightning.model.Contact;
import com.lightning.model.User;

public interface OnMainFinishedListener {
	void onNameError(int resId);
	void onEmailError(int resId);
	void onWhereError(int resId);
	void showMessage(String message);
	void onUsersFetched(List<User> users);
	void onContactsFetched(List<Contact> contacts);
}
