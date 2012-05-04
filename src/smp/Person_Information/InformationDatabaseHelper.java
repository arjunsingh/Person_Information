package smp.Person_Information;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InformationDatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "applicationdata";
	private static final int DATABASE_VERSION = 7;
	
	public InformationDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	public void onCreate(SQLiteDatabase database) {
		Information_Table.onCreate(database);
	}
	
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Information_Table.onUpgrade(database, oldVersion, newVersion);
	}
}
