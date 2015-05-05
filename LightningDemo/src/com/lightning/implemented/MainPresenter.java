package com.lightning.implemented;

import java.util.List;

import android.app.Activity;

import com.lightning.interacted.OnMainInteractedListener;
import com.lightning.interactor.MainInteractor;
import com.lightning.model.Contact;
import com.lightning.model.User;
import com.lightning.views.MainView;

public class MainPresenter implements com.lightning.presenter.MainPresenter, OnMainInteractedListener {
	private MainView view;
	private MainInteractor interactor;
	
	public MainPresenter(MainView view) {
		this.view = view;
		interactor = new com.lightning.implemented.MainInteractor();
	}

	@Override
	public void insertUser(User user) {
		interactor.insert(user, this);
	}
	
	@Override
	public void updateUser(User user, String where) {
		interactor.update(user, where, this);
	}
	
	@Override
	public void deleteUser(String where) {
		interactor.delete(where, this);
	}
	
	@Override
	public void getUser(User user) {
		view.clearErrors();
		view.setUser(user);
	}
	
	@Override
	public void getUsers() {
		view.showDialog();
		interactor.getUsers(this);
	}
	
	@Override
	public void getContacts(Activity activity) {
		view.showDialog();
		interactor.getContacts(activity, this);
	}

	@Override
	public void onNameError(int resId) {
		view.setNameError(resId);
	}

	@Override
	public void onEmailError(int resId) {
		view.setEmailError(resId);
	}
	
	@Override
	public void onWhereError(int resId) {
		view.setWhereError(resId);
	}
	
	@Override
	public void clearFields() {
		view.clearFields();
		view.clearErrors();
	}
	
	@Override
	public void hideDialog() {
		view.hideDialog();
	}

	@Override
	public void showMessage(String message) {
		view.showMessage(message);
	}

	@Override
	public void onUsersFetched(List<User> users) {
		view.hideDialog();
		view.setUsers(users);		
	}

	@Override
	public void onContactsFetched(List<Contact> contacts) {
		view.hideDialog();
		view.setContacts(contacts);
	}
}