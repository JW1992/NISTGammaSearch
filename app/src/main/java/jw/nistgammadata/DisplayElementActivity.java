package jw.nistgammadata;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayElementActivity extends AppCompatActivity {

    private TestAdapter mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_element);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        int nElemNumber = intent.getIntExtra(SearchOptionSelect.SEARCH_ELEMENT_NUMBER, 0);

        String strElementName = "N/A";

        mDbHelper = new TestAdapter(this);
        mDbHelper.createDatabase();
        mDbHelper.open();
        Cursor testdata = mDbHelper.getElementName(nElemNumber);
        if (testdata!=null)
        {
            if(testdata.moveToFirst()) {
                //do {
                //int nAtomNumber = testdata.getInt(testdata.getColumnIndex("atom_number"));
                strElementName = testdata.getString(testdata.getColumnIndex("full_name"));

                //} while (testdata.moveToNext());
            }
        }
        mDbHelper.close();

        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.textViewIDElementName);
        textView.setText(strElementName);
    }
}
