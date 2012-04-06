package gre.pojo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class SqlHelper extends SQLiteOpenHelper {
	public static final String DATABASE_PATH = "/data/data/gre.pojo/databases/";

	public static final String DATABASE_NAME = "abcd.db";


	public SQLiteDatabase db;

	private final Context myContext;

	public SqlHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
		this.myContext = context;

	}
	public SQLiteDatabase loadDb(Context context){
		//Close any old db handle
		try{
		if (db != null && db.isOpen()) {
		db.close();
		}
		File fileTest = context.getFileStreamPath(DATABASE_NAME);
		boolean exists = fileTest.exists();
		if(exists==false)
		{

		// The name of the database to use from the bundled assets.
		InputStream myInput = context.getAssets().open(DATABASE_NAME, Context.MODE_PRIVATE);

		// Create a file in the app’s file directory since sqlite requires a path
		// Not ideal but we will copy the file out of our bundled assets and open it
		// it in another location.
		FileOutputStream myOutput = context.openFileOutput(DATABASE_NAME, Context.MODE_PRIVATE);

		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
		myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		// Guarantee Write!
		myOutput.getFD().sync();
		myOutput.close();
		myInput.close();
		}
		// Not grab the newly written file
		File fileObj = context.getFileStreamPath(DATABASE_NAME);
		// and open the database
		
		return db = SQLiteDatabase.openDatabase(fileObj.getAbsolutePath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
		}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// check if exists and copy database from resource
		createDB();

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("SqlHelper", "Upgrading database from version " + oldVersion
				+ " to " + newVersion + ", which will destroy all old data");
		onCreate(db);
	}

	public void createDatabase() {
		createDB();
	}

	private void createDB() {

		boolean dbExist = DBExists();

		if (!dbExist) {

			copyDBFromResource();

		}

	}

	private boolean DBExists() {

		SQLiteDatabase db = null;

		try {
			String databasePath = DATABASE_PATH + DATABASE_NAME;
			db = SQLiteDatabase.openDatabase(databasePath, null,
					SQLiteDatabase.OPEN_READWRITE);
			db.setLocale(Locale.getDefault());
			db.setLockingEnabled(true);
			db.setVersion(1);

		} catch (SQLiteException e) {

			Log.e("SqlHelper", "database not found");

		}

		if (db != null) {

			db.close();

		}

		return db != null ? true : false;
	}

	private void copyDBFromResource() {

		InputStream inputStream = null;
		OutputStream outStream = null;
		String dbFilePath = DATABASE_PATH + DATABASE_NAME;

		try {

			inputStream = myContext.getAssets().open(DATABASE_NAME);

			outStream = new FileOutputStream(dbFilePath);

			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}

			outStream.flush();
			outStream.close();
			inputStream.close();

		} catch (IOException e) {

			throw new Error("Problem copying database from resource file.");

		}

	}

	public void openDataBase() throws SQLException {

		String myPath = DATABASE_PATH + DATABASE_NAME;
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);

	}

	@Override
	public synchronized void close() {

		if (db != null)
			db.close();

		super.close();

	}

	
}