package ca.mogkolpon.pbrurun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    //Explicit
    private EditText nameEditText, userEditText, passeordEditText;
    private RadioGroup radioGroup;
    private RadioButton avata0RadioButton, avata1RadioButton,
            avata2RadioButton, avata3RadioButton, avata4RadioButton;
    private String nameString, userString, passwordString, avataString;
    private int indexAnInt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget
        bindWidget();

        //Radio Controller
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radioButton:
                        indexAnInt = 0;
                        break;
                    case R.id.radioButton2:
                        indexAnInt = 1;
                        break;
                    case R.id.radioButton3:
                        indexAnInt = 2;
                        break;
                    case R.id.radioButton4:
                        indexAnInt = 3;
                        break;
                    case R.id.radioButton5:
                        indexAnInt = 4;
                        break;
                }
            }
        });
    }// Main Method

    private void bindWidget() {
        nameEditText = (EditText) findViewById(R.id.editText);
        userEditText = (EditText) findViewById(R.id.editText2);
        passeordEditText = (EditText) findViewById(R.id.editText3);
        radioGroup = (RadioGroup) findViewById(R.id.ragAvata);
        avata0RadioButton = (RadioButton) findViewById(R.id.radioButton);
        avata1RadioButton = (RadioButton) findViewById(R.id.radioButton2);
        avata2RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        avata3RadioButton = (RadioButton) findViewById(R.id.radioButton4);
        avata4RadioButton = (RadioButton) findViewById(R.id.radioButton5);
    }

    public void clickSignUpSign(View view) {

        nameString = nameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passeordEditText.getText().toString().trim();

        if (nameString.equals("")||userString.equals("")||passwordString.equals("")) {
            //H
            Toast.makeText(this,"fffff",
                    Toast.LENGTH_SHORT).show();
        } else {
            //No space
            uploadValueToServer();


        }

    } // click

    private void uploadValueToServer() {
        Log.d("pbruV1", "name ==>" + nameString);
        Log.d("pbruV1", "user ==>" + userString);
        Log.d("pbruV1", "pass ==>" + passwordString);
        Log.d("pbruV1", "avata ==>" + Integer.toString(indexAnInt));
    }

}//Main Class
