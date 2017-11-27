package jw.nistgammadata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CalcAttenuationActivity extends AppCompatActivity {

    private double fDensity = 0.0;
    private double fEnergy = 0.0;
    private double fDistance = 0.0;
    private double[] fEnergyList;
    private double[] fCoeffList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_attenuation);
        Intent intent = getIntent();
        fEnergyList = intent.getDoubleArrayExtra(DisplayMaterialActivity.ATTEN_ENERGY);
        fCoeffList = intent.getDoubleArrayExtra(DisplayMaterialActivity.ATTEN_COEFF);
    }
    public void searchByMaterialName(View view) {
        EditText editTextDensity = (EditText) findViewById(R.id.editTextDensity);
        EditText editTextEnergy = (EditText) findViewById(R.id.editTextEnergy);
        EditText editTextDistance = (EditText) findViewById(R.id.editTextEditDistance);
        TextView textViewResult = (TextView) findViewById(R.id.textViewIDCalcAttnResult);
        if(editTextEnergy.getText()==null || editTextDensity.getText()==null || editTextDistance.getText()==null){
            textViewResult.setText("Must enter a value first");
            return;
        }
        try {
            fEnergy = Double.parseDouble(editTextEnergy.getText().toString());
            fDensity = Double.parseDouble(editTextDensity.getText().toString());
            fDistance = Double.parseDouble(editTextDistance.getText().toString());
        }
        catch (NumberFormatException e){
            textViewResult.setText("Must enter a value first");
            return;
        }

        if(fEnergy>=0.001&&fEnergy<=20){
            int i;
            double fCoeffInterp = 0;
            for(i=0; i<fEnergyList.length; i++){
                if(fEnergy == fEnergyList[i]){
                    fCoeffInterp = fCoeffList[i];
                    break;
                }
                if(fEnergy<fEnergyList[i]){
                    fCoeffInterp = fCoeffList[i-1] + (fEnergy-fEnergyList[i-1])*(fCoeffList[i]-fCoeffList[i-1])/(fEnergyList[i]-fEnergyList[i-1]);
                    break;
                }
            }
            textViewResult.setText(Double.toString(Math.exp(-fCoeffInterp*fDensity*fDistance)));
        }
        else{
            textViewResult.setText("Invalid energy range (0.001 to 20)");
        }
    }
}
