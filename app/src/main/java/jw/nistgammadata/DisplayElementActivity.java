package jw.nistgammadata;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayElementActivity extends AppCompatActivity {

    private NISTDBAdapter mDbHelper;
    public static final String ATTEN_ENERGY = "jw.nistgammadata.ENERGY_LIST";
    public static final String ATTEN_COEFF = "jw.nistgammadata.COEFF_LIST";
    private double[] fEnergy;
    private double[] fCoeff;
    private double fDensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_element);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        int nElemNumber = intent.getIntExtra(SearchOptionSelect.SEARCH_ELEMENT_NUMBER, 0);
        fDensity = intent.getDoubleExtra(SearchOptionSelect.SEARCH_OBJECT_DENSITY, 0.0);

        String strElementName = "N/A";
        boolean bElementExist = false;

        mDbHelper = new NISTDBAdapter(this);
        mDbHelper.createDatabase();
        mDbHelper.open();
        Cursor testData = mDbHelper.getElementName(nElemNumber);
        if (testData!=null)
        {
            if(testData.moveToFirst()) {
                //do {
                //int nAtomNumber = testData.getInt(testData.getColumnIndex("atom_number"));
                strElementName = testData.getString(testData.getColumnIndex("name"));
                bElementExist = true;

                //} while (testdata.moveToNext());
            }
            else strElementName = "Element is not available";
        }

        if(bElementExist){
            StringBuilder strBDElem = new StringBuilder(strElementName);
            //String strTemp = String.valueOf(Character.toUpperCase(strBDElem.charAt(0)));
            //strBDElem.replace(0, 1, strTemp);
            //strBDElem.insert(0, "Attenuation_");
            double fEnergyTemp, fPPTemp, fTotalTemp;
            //Cursor energyData = mDbHelper.getEnergyFromElemTable("Attenuation_Hydrogen");
            //String[] strInfoQuery = new String[] {"energy","totalcoherent"};
            String[] strInfoQuery = new String[] {"*"};
            Cursor energyData = mDbHelper.getInfoFromElemTable(strInfoQuery, strBDElem.toString());
            if (energyData!=null)
            {
                if(energyData.moveToFirst()) {
                    /*fEnergyTemp  = energyData.getDouble(0);
                    TextView textViewEnergyTemp = (TextView) findViewById(R.id.textViewIDEnergyValues);
                    textViewEnergyTemp.setText(Double.toString(fEnergyTemp));*/
                    //Debugging: Jiawei-Nov23
                    LinearLayout myLinearLayoutEnergy = (LinearLayout) findViewById(R.id.layoutEnergy);
                    LinearLayout myLinearLayoutAtten = (LinearLayout) findViewById(R.id.layoutAtten);
                    final int N = energyData.getCount(); // total number of textviews to add
                    fEnergy = new double[N];
                    fCoeff = new double[N];
                    //final TextView[] myTextViews = new TextView[N]; // create an empty array;
                    for (int i = 0; i < N; i++) {
                        // create a new textview
                        final TextView textViewEnergy = new TextView(this);
                        final TextView textViewAtten = new TextView(this);
                        // set some properties of rowTextView or something
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
                        //rowTextView.setText(Double.toString(fEnergyTemp));
                        // add the textview to the linearlayout
                        myLinearLayoutEnergy.addView(textViewEnergy);
                        myLinearLayoutAtten.addView(textViewAtten);
                        // save a reference to the textview for later
                        //myTextViews[i] = textViewEnergy;
                        if(!energyData.moveToNext()) break;
                    }
                }
            }
        }
        mDbHelper.close();

        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.textViewIDElementName);
        textView.setText(strElementName);
    }
    public void onCalcAtten(View view){
        Intent intent = new Intent(this, CalcAttenuationActivity.class);
        intent.putExtra(ATTEN_ENERGY, fEnergy);
        intent.putExtra(ATTEN_COEFF, fCoeff);
        intent.putExtra(SearchOptionSelect.SEARCH_OBJECT_DENSITY, fDensity);
        startActivity(intent);
    }
}
