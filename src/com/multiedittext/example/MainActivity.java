package com.multiedittext.example;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.multiedittext.example.widget.MultiEditText;

public class MainActivity extends BaseActivity {

	private MultiEditText mMultiEditText1, mMultiEditText2;
	private Button mButton1, mButton2,mButton3,mButton4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initViews();
		addListenner();
	}

	private void initViews() {
		mMultiEditText1 = (MultiEditText) findViewById(R.id.edittext1);
		mMultiEditText2 = (MultiEditText) findViewById(R.id.edittext2);
		mButton1 = (Button) findViewById(R.id.button1);
		mButton2 = (Button) findViewById(R.id.button2);
		mButton3 = (Button) findViewById(R.id.button3);
		mButton4 = (Button) findViewById(R.id.button4);
	}

	private void addListenner() {
		mButton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), mMultiEditText1.getText().toString(), Toast.LENGTH_LONG).show();
			}
		});
		mButton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), mMultiEditText2.getText().toString(), Toast.LENGTH_LONG).show();
			}
		});
		mButton3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mMultiEditText1.isTextVisible()) {
					mMultiEditText1.setTextVisible(false);
				}else {
					mMultiEditText1.setTextVisible(true);
				}
			}
		});
		mButton4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mMultiEditText2.isTextVisible()) {
					mMultiEditText2.setTextVisible(false);
				}else {
					mMultiEditText2.setTextVisible(true);
				}
			}
		});
	}
}
