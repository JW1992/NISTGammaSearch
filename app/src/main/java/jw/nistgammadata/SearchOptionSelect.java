package jw.nistgammadata;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class SearchOptionSelect extends AppCompatActivity {

    private TestAdapter mDbHelper;
    public static final String SEARCH_ELEMENT_NUMBER = "jw.nistgammadata.ELEMENT_NUMBER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_option_select);

        mDbHelper = new TestAdapter(this);
        mDbHelper.createDatabase();
    }




    public void searchByAtomNum(View view){
        Intent intent = new Intent(this, DisplayElementActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextIDSearchNumber);
        String strInputElemNum = editText.getText().toString();
        try {
            int nInElemNum = Integer.parseInt(strInputElemNum);
            if(nInElemNum<=0 || nInElemNum>=3){
                updateWarningTextView(new String("Invalid input atom number, need integer from 1 to 2"));
                return;
            }
            intent.putExtra(SEARCH_ELEMENT_NUMBER, nInElemNum);

            //Debugging Jiawei-Nov21,2017
            /*
            mDbHelper.open();
            Cursor testdata = mDbHelper.getElementName(nInElemNum);
            if (testdata!=null)
            {
                if(testdata.moveToFirst()) {
                    //do {
                        //int nAtomNumber = testdata.getInt(testdata.getColumnIndex("atom_number"));
                        String strElementName = testdata.getString(testdata.getColumnIndex("full_name"));
                        updateWarningTextView(strElementName);
                        int nTemp = 0;

                    //} while (testdata.moveToNext());
                }
            }
            mDbHelper.close();
            */

            startActivity(intent);
        } catch (NumberFormatException e) {
            //Remaining to be added: invalid data type warning
            updateWarningTextView(new String("Wrong data type, need integer from 1 to 2"));
        }


    }

    public void updateWarningTextView(String toThis) {
        TextView textView = (TextView) findViewById(R.id.textViewWarningMessage);
        textView.setText(toThis);
    }
}
