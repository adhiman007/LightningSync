package com.lightning.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lightning.demo.R;
import com.lightning.model.Contact;

public class ContactsAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Contact>	contacts;
	
	
	public ContactsAdapter(Activity activity, List<Contact> contacts) {
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.contacts = contacts;
	}

	@Override
	public int getCount() {
		return contacts.size();
	}

	@Override
	public Object getItem(int position) {
		return contacts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return contacts.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.list_contact, null);
			holder = new ViewHolder();
			holder.txtName = (TextView)convertView.findViewById(R.id.txt_name);
			holder.txtEmail = (TextView)convertView.findViewById(R.id.txt_email);
			holder.txtPhone = (TextView)convertView.findViewById(R.id.txt_phone);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Contact contact = contacts.get(position);
		
		holder.txtName.setText("Name: " + contact.getName());
		holder.txtEmail.setText("Email: " + contact.getEmail());
		holder.txtPhone.setText("Phone: " + contact.getPhone().getMobile());
		return convertView;
	}

	static class ViewHolder {
		TextView txtName;
		TextView txtEmail;
		TextView txtPhone;
	}
}
