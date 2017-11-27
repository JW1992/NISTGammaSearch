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

    private NISTDBAdapter mDbHelper;
    public static final String SEARCH_ELEMENT_NUMBER = "jw.nistgammadata.ELEMENT_NUMBER";
    public static final String SEARCH_MATERIAL_ABBREV = "jw.nistgammadata.MATERIAL_CURSOR";
    public static final String SEARCH_OBJECT_DENSITY = "jw.nistgammadata.OBJECT_DENSITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_option_select);

        mDbHelper = new NISTDBAdapter(this);
        mDbHelper.createDatabase();
        mDbHelper.open();
    }

    public void searchByElemName(View view) {
        Intent intent = new Intent(this, DisplayElementActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextIDSearchSymbol);
        String strInputName = editText.getText().toString();
        Cursor testdata = mDbHelper.getElementAtomNumByName(strInputName);//Search by name
        if (testdata!=null)
        {
            if(testdata.moveToFirst()) {
                int nElemNum = testdata.getInt(testdata.getColumnIndex("atom_number"));
                intent.putExtra(SEARCH_ELEMENT_NUMBER, nElemNum);
                double fTemp = testdata.getDouble(testdata.getColumnIndex("density"));
                intent.putExtra(SEARCH_OBJECT_DENSITY, fTemp);
                startActivity(intent);
            }
            else{
                updateWarningTextView(new String("Unable to find this element. Also check spells!"));
            }
        }
    }

    public void searchByMaterialName(View view) {
        Intent intent = new Intent(this, SelectMaterialActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextIDSearchMaterial);
        String strInputName = editText.getText().toString();
        Cursor mCur = mDbHelper.getMaterialNameByAbbrev(strInputName);//Search by name
        if (mCur.getCount() > 0)
        {
            intent.putExtra(SEARCH_MATERIAL_ABBREV, strInputName);
            startActivity(intent);
        }
        else{
            updateWarningTextView(new String("Unable to find matching material. Also check spells!"));
        }
    }

    public void searchByAtomNum(View view){
        Intent intent = new Intent(this, DisplayElementActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextIDSearchNumber);
        String strInputElemNum = editText.getText().toString();
        try {
            int nInElemNum = Integer.parseInt(strInputElemNum);
            if(nInElemNum<=0 || nInElemNum>=93){
                updateWarningTextView(new String("Invalid input atom number, need integer from 1 to 92"));
                return;
            }

            //Debugging Jiawei-Nov21,2017
            //mDbHelper.open();
            //Cursor testdata = mDbHelper.getElementName(nInElemNum);
            /*if (testdata!=null)
            {
                if(testdata.moveToFirst()) {
                    //do {
                        //int nAtomNumber = testdata.getInt(testdata.getColumnIndex("atom_number"));
                        String strElementName = testdata.getString(testdata.getColumnIndex("full_name"));
                        updateWarningTextView(strElementName);
                        int nTemp = 0;

                    //} while (testdata.moveToNext());
                }
            }*/
            //mDbHelper.close();
            Cursor testdata = mDbHelper.getElementName(nInElemNum);
            testdata.moveToFirst();
            intent.putExtra(SEARCH_ELEMENT_NUMBER, nInElemNum);
            double fTemp = testdata.getDouble(testdata.getColumnIndex("density"));
            intent.putExtra(SEARCH_OBJECT_DENSITY, fTemp);
            startActivity(intent);
        } catch (NumberFormatException e) {
            //Remaining to be added: invalid data type warning
            updateWarningTextView(new String("Wrong data type, need integer from 1 to 92"));
        }
    }

    public void updateWarningTextView(String toThis) {
        TextView textView = (TextView) findViewById(R.id.textViewWarningMessage);
        textView.setText(toThis);
    }
}
