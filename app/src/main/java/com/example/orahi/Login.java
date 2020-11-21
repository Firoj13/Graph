package com.example.orahi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class Login extends AppCompatActivity {

    FirebaseAuth mAuth;

    //phone number
    EditText phoneNumber;
    CountryCodePicker countryCodePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.user_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loginwith_Number();
            }
        });
    }
    //login with number
    private void Loginwith_Number() {


        countryCodePicker = findViewById(R.id.login_country_code_picker);
        phoneNumber = findViewById(R.id.login_phone_number);

        if(!validatPhoneNumber()){
            return;
        }

        String _getUserEnterPhoneNumber = phoneNumber.getText().toString().trim(); //getPhone number
        String _phoneNo = "+"+countryCodePicker.getFullNumber()+_getUserEnterPhoneNumber;

        Intent i = new Intent(getApplicationContext(), Verify_Otp.class);
        i.putExtra("phoneNo",_phoneNo);

        startActivity(i);
        finish();
    }

    private boolean validatPhoneNumber() {

        String val = phoneNumber.getText().toString().trim();

        if (val.isEmpty()){
            Toast.makeText(this, "Please enter a number!", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
}