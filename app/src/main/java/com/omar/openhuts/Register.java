package com.omar.openhuts;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Register extends DefaultActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		Spanned policy = Html.fromHtml(getString(R.string.tac));
		TextView termsOfUse = findViewById(R.id.tac);
		termsOfUse.setText(policy);
		termsOfUse.setMovementMethod(LinkMovementMethod.getInstance());
	}

	public void register(View v) {
		Log.d("click", "clicked on send register");
	}
}
