package com.lightning.interactor;

import android.app.Activity;

import com.lightning.interacted.OnMainInteractedListener;
import com.lightning.model.User;

public interface MainInteractor {
	void insert(User user, OnMainInteractedListener listener);
	void update(User user, String where, OnMainInteractedListener listener);
	void delete(String where, OnMainInteractedListener listener);
	void getUsers(OnMainInteractedListener listener);
	void getContacts(Activity activity, OnMainInteractedListener listener);
}