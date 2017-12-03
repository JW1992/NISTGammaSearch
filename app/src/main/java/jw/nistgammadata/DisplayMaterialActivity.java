package jw.nistgammadata;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayMaterialActivity extends AppCompatActivity {

    private NISTDBAdapter mDbHelper;
    public static final String ATTEN_ENERGY = "jw.nistgammadata.ENERGY_LIST";
    public static final String ATTEN_COEFF = "jw.nistgammadata.COEFF_LIST";
    private double[] fEnergy;
    private double[] fCoeff;
    private double fDensity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_material);

        mDbHelper = new NISTDBAdapter(this);
        mDbHelper.createDatabase();
        mDbHelper.open();
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String strMaterialName = intent.getStringExtra(SelectMaterialActivity.SELECT_MATERIAL_NAME);
        Cursor mCurTemp = mDbHelper.getMaterialNameByAbbrev(strMaterialName);
        mCurTemp.moveToFirst();
        fDensity = mCurTemp.getDouble(mCurTemp.getColumnIndex("density"));
        StringBuilder strBDElem = new StringBuilder(strMaterialName);
        double fEnergyTemp, fPPTemp, fTotalTemp;
        String[] strInfoQuery = new String[] {"*"};
        Cursor energyData = mDbHelper.getInfoFromElemTable(strInfoQuery, strBDElem.toString());

        if (energyData!=null)
        {
            if(energyData.moveToFirst()) {
                //Debugging: Jiawei-Nov25

                LinearLayout myLinearLayoutEnergy = (LinearLayout) findViewById(R.id.layoutEnergy);
                LinearLayout myLinearLayoutAtten = (LinearLayout) findViewById(R.id.layoutAtten);
                LinearLayout myLinearLayoutEdge = (LinearLayout) findViewById(R.id.layoutEdge);
                final int N = energyData.getCount(); // total number of textviews to add
                fEnergy = new double[N];
                fCoeff = new double[N];
                for (int i = 0; i < N; i++) {
                    // create a new textview
                    final TextView textViewEnergy = new TextView(this);
                    final TextView textViewAtten = new TextView(this);
                    final TextView textViewEdge = new TextView(this);
                    // set some properties of rowTextView or something
                    String strEdge = "";
                    strEdge = energyData.getString(energyData.getColumnIndex("absedge"));
                    fEnergyTemp  = energyData.getDouble(energyData.getColumnIndex("energy"));
                    //fPPTemp = energyData.getDouble(energyData.getColumnIndex("photoelectric"));
                    fTotalTemp = energyData.getDouble(energyData.getColumnIndex("attencoeff"));
                    fEnergy[i] = fEnergyTemp;
                    fCoeff[i] = fTotalTemp;
                    //String strTempShow = String.format("%-10s %-20s %-30s", Double.toString(fEnergyTemp), Double.toString(fPPTemp), Double.toString(fTotalTemp));
                    String strTempShowEnergy = String.format("%s", Double.toString(fEnergyTemp));
                    String strTempShowAtten = String.format("%s", Double.toString(fTotalTemp));
                    textViewEnergy.setText(strTempShowEnergy);
                    textViewAtten.setText(strTempShowAtten);
                    if(strEdge!=null){
                        textViewEdge.setText(strEdge);
                        myLinearLayoutEdge.addView(textViewEdge);
                    }
                    else{
                        textViewEdge.setText(" ");
                        myLinearLayoutEdge.addView(textViewEdge);
                    }
                    // add the textview to the linearlayout
                    myLinearLayoutEnergy.addView(textViewEnergy);
                    myLinearLayoutAtten.addView(textViewAtten);
                    // save a reference to the textview for later
                    //myTextViews[i] = textViewEnergy;
                    if(!energyData.moveToNext()) break;

                }
            }
        }
        TextView textView = (TextView) findViewById(R.id.textViewMaterialName);
        textView.setText(strMaterialName);
        mDbHelper.close();

    }

    public void onCalcAtten(View view){
        Intent intent = new Intent(this, CalcAttenuationActivity.class);
        intent.putExtra(ATTEN_ENERGY, fEnergy);
        intent.putExtra(ATTEN_COEFF, fCoeff);
        intent.putExtra(SearchOptionSelect.SEARCH_OBJECT_DENSITY, fDensity);
        startActivity(intent);
    }
}
