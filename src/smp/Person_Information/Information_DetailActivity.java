package smp.Person_Information;




import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class Information_DetailActivity extends Activity{
	
	
	private EditText mNameText;
	private EditText mEmail_AddressText;
	private EditText mPhone_NumberText;
	private EditText mAddressText;
	private Long mRowId;
	private Information_DbAdapter mDbHelper;
	private Spinner mCategory;
	
	protected void onCreate(Bundle bundle) {
		
		super.onCreate(bundle);
		mDbHelper = new Information_DbAdapter(this);
		mDbHelper.open();
		setContentView(R.layout.information_edit);
		mCategory = (Spinner) findViewById(R.id.category);
		mNameText = (EditText) findViewById(R.id.information_edit_name);
		mEmail_AddressText = (EditText) findViewById(R.id.information_edit_address);
		mPhone_NumberText=(EditText) findViewById(R.id.information_edit_phone_no);
		mAddressText=(EditText) findViewById(R.id.information_edit_address);

		
		Button confirmButton = (Button) findViewById(R.id.information_edit_button);
		mRowId = null;
		Bundle extras = getIntent().getExtras();
		mRowId = (bundle == null) ? null : (Long) bundle
				.getSerializable(Information_DbAdapter.KEY_ROWID);
		
		if (extras != null) {
			mRowId = extras.getLong(Information_DbAdapter.KEY_ROWID);
		}
		populateFields();
		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_OK);
				finish();
			}
			
		});
		}
	
	
	private void populateFields() {
		
		if (mRowId != null) {
			
			Cursor information = mDbHelper.fetchInformation(mRowId);
			startManagingCursor(information);
			
			String category = information.getString(information
					.getColumnIndexOrThrow(Information_DbAdapter.KEY_CATEGORY));
			
			for (int i = 0; i < mCategory.getCount(); i++) {
				
				String s = (String) mCategory.getItemAtPosition(i);
				Log.e(null, s + " " + category);
				if (s.equalsIgnoreCase(category)) {
					mCategory.setSelection(i);
				}
			}
			
			mNameText.setText(information.getString(information
					.getColumnIndexOrThrow(Information_DbAdapter.KEY_NAME)));
			
			
			mEmail_AddressText.setText(information.getString(information
					.getColumnIndexOrThrow(Information_DbAdapter.KEY_EMAIL_ADDRESS)));
			
			mPhone_NumberText.setText(information.getString(information
					.getColumnIndexOrThrow(Information_DbAdapter.KEY_PHONE_NUMBER)));
			
			
			mAddressText.setText(information.getString(information
					.getColumnIndexOrThrow(Information_DbAdapter.KEY_ADDRESS)));
		}
	}
	
	protected void onSaveInstanceState(Bundle outState) {
		
		super.onSaveInstanceState(outState);
		saveState();
		outState.putSerializable(Information_DbAdapter.KEY_ROWID, mRowId);
	}
	
	
	protected void onPause() {
		super.onPause();
		saveState();
	}
	
	protected void onResume() {
		super.onResume();
		populateFields();
	}
	
	
	
	private void saveState() {
		
		String category = (String) mCategory.getSelectedItem();
		String name = mNameText.getText().toString();
		String email_address = mEmail_AddressText.getText().toString();
		String phone_number = mPhone_NumberText.getText().toString();
		String address = mAddressText.getText().toString();
		
		if (mRowId == null) {
			
			long id = mDbHelper.createInformation(category, name, email_address,phone_number,address);
			
			if (id > 0) {
				
				mRowId = id;
			}
			
		}
		
		else {
			
			mDbHelper.updateInformation(mRowId, category, name, email_address,phone_number,address);
		}
	}

}
