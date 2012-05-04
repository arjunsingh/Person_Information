package smp.Person_Information;




import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Person_InformationActivity extends ListActivity {
	private Information_DbAdapter dbHelper;
	private static final int ACTIVITY_CREATE = 5;
	private static final int ACTIVITY_EDIT = 1;
	private static final int DELETE_ID = Menu.FIRST + 1;
	private Cursor cursor;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_list);
        this.getListView().setDividerHeight(5);
        //dbHelper = new Information_DbAdapter(this);
        dbHelper.open();
		fillData();
		registerForContextMenu(getListView());
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
		return true;
    }
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.insert:
			createInformation();
			return true;
    }
    	return super.onMenuItemSelected(featureId, item);
   }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.insert:
			createInformation();
			return true;
		}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    public boolean onContextItemSelected(MenuItem item) {
    	
    	switch (item.getItemId()) {
		case R.id.DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();
			dbHelper.deleteInformation(info.id);
			fillData();
			return true;
		}
    	
    	return super.onContextItemSelected(item);
    }
    
    private void createInformation() {
		Intent i = new Intent(this, Information_DetailActivity.class);
		startActivityForResult(i, ACTIVITY_CREATE);
	}
    
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
    	super.onListItemClick(l, v, position, id);
    	Intent i = new Intent(this, Information_DetailActivity.class);
		i.putExtra(Information_DbAdapter.KEY_ROWID, id);
		startActivityForResult(i, ACTIVITY_EDIT);
		
	
    }
    
    
    protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
    	
		super.onActivityResult(requestCode, resultCode, intent);
		fillData();

	}
    
    private void fillData() {
    	
    	cursor = dbHelper.fetchAllInformations();
		startManagingCursor(cursor);
		
		String[] from = new String[] { Information_DbAdapter.KEY_NAME };
		int[] to = new int[] { R.id.category };
		
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,R.layout.information_row, cursor, from, to);
		
		setListAdapter(notes);
    }
    
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
    	
    	super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }
    
    protected void onDestroy() {
    	
    	super.onDestroy();
    	if (dbHelper != null) {
    		
    		dbHelper.close();
    	}
    }
}