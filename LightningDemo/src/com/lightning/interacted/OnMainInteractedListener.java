package com.lightning.interacted;

import java.util.List;

import com.lightning.model.Contact;
import com.lightning.model.User;

public interface OnMainInteractedListener {
	void onNameError(int resId);
	void onEmailError(int resId);
	void onWhereError(int resId);
	void clearFields();
	void hideDialog();
	void showMessage(String message);
	void onUsersFetched(List<User> users);
	void onContactsFetched(List<Contact> contacts);
}