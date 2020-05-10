package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.List;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + UsersMaster.Users.TABLE_NAME + "("+
                UsersMaster.Users._ID + " INTEGER PRIMARY KEY, " +
                UsersMaster.Users.COLUMN_NAME_USERNAME + " TEXT, "+
                UsersMaster.Users.COLUMN_NAME_PASSWORD + " TEXT )";



        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UsersMaster.Users.TABLE_NAME);
        onCreate(db);
    }

    public void AddInfo(String username, String password){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_USERNAME, username);
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);

        long newRowID = db.insert(UsersMaster.Users.TABLE_NAME, null, values);
    }

    public List<String> ReadAllInfo(){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                UsersMaster.Users._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };

        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(UsersMaster.Users.TABLE_NAME,
                projection,
                null, null, null , null,
                sortOrder
        );

        List<String> userNames = new ArrayList<>();
        List<String> passwords = new ArrayList<>();

        while (cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String pass = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));
            userNames.add(username);
            userNames.add(pass);
        }

        cursor.close();
        return userNames;
    }

    public List<String> readInfo(String userName, String UserPAssword){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                UsersMaster.Users._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };

        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(UsersMaster.Users.TABLE_NAME,
                projection,
                null, null, null , null,
                sortOrder
        );

        List<String> userNames = new ArrayList<>();
        List<String> passwords = new ArrayList<>();

        while (cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String pass = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));

            if(userName.equals(userName) && pass.equals(UserPAssword)){
                userNames.add(username);
                userNames.add(pass);
            }

        }

        cursor.close();
        return userNames;
    }

    public void DeleteInfo(String username){
        SQLiteDatabase db = getReadableDatabase();

        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";

        String[] selectionArgs = { username };

        db.delete(UsersMaster.Users.TABLE_NAME, selection, selectionArgs);
    }

    public void updateInfo(String username, String password){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_USERNAME, password);

        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = { username };

        int count = db.update(UsersMaster.Users.TABLE_NAME, values, selection, selectionArgs);
    }
}