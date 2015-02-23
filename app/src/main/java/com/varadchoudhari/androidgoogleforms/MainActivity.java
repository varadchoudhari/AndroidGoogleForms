package com.varadchoudhari.androidgoogleforms;

import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.URLEncoder;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    //Objects for Layout Widgets
    EditText firstname, lastname, joinreason;
    RadioGroup gender;
    RadioButton male, female;
    CheckBox verifyage, acceptterms;
    Button register;
    Spinner country;

    //Strings for storing data
    String firstName, lastName, joinReason, getGender, ageVerified, termsAccepted, getCountry;
    int getGenderId;
    boolean isAgeVerified, areTermsAccepted;

    String eventHandler = "Google Forms Upload";

    //IDs for radio buttons
    private static final int MALE_ID = 1;
    private static final int FEMALE_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Registering objects with layout elements
        firstname = (EditText) findViewById(R.id.firstName);
        lastname = (EditText) findViewById(R.id.lastName);
        gender = (RadioGroup) findViewById(R.id.genderGroup);
        male = (RadioButton) findViewById(R.id.maleCheck);
        male.setId(MALE_ID);
        female = (RadioButton) findViewById(R.id.femaleCheck);
        female.setId(FEMALE_ID);
        joinreason = (EditText) findViewById(R.id.whyJoin);
        verifyage = (CheckBox) findViewById(R.id.ageCheck);
        acceptterms = (CheckBox) findViewById(R.id.termsCheck);

        register = (Button) findViewById(R.id.hitRegister);
        register.setOnClickListener(this);



        //Country dropdown box implementation - Array list can be found in /values/countries.xml
        country = (Spinner) findViewById(R.id.country_list);
        ArrayAdapter<CharSequence> country_array = ArrayAdapter.createFromResource(this,R.array.countries,android.R.layout.simple_list_item_1);
        country_array.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        country.setAdapter(country_array);





    }

    public void postData()
    {
        //URL to communicate with the online form.
        String fullURL = "https://docs.google.com/forms/d/1EfUh0v0WcynKpGpgMy9Ssox0XrEr4tuETAV0dNy58ZY/formResponse";
        HttpRequest mReq = new HttpRequest();


        //Adding data to the appropriate fields.
        String data = "entry_800579790=" + URLEncoder.encode(firstName) + "&" + "entry_525880789=" + URLEncoder.encode(lastName) + "&" + "entry_1794160077=" + URLEncoder.encode(getGender) + "&" + "entry_1697431401=" + URLEncoder.encode(getCountry) + "&" + "entry_161814974=" + URLEncoder.encode(joinReason) + "&" + "entry_2112447081=" + URLEncoder.encode(ageVerified) + "&" + "entry_1688312505=" + URLEncoder.encode(termsAccepted);
        String response = mReq.sendPost(fullURL, data);
        Log.i(eventHandler, response);
        Toast.makeText(this, "You have registered!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {

        //Getting data into the string.
        firstName = firstname.getText().toString();
        lastName = lastname.getText().toString();
        getGenderId = gender.getCheckedRadioButtonId();
        isAgeVerified = verifyage.isChecked();
        areTermsAccepted = acceptterms.isChecked();
        joinReason = joinreason.getText().toString();
        getCountry = country.getSelectedItem().toString();


        //Matching input and assigning appropriate value to the string.
        if(getGenderId == 1)
            getGender = "Male";
        else
            getGender = "Female";



        if(isAgeVerified == true)
            ageVerified = "Yes, I am at least 18 years old.";


        else
            ageVerified = "";

        if(areTermsAccepted == true) {
            termsAccepted = "I accept the terms and conditions";
            //First Name field validation
            if(firstName.isEmpty() == false)
                postData();

            else
                Toast.makeText(this, "First Name field cannot be left blank", Toast.LENGTH_LONG).show();

        }
        else
            Toast.makeText(this, "FAILED: Please accept the terms and conditions!", Toast.LENGTH_LONG).show();




    }
}
