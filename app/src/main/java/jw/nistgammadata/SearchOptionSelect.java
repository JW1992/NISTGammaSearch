package jw.nistgammadata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SearchOptionSelect extends AppCompatActivity {
    public static final String SEARCH_ELEMENT_NUMBER = "jw.nistgammadata.ELEMENT_NUMBER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_option_select);
    }

    public void searchByAtomNum(View view){
        Intent intent = new Intent(this, DisplayElementActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextIDSearchNumber);
        String strInputElemNum = editText.getText().toString();
        try {
            int nInElemNum = Integer.parseInt(strInputElemNum);
            if(nInElemNum<=0 || nInElemNum>=3){
                updateWarningTextView(new String("Wrong input range, need integer from 1 to 2"));
                return;
            }
            intent.putExtra(SEARCH_ELEMENT_NUMBER, nInElemNum);
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
