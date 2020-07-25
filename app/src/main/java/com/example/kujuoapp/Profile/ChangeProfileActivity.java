package com.example.kujuoapp.Profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.BaseClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.kujuoapp.CommanActivity.SplashScreen1.RequestPermissionCode;

public class ChangeProfileActivity extends AppCompatActivity {

    ImageView arrowright,arrowbottom;
    EditText phoneEdit, nameEdit;
    TextView usersetdname,usersetdphone;
    LinearLayout fnameline,phoneline;
    ExpandGetterSetter eGSetter;
    Button save_changes_btn;
    ImageView back,changeProfile;
    public Uri uri;
    Bitmap selct,cap;
    Uri selectedImage;
    String image;
    public static String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        getSupportActionBar().hide();
        arrowright = findViewById(R.id.fname_arrow);
        arrowbottom = findViewById(R.id.phno_arrow);
        usersetdname = findViewById(R.id.my_username);
        fnameline = findViewById(R.id.fname_line);
        phoneline = findViewById(R.id.phoneline);
        usersetdphone = findViewById(R.id.my_userphone);
        phoneEdit = findViewById(R.id.ph_edit);
        nameEdit = findViewById(R.id.fullname_edit);
        save_changes_btn = findViewById(R.id.save_changes);
        changeProfile = findViewById(R.id.change_profile);
        back = findViewById(R.id.cp_backz);


        fetchUData();
        eGSetter = new ExpandGetterSetter();
        eGSetter.setCheckname(true);
        eGSetter.setCheckphone(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment.fetchUData();
                finish();
            }
        });
        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              type = "image";
                save_changes_btn.setEnabled(true);
                if(checkPermission()) {
                    selectImage();
                } else {
                    requestPermission();
                }
            }
        });
        fnameline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (eGSetter.isCheckname()){
                    arrowright.setBackgroundResource(R.drawable.ic_arrow_down);
                    usersetdname.setVisibility(View.GONE);
                    nameEdit.setVisibility(View.VISIBLE);
                    save_changes_btn.setEnabled(true);
                    type = "name";
                    eGSetter.setCheckname(false);
                }else {
                    arrowright.setBackgroundResource(R.drawable.ic_right_arrow);
                    usersetdname.setVisibility(View.VISIBLE);
                    nameEdit.setVisibility(View.GONE);
                    eGSetter.setCheckname(true);
                }

            }
        });

        phoneline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eGSetter.isCheckphone()){
                    arrowbottom.setBackgroundResource(R.drawable.ic_arrow_down);
                    usersetdphone.setVisibility(View.GONE);
                    phoneEdit.setVisibility(View.VISIBLE);
                    save_changes_btn.setEnabled(true);
                    eGSetter.setCheckphone(false);
                    type = "phone";
                }else {
                    arrowbottom.setBackgroundResource(R.drawable.ic_right_arrow);
                    usersetdphone.setVisibility(View.VISIBLE);
                    phoneEdit.setVisibility(View.GONE);
                    eGSetter.setCheckphone(true);
                }

            }
        });

        save_changes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneEdit.getText().toString().isEmpty() || nameEdit.getText().toString().isEmpty()){
                    return;
                }else {
                    updateUData(nameEdit.getText().toString(),phoneEdit.getText().toString());
                    fetchUData();
                }
            }
        });
    }

    private void updateUData(final String name, final String phone) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"update_profile_text.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        //progress_spinner.dismiss();
                         Toast.makeText(getApplicationContext(),ServerResponse.toString(),Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(getApplicationContext(),"Check Your Internet Connection", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                String userid=preferences1.getString("user_id", "");
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                params.put("userid", userid);
                params.put("username", name);
                params.put("phone", phone);
                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);
    }


    private void fetchUData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"fetch_profile_data1.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        //progress_spinner.dismiss();
                     //    Toast.makeText(getApplicationContext(),ServerResponse.toString(),Toast.LENGTH_SHORT).show();
                        if(ServerResponse.trim().equals("0")){
                            Toast.makeText(getApplicationContext(),"Not Found",Toast.LENGTH_SHORT).show();
                        }


                        try {
                            JSONArray jsonArray = new JSONArray(ServerResponse);
                            if (jsonArray.length() > 0) {
                                for (int j = 0; j < jsonArray.length(); j++) {

                                    JSONObject info = jsonArray.getJSONObject(j);
                                    usersetdname.setText(info.getString("user_name"));
                                    usersetdphone.setText(info.getString("user_phoneno"));
                                    Glide.with(getApplicationContext())
                                            .load(info.getString("user_profile"))
                                            .placeholder(R.drawable.ic_loading)
                                            .circleCrop()
                                            .into(changeProfile);
                                    nameEdit.setText(info.getString("user_name"));
                                    phoneEdit.setText(info.getString("user_phoneno"));

                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(getApplicationContext(),"Check Your Internet Connection", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                String userid=preferences1.getString("user_id", "");
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid", userid);
                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);

    }


    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangeProfileActivity.this);
        builder.setTitle("Upload Image!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(iCamera.resolveActivity(getApplicationContext().getPackageManager()) != null){
                        startActivityForResult(iCamera,1);
                    }
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void updatedata(final String updatefield, final String type) {
        BaseClass.progress(ChangeProfileActivity.this);
        BaseClass.progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"update_profile_data.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Toast.makeText(getApplicationContext(), ServerResponse.trim().toString(),  Toast.LENGTH_SHORT).show();
                        // Hiding the progress dialog after all task complete.


                             BaseClass.toast(getApplicationContext(), ""+ServerResponse);
                                BaseClass.progressDialog.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        BaseClass.progressDialog.hide();
                         Toast.makeText(getApplicationContext(),volleyError.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String userid=preferences1.getString("user_id", "");
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                params.put("userid", userid);
                params.put("updatefields", updatefield);
                params.put("type",type);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);

    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(ChangeProfileActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA ,READ_EXTERNAL_STORAGE}, RequestPermissionCode);
    }
    private  String ImageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] imgByte=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }
    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1 ){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                image=ImageToString(bitmap);
                updatedata(image,"image");
               // changeProfile.setImageBitmap(bitmap);
                Glide.with(getApplicationContext()).load(bitmap)
                        .circleCrop()
                        .placeholder(R.drawable.ic_loading)
                        .into(changeProfile);

            } else if (requestCode == 2) {
                selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getApplicationContext().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                try {
                    selct = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                    image=ImageToString(selct);
                    updatedata(image,"image");
                   // changeProfile.setImageBitmap(selct);
                    Glide.with(getApplicationContext())
                            .load(selct)
                            .placeholder(R.drawable.ic_loading)
                            .circleCrop()
                            .into(changeProfile);

                    // image=ImageToString(selct);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Log.w("path of image from gallery......******************.........", picturePath+"");
                //changeProfile.setImageBitmap(thumbnail);
                Glide.with(getApplicationContext())
                        .load(thumbnail)
                        .circleCrop()
                        .placeholder(R.drawable.ic_loading)
                        .into(changeProfile);
            }
        }
    }
    @Override
    public void onBackPressed() {

       ProfileFragment.fetchUData();
        finish();
    }
}