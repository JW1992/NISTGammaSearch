package jw.nistgammadata;

import java.io.IOException;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by jiawei on 11/21/17.
 */

public class NISTDBAdapter {
    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public NISTDBAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public NISTDBAdapter(Context context, String strDBName)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext, strDBName);
    }

    public NISTDBAdapter createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public NISTDBAdapter createDatabase(String strDBName) throws SQLException
    {
        try
        {
            mDbHelper.createDataBase(strDBName);
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public NISTDBAdapter open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }
    public NISTDBAdapter open(String strDBName) throws SQLException
    {
        try
        {
            mDbHelper.openDataBase(strDBName);
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }


    public void close()
    {
        mDbHelper.close();
    }

    public Cursor getTestData()//Debugging: Jiawei-Nov23
    {
        try
        {
            String sql ="SELECT full_name FROM ElementsTable";

            Cursor mCur = mDb.rawQuery(sql, null);
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getElementName(int nInputAtomNum)
    {
        try
        {
            StringBuilder tempStrBuilder = new StringBuilder();
            tempStrBuilder.append(nInputAtomNum);
            String sql ="SELECT name FROM elements WHERE atom_number LIKE " + tempStrBuilder.toString();

            Cursor mCur = mDb.rawQuery(sql, null);
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getElementAtomNumByName(String strElemName)
    {
        try
        {
            StringBuilder tempStrBuilder = new StringBuilder();
            tempStrBuilder.append(strElemName);
            String sql ="SELECT atom_number FROM elements WHERE name LIKE '" + tempStrBuilder.toString() + "' OR symbol LIKE '" + tempStrBuilder.toString() + "'";

            Cursor mCur = mDb.rawQuery(sql, null);
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getMaterialNameByAbbrev(String strElemSymbol)
    {
        try
        {
            StringBuilder tempStrBuilder = new StringBuilder();
            tempStrBuilder.append(strElemSymbol);
            String sql ="SELECT * FROM materials WHERE name LIKE '%%" + tempStrBuilder.toString() + "%%'";

            Cursor mCur = mDb.rawQuery(sql, null);
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getInfoFromElemTable(String[] strQuery, String strTableName)
    {
        if(strQuery==null) return null;
        try
        {
            StringBuilder tempStrBuilder = new StringBuilder();
            tempStrBuilder.append("SELECT ");
            for(int i=0; i<strQuery.length-1; i++){
                tempStrBuilder.append(strQuery[i]);
                tempStrBuilder.append(", ");
            }
            tempStrBuilder.append(strQuery[strQuery.length-1]);
            tempStrBuilder.append(" FROM ");
            //tempStrBuilder.append("SELECT energy FROM ");
            tempStrBuilder.append(strTableName);
            String sql =tempStrBuilder.toString();

            Cursor mCur = mDb.rawQuery(sql, null);
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
}
