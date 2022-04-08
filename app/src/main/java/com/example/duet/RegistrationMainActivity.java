package com.example.duet;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;

public class RegistrationMainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView mDisplayDate,Registration,editTextTextEmailAddress,editTextTextPassword,editTextTextPersonName2,editTextTextPersonName,main_LBL_date;
    private Spinner main_SPN_gender;
    private Spinner main_SPN_interested;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String[] gender = {"Gender","Female","Male","Other"};
    private String[] interested = {"Interested In","Female","Male","Other"};
    private EditText editTextPhone,editTextTextPostalAddress;
    private Typeface type2;
    private MaterialButton main_BTN_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_main);

        main_SPN_gender = findViewById(R.id.main_SPN_gender);
        main_SPN_gender.setOnItemSelectedListener(this);
        main_SPN_interested = findViewById(R.id.main_SPN_interested);
        main_SPN_interested.setOnItemSelectedListener(this);
        mDisplayDate = (TextView) findViewById(R.id.main_LBL_date);
        Registration=(TextView) findViewById(R.id.Registration);

        editTextTextEmailAddress=(TextView) findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword=(TextView) findViewById(R.id.editTextTextPassword);
        editTextTextPersonName2=(TextView) findViewById(R.id.editTextTextPersonName2);
        editTextTextPersonName=(TextView) findViewById(R.id.editTextTextPersonName);
        main_LBL_date=(TextView) findViewById(R.id.main_LBL_date);
        editTextPhone=findViewById(R.id.editTextPhone);
        editTextTextPostalAddress=findViewById(R.id.editTextTextPostalAddress);
        main_BTN_next = findViewById(R.id.main_BTN_next);


        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Momcake-Bold.otf");
        type2 = Typeface.createFromAsset(getAssets(),"fonts/Rounded.ttf");
        //Registration.setTypeface(type);
        editTextTextEmailAddress.setTypeface(type2);
        editTextTextPassword.setTypeface(type2);
        editTextTextPersonName2.setTypeface(type2);
        editTextTextPersonName.setTypeface(type2);
        main_LBL_date.setTypeface(type2);
        editTextPhone.setTypeface(type2);
        editTextTextPostalAddress.setTypeface(type2);


        setDatePicker();
        setSpinner(main_SPN_gender,gender);
        setSpinner(main_SPN_interested,interested);

        main_BTN_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // collect all data and send it to the next page
                String[] userDetails = {
                  editTextTextEmailAddress.getText().toString(),
                  editTextTextPassword.getText().toString(),
                  editTextTextPersonName2.getText().toString(),
                  editTextTextPersonName.getText().toString(),
                  main_LBL_date.getText().toString(),
                  main_SPN_gender.toString(),
                  editTextPhone.getText().toString(),
                  editTextTextPostalAddress.getText().toString()
                };

                // TODO -  check what field is empty and pop a message to fill it before continue to next page

                Intent intent = new Intent(RegistrationMainActivity.this, RegistrationArtistsActivity.class);
                intent.putExtra("userDetails",userDetails);
                startActivity(intent);
                finish();
            }
        });

    }

    private void setSpinner(Spinner spn,String[] arr) {



        //ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.gender, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter adapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arr){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spn.setAdapter(adapter);
        //spn.setOnItemSelectedListener(this);
    }

    private void setDatePicker() {
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegistrationMainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}