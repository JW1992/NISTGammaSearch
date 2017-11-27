package jw.nistgammadata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectMaterialActivity extends AppCompatActivity {

    public static final String SELECT_MATERIAL_NAME = "jw.nistgammadata.MATERIAL_NAME";
    public static final String SEARCH_OBJECT_DENSITY = "jw.nistgammadata.OBJECT_DENSITY";
    private NISTDBAdapter mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_material);
        LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayoutMatchingMaterials); 
        final Intent intentDispMaterial = new Intent(this, DisplayMaterialActivity.class);

        // Get the Intent that started this activity and extract the string
        final Intent intent = getIntent();
        String strMatAbbrev = intent.getStringExtra(SearchOptionSelect.SEARCH_MATERIAL_ABBREV);

        mDbHelper = new NISTDBAdapter(this);
        mDbHelper.createDatabase();
        mDbHelper.open();
        Cursor mCur = mDbHelper.getMaterialNameByAbbrev(strMatAbbrev);
        if (mCur.moveToFirst()) {
            int N = mCur.getCount();
            for(int i = 0; i<N; i++){
                final String strMaterialName = mCur.getString(mCur.getColumnIndex("name"));
                Button myButton = new Button(this);
                myButton.setText(strMaterialName);
                myButton.setTransformationMethod(null);
                myButton.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        intentDispMaterial.putExtra(SELECT_MATERIAL_NAME, strMaterialName);
                        startActivity(intentDispMaterial);
                    }
                });

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ll.addView(myButton, lp);
                if(!mCur.moveToNext()) break;
            }
        }        
    }
}
