package com.lightning.interactor;

import android.app.Activity;

import com.lightning.finisher.OnMainFinishedListener;
import com.lightning.model.User;

public interface MainInteractor {
	void insert(User user, OnMainFinishedListener listener);
	void update(User user, String where, OnMainFinishedListener listener);
	void delete(String where, OnMainFinishedListener listener);
	void getUsers(OnMainFinishedListener listener);
	void getContacts(Activity activity, OnMainFinishedListener listener);
}