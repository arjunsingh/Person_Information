package smp.Person_Information;




import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Information_Table {
	
	
	private static final String DATABASE_CREATE = "create table information "
			+ "(_id integer primary key autoincrement, "
			+ "category text not null, " + "name text not null,"
			+ " email_address text not null," + "phone_number text not null,"
			+ "address text not null);";
	
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(Information_Table.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS information");
		onCreate(database);
	}

}
