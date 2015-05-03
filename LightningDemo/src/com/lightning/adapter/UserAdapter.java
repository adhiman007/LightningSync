package com.lightning.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lightning.demo.R;
import com.lightning.model.User;

public class UserAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<User> users;
	
	public UserAdapter(Activity activity, List<User> users) {
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.users = users;
	}

	@Override
	public int getCount() {
		return users.size();
	}

	@Override
	public Object getItem(int position) {
		return users.get(position);
	}

	@Override
	public long getItemId(int position) {
		return users.get(position).hashCode();
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

		User user = users.get(position);
		holder.txtName.setText(user.getName());
		holder.txtEmail.setText(user.getEmail());
		holder.txtPhone.setVisibility(View.GONE);
		return convertView;
	}

	static class ViewHolder {
		TextView txtName;
		TextView txtEmail;
		TextView txtPhone;
	}
}
