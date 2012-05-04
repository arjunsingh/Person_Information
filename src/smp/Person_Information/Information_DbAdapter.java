package smp.Person_Information;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Information_DbAdapter {
	
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_NAME = "name";
	public static final String KEY_EMAIL_ADDRESS = "email_address";
	public static final String KEY_PHONE_NUMBER="phone_number";
	public static final String KEY_ADDRESS="address";
	private static final String DB_TABLE = "information";
	private Context context;
	private SQLiteDatabase db;
	private InformationDatabaseHelper dbHelper;
	
	
	public Information_DbAdapter(Context context) {
		this.context = context;
	}
	
	public Information_DbAdapter open() throws SQLException {
		dbHelper = new InformationDatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public long createInformation(String category, String name, String email_address, String phone_number, String address) {
		ContentValues values = createContentValues(category, name, email_address, phone_number, address);
				

		return db.insert(DB_TABLE, null, values);
	}
	
	public boolean updateInformation(long rowId, String category, String name,
			String email_address, String phone_number, String address) {
		ContentValues values = createContentValues(category, name,
				email_address, phone_number, address);

		return db.update(DB_TABLE, values, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public boolean deleteInformation(long rowId) {
		return db.delete(DB_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public Cursor fetchAllInformations() {
		return db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_CATEGORY,
				KEY_NAME, KEY_EMAIL_ADDRESS, KEY_PHONE_NUMBER, KEY_ADDRESS }, null, null, null, null, null,null);
	}
	
	public Cursor fetchInformation(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DB_TABLE, new String[] { KEY_ROWID,
				KEY_CATEGORY, KEY_NAME, KEY_EMAIL_ADDRESS, KEY_PHONE_NUMBER, KEY_ADDRESS }, KEY_ROWID + "="
				+ rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	private ContentValues createContentValues(String category, String name,
			String email_address, String phone_number, String address) {
		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY, category);
		values.put(KEY_NAME, name);
		values.put(KEY_EMAIL_ADDRESS, email_address);
		values.put(KEY_PHONE_NUMBER, phone_number);
		values.put(KEY_ADDRESS, address);
		return values;
	}

}
