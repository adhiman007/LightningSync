package com.lightning.table;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import com.lightning.db.DatabaseHelper;
import com.lightning.model.Contact;
import com.lightning.model.Phone;
import com.lightning.table.LightningTable;

public class ContactTable extends LightningTable<Contact> {

	public ContactTable() {
		super(new Contact());
	}

	@Override
	protected SQLiteOpenHelper getDBHelper() {
		return new DatabaseHelper(getContext());
	}
	
	@Override
	protected Contact populate(Cursor cursor) {
		Contact contact = new Contact();
		contact.setAddress(cursor.getString(cursor.getColumnIndex("address")));
		contact.setEmail(cursor.getString(cursor.getColumnIndex("email")));
		contact.setGender(cursor.getString(cursor.getColumnIndex("gender")));
		contact.setId(cursor.getString(cursor.getColumnIndex("id")));
		contact.setName(cursor.getString(cursor.getColumnIndex("name")));
		PhoneTable phoneTable = new PhoneTable();
		Phone phone = phoneTable.getFilterList("id = '" + contact.getId() + "'").get(0);
		contact.setPhone(phone);
		return contact;
	}
}