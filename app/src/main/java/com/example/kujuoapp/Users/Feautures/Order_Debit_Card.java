package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kujuoapp.Profile.ChangeProfileActivity;
import com.example.kujuoapp.R;

import java.io.IOException;

public class Order_Debit_Card extends AppCompatActivity {

    ImageView take_nis_image;
    TextView required_warn;
    Uri selectedImage;
    Bitmap selct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__debit__card);

        take_nis_image = findViewById(R.id.take_nis_image);
        required_warn = findViewById(R.id.req_text);
        imageTaking();
        statusbar();
        back();
    }

    private void imageTaking() {
    take_nis_image.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectImage();
        }
    });
    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Order_Debit_Card.this);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1 ){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
               // take_nis_image=ImageToString(bitmap);
                //updatedata(image,"image");
                // changeProfile.setImageBitmap(bitmap);
                Glide.with(getApplicationContext()).load(bitmap)
                        .fitCenter()
                        .placeholder(R.drawable.ic_loading)
                        .into(take_nis_image);

                required_warn.setVisibility(View.INVISIBLE);

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
                    //image=ImageToString(selct);
                   // updatedata(image,"image");
                    // changeProfile.setImageBitmap(selct);
                    Glide.with(getApplicationContext())
                            .load(selct)
                            .placeholder(R.drawable.ic_loading)
                            .fitCenter()
                            .into(take_nis_image);
                    required_warn.setVisibility(View.INVISIBLE);
                    // image=ImageToString(selct);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Log.w("path of image from gallery......******************.........", picturePath+"");
                //changeProfile.setImageBitmap(thumbnail);
                Glide.with(getApplicationContext())
                        .load(thumbnail)
                        .fitCenter()
                        .placeholder(R.drawable.ic_loading)
                        .into(take_nis_image);

                required_warn.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void statusbar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = Order_Debit_Card.this.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);

        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    private void back()
    {
        ImageView imageView=findViewById(R.id.tback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}