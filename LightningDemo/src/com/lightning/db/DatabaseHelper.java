package com.lightning.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lightning.LightningHelper;
import com.lightning.model.Contact;
import com.lightning.model.Phone;
import com.lightning.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "lightning.db";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		LightningHelper.createTable(db, new Contact());
		LightningHelper.createTable(db, new Phone());
		LightningHelper.createTable(db, new User());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}