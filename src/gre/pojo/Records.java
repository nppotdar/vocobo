package gre.pojo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class Records extends SQLiteOpenHelper implements BaseColumns {
	private static final String DB_NAME = "/data/data/gre.pojo/databases/abcd.db";
	private static final int DB_VERSION = 1;
	private static final String COLUMN_TEXT = "col_text";

	public Records(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE VIRTUAL TABLE ").append("HF").append(" USING FTS3 (");
		sb.append(_ID).append(" INTEGER PRIMAERY KEY, ");
		sb.append(COLUMN_TEXT).append(" TEXT ");
		sb.append(" ); ");
		db.execSQL(sb.toString());

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	

	public List<String> search(String query) {
		
		SQLiteDatabase db = getReadableDatabase();
		
		StringBuffer sb = new StringBuffer();
		String TABLE_NAME = query.toUpperCase().charAt(0)+"";
		sb.append("CREATE VIRTUAL TABLE ").append("HF").append(" USING FTS3 (");
		sb.append(_ID).append(" INTEGER PRIMARY KEY, ");
		sb.append(COLUMN_TEXT).append(" TEXT ");
		sb.append(" ); ");
		db.execSQL(sb.toString());
		
		List<String> result = new ArrayList<String>();
		final Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_TEXT },
				COLUMN_TEXT + " MATCH ?", new String[] { query }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				String text = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEXT));
				result.add(text);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return result;
	}

}
