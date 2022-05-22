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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duet.MSP;
import com.example.duet.R;
import com.example.duet.data.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegistrationFormForSpotifyUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private TextInputEditText editTextFirstName,editTextLastName, editTextOccupation,editBio;
    private TextView main_LBL_date,Registration,error;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Spinner main_SPN_gender;
    private Spinner main_SPN_interested;
    private Typeface type2;
    private MaterialButton main_BTN_finish;
    private String[] gender = {"Gender","Female","Male","Other"};
    private String[] interested = {"Interested In","Female","Male","Other"};
    private String []userDetails={};
    private String [] chosenArtists=new String[3];
    private String song="";
    private String jsonBody="";
    private String jsonDetails="";

    private String email="";
    private User user=new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form_for_spotify_user);
        email=getIntent().getStringExtra("email");
        chosenArtists=getIntent().getStringArrayExtra("chosenArtists");
        song=getIntent().getStringExtra("song");

        //getSpotifyUser();
        findViews();
        setFonts();
        setDatePicker();
        setSpinner(main_SPN_gender,gender);
        setSpinner(main_SPN_interested,interested);
        makeUser();
        main_BTN_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDetails = new String[]{
                        email,
                        editTextFirstName.getText().toString(),
                        editTextLastName.getText().toString(),
                        main_LBL_date.getText().toString().substring(12),
                        main_SPN_gender.getSelectedItem().toString(),
                        main_SPN_interested.getSelectedItem().toString(),
                        editTextOccupation.getText().toString(),
                        editBio.getText().toString()
                };
                Log.d("ccc",userDetails.toString()+"");

                makeUserDetails();

                if (!checkIfEmpty()) {
                    error.setText("Fill out the Required details");
                    error.setVisibility(View.VISIBLE);
                }else {
                    createNewUser();
                    createUserDetails();
                }

                Intent intent = new Intent(RegistrationFormForSpotifyUser.this, MatchActivity.class);
                intent.putExtra("email",email);
                //intent.putExtra("chosenArtists",chosenArtists);
                //intent.putExtra("song",song);

                startActivity(intent);
                finish();
            }
        });

    }

    private void makeUserDetails() {
        user.setUsername(editTextFirstName.getText().toString());
        Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new User.userJsonSerializer()).create();
        jsonBody = gson.toJson(user);

        jsonDetails="{\n" +
                "    \"name\":\""+userDetails[0]+"\",\n" +
                "    \"type\":\"profile\",\n" +
                "    \"createdBy\":{\n" +
                "        \"userId\":{\n" +
                "            \"domain\":\"2022b.Yaeli.Bar.Gimelshtei\",\n" +
                "            \"email\":\"avivit.yehezkel@s.afeka.ac.il\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"instanceAttributes\":{\n" +
                "        \"firstname\":\""+userDetails[1]+"\",\n" +
                "        \"lastname\":\""+userDetails[2]+"\",\n" +
                "        \"birthdate\":\""+userDetails[3]+"\",\n" +
                "        \"gender\":\""+userDetails[4]+"\",\n" +
                "        \"interestedin\":\""+userDetails[5]+"\",\n" +
                "        \"occupation\":\""+userDetails[6]+"\",\n" +
                "        \"bio\":\""+userDetails[7]+"\",\n" +
                "        \"chosenArtists\":\""+ Arrays.toString(chosenArtists) +"\",\n" +
                "        \"chosenSong\":\""+song+"\"\n" +
                "    }\n" +
                "\n" +
                "}";
    }

    private void createUserDetails(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String endpoint = "http://192.168.0.105:8085/iob/instances";
        StringRequest request = new StringRequest(Request.Method.POST, endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // on below line we are displaying a success toast message.
                        //Toast.makeText(RegistrationSongsActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                        try {
                            //response json
                            JSONObject respObj = new JSONObject(response);

                        } catch (JSONException e) {
                            Log.d("ccc","createUserDetails catch "+e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ccc","createUserDetails error "+error.toString());
                // method to handle errors.
                //TODO:tell the user if email already in the system
                //Toast.makeText(RegistrationFormForSpotifyUser.this, "instance create = " + error, Toast.LENGTH_SHORT).show();
              //  Log.d("createUserDetails",""+jsonDetails.toString());
               // Log.d("createUserDetails",""+error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return jsonDetails == null ? null : jsonDetails.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonDetails, "utf-8");
                    return null;
                }
            }
        };
        queue.add(request);
    }



    private void makeUser() {
        user = new User();
        user.setEmail(email);
        user.generateAvatar();
    }


    private void createNewUser(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String endpoint = "http://192.168.0.105:8085/iob/users";
        StringRequest request = new StringRequest(Request.Method.POST, endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // on below line we are displaying a success toast message.
                        //Toast.makeText(RegistrationSongsActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                        try {
                            //response json
                            JSONObject respObj = new JSONObject(response);
                            //Log.d("11111",""+respObj.toString());


                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Log.d("11111",""+e.toString());

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(RegistrationFormForSpotifyUser.this, "user create = " + error, Toast.LENGTH_SHORT).show();
                Log.d("11111",""+error.toString());
                Log.d("11111",""+jsonBody);

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return jsonBody == null ? null : jsonBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonBody, "utf-8");
                    return null;
                }
            }
        };
        queue.add(request);
    }

    private void getSpotifyUser() {
        String ENDPOINT = "https://api.spotify.com/v1/me";
        RequestQueue mqueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ENDPOINT, null, response -> {
            try {
                email=response.getString("email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = MSP.getMe().getString("token","");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        mqueue.add(jsonObjectRequest);


        // TODO:ADD 3 ARTISTS AND 1 SONG FROM SPOTIFY
//        ENDPOINT = "https://api.spotify.com/v1/me/top/artists";
//        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, ENDPOINT, null, response -> {
//            try {
//                email=response.getString("email");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }, new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                String token = MSP.getMe().getString("token","");
//                String auth = "Bearer " + token;
//                headers.put("Authorization", auth);
//                return headers;
//            }
//        };
//
        //mqueue.add(jsonObjectRequest2);

    }

    private void setFonts() {
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Momcake-Bold.otf");
        type2 = Typeface.createFromAsset(getAssets(),"fonts/Rounded.ttf");
        editTextFirstName.setTypeface(type2);
        editTextLastName.setTypeface(type2);
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
                        RegistrationFormForSpotifyUser.this,
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
    private void setSpinner(Spinner spn,String[] arr) {
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

    private boolean checkIfEmpty() {
        boolean isFull = false;
        if(editTextFirstName.getText().toString().equals("")){
            //Toast.makeText(this, "Please enter first name", Toast.LENGTH_SHORT).show();
        }else if(editTextLastName.getText().toString().equals("")){
            //Toast.makeText(this, "Please enter second name", Toast.LENGTH_SHORT).show();
        }else if(editTextOccupation.getText().toString().equals("")){
            //Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
        }else if(main_LBL_date.getText().toString().equals("")) {
            //Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
        }else if(editBio.getText().toString().equals("")){

        }else{
            isFull = true;
        }
        return isFull;
    }










    private void findViews() {
        editTextFirstName=findViewById(R.id.editTextFirstName);
        editTextLastName=findViewById(R.id.editTextLastName);
        editTextOccupation =findViewById(R.id.editTextoccupation);
        editBio=findViewById(R.id.editBio);

        main_SPN_gender = findViewById(R.id.main_SPN_gender);
        main_SPN_gender.setOnItemSelectedListener(this);
        main_SPN_interested = findViewById(R.id.main_SPN_interested);
        main_SPN_interested.setOnItemSelectedListener(this);



        editTextOccupation =findViewById(R.id.editTextoccupation);
        main_LBL_date= findViewById(R.id.main_LBL_date);
        Registration= findViewById(R.id.Registration);
        main_BTN_finish=findViewById(R.id.main_BTN_finish);
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