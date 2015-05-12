package com.lightning.sync;

import android.app.Activity;

import com.lightning.model.Response;

public class ResponseRequest extends LightningRequest<Void, Response> {

	public ResponseRequest(Activity activity) {
		super(activity);
	}

	@Override
	public String getURL() {
		return RequestUrl.URL;
	}
	
	@Override
	public void onResponse(Response result) {
		// TODO Auto-generated method stub
		
	}
}