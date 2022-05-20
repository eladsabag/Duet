package com.example.duet.activities;



import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duet.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Map;

public class RegistrationMainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextInputEditText editTextEmail, editTextFirstName, editTextLastName, editTextOccupation,editBio;
    private Spinner main_SPN_gender;
    private Spinner main_SPN_interested;
    private TextView main_LBL_date,Registration,error;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String[] gender = {"Gender","Female","Male","Other"};
    private String[] interested = {"Interested In","Female","Male","Other"};
    private Typeface type2;
    private MaterialButton main_BTN_next;
    private boolean isExist = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_main);
        findViews();
        setFonts();
        setDatePicker();
        setSpinner(main_SPN_gender,gender);
        setSpinner(main_SPN_interested,interested);

        main_BTN_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // collect all data and send it to the next page
                String[] userDetails = {
                        editTextEmail.getText().toString(),
                        editTextFirstName.getText().toString(),
                        editTextLastName.getText().toString(),
                        main_LBL_date.getText().toString().substring(12),
                        main_SPN_gender.getSelectedItem().toString(),
                        main_SPN_interested.getSelectedItem().toString(),
                        editTextOccupation.getText().toString(),
                        editBio.getText().toString()
                };
                Log.d("ccc","date "+main_LBL_date.getText().toString().substring(12));

                getUser();
                if (!checkIfEmpty()) {
                    error.setText("Fill out the Required details");
                    error.setVisibility(View.VISIBLE);

                } else if(!isExist) {
                    Intent intent = new Intent(RegistrationMainActivity.this, RegistrationArtistsActivity.class);
                    intent.putExtra("userDetails",userDetails);
                    startActivity(intent);
                } else {
                    error.setText("User email exists on system. Try again!");
                    error.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void getUser(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String endpoint = "http://10.0.0.11:8085/iob/users/login/2022b.Yaeli.Bar.Gimelshtei/" + editTextEmail.getText().toString();
        StringRequest request = new StringRequest(Request.Method.GET, endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // on below line we are displaying a success toast message.
                        try {
                            //response json
                            JSONObject respObj = new JSONObject(response);
                            isExist = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        isExist = false; }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        queue.add(request);
    }

    private boolean checkIfEmpty() {
        boolean isFull = false;
        if(editTextEmail.getText().toString().equals("")){
            //Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        }else if(editTextFirstName.getText().toString().equals("")){
            //Toast.makeText(this, "Please enter first name", Toast.LENGTH_SHORT).show();
        }else if(editTextLastName.getText().toString().equals("")){
            //Toast.makeText(this, "Please enter second name", Toast.LENGTH_SHORT).show();
        }else if(editTextOccupation.getText().toString().equals("")){
            //Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
        }else if(main_LBL_date.getText().toString().equals("")){
            //Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
        }else{
            isFull = true;
        }
        return isFull;
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
                    tv.setTypeface(type2);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                    tv.setTypeface(type2);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spn.setAdapter(adapter);
        //spn.setOnItemSelectedListener(this);
    }

    private void setDatePicker() {
        main_LBL_date.setOnClickListener(new View.OnClickListener() {
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
                main_LBL_date.setTypeface(type2);
                main_LBL_date.setText("Birth Date: "+date);
            }
        };
    }

    private void setFonts() {
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Momcake-Bold.otf");
        type2 = Typeface.createFromAsset(getAssets(),"fonts/Rounded.ttf");
        editTextEmail.setTypeface(type2);
        editTextFirstName.setTypeface(type2);
        editTextLastName.setTypeface(type2);
        editBio.setTypeface(type2);
        editTextOccupation.setTypeface(type2);

    }

    private void findViews() {

        main_SPN_gender = findViewById(R.id.main_SPN_gender);
        main_SPN_gender.setOnItemSelectedListener(this);
        main_SPN_interested = findViewById(R.id.main_SPN_interested);
        main_SPN_interested.setOnItemSelectedListener(this);
        Registration= findViewById(R.id.Registration);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        main_LBL_date= findViewById(R.id.main_LBL_date);
        editTextOccupation =findViewById(R.id.editTextoccupation);
        main_BTN_next = findViewById(R.id.main_BTN_next);
        error = findViewById(R.id.error);
        editBio=findViewById(R.id.editBio);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}