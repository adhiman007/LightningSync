package com.lightning.table;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import com.lightning.db.DatabaseHelper;
import com.lightning.model.Phone;
import com.lightning.table.LightningTable;

public class PhoneTable extends LightningTable<Phone> {

	public PhoneTable() {
		super(new Phone());
	}

	@Override
	protected SQLiteOpenHelper getDBHelper() {
		return new DatabaseHelper(getContext());
	}

	@Override
	protected Phone populate(Cursor cursor) {
		Phone phone = new Phone();
		phone.setId(cursor.getString(cursor.getColumnIndex("id")));
		phone.setHome(cursor.getString(cursor.getColumnIndex("home")));
		phone.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
		phone.setOffice(cursor.getString(cursor.getColumnIndex("office")));
		return phone;
	}
}