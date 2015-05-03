	package com.lightning.presenter;

import android.app.Activity;

import com.lightning.model.User;

public interface MainPresenter {
	void insertUser(User user);
	void updateUser(User user, String where);
	void deleteUser(String where);
	void getUsers();
	void getContacts(Activity activity);
}