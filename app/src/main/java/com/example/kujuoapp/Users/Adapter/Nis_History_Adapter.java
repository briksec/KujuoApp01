package com.example.kujuoapp.Users.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.BaseClass;
import com.example.kujuoapp.Users.DataClass.AllTrans;
import com.example.kujuoapp.Users.DataClass.Nis_data_recod;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Nis_History_Adapter extends RecyclerView.Adapter<Nis_History_Adapter.MyviewHolder>{

    Context context;
    List<Nis_data_recod> data;


    public static String rec_id,rec_same,tran_amount,fees,total_amount;
    View view;
   public String  path;
   Canvas canvas;
    Bitmap b;
    public Nis_History_Adapter(Context context, List<Nis_data_recod> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_recent_trans_card,parent,false);
        return new Nis_History_Adapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, final int position)
    {
        if(position%2==1)
        {
            holder.trans_amount.setTextColor(Color.BLUE);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFF1F0"));
        }
        Glide.with(context)
                .load("http://app.briksec.com/kujuoapp/promos/nis.jpg")
                .placeholder(R.drawable.cap_boy)
                .error(R.drawable.cap_boy).circleCrop()
                .into(holder.profile);
        holder.name.setText(data.get(position).getReceiver_nis());
        holder.trans_amount.setText("-$ "+data.get(position).getSend_amount());

       String lastFourDigits = "*****"+data.get(position).getReceiver_code().substring(data.get(position).getReceiver_code().length() - 4);

        holder.contact.setText(lastFourDigits);

        holder.datetime.setText(parseDateToddMMyyyy(data.get(position).getDatetime()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cardView.setBackgroundColor(Color.parseColor("#D3D3D3"));
             tran_popup(position);
            }
        });
    }
    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "MMM-dd-yy h:mm a";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public void tran_popup(final int pos)
    {
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.transaction_record_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(context);


        String   user_name=preferences1.getString("user_name", "");
        String  wallet_id=preferences1.getString("wallet_id", "");


        final TextView trans_id =dialog.findViewById(R.id.trans_id);
        final TextView datetime =dialog.findViewById(R.id.datetime);
        final TextView rec_id =dialog.findViewById(R.id.rec_id);
        final TextView rec_name =dialog.findViewById(R.id.rec_name);
        final TextView sent_name =dialog.findViewById(R.id.sent_name);
        final TextView sent_id =dialog.findViewById(R.id.sent_id);
        final TextView fees_charge =dialog.findViewById(R.id.fees_charge);
        final TextView totalamount =dialog.findViewById(R.id.totalamount);
        final TextView trans_amount =dialog.findViewById(R.id.trans_amount);
        final TextView transatype =dialog.findViewById(R.id.transatype);
        final ImageView cross =dialog.findViewById(R.id.cros);
        final ImageView image =dialog.findViewById(R.id.my);
        final ImageView saveicon =dialog.findViewById(R.id.saveicon);
        final ImageView shareicon =dialog.findViewById(R.id.shareicon);
        final LinearLayout share =dialog.findViewById(R.id.share);
        final LinearLayout save =dialog.findViewById(R.id.save);
        final  TextView typehead=dialog.findViewById(R.id.typehead);

        trans_id.setText("#ID"+data.get(pos).getTrans_nis_id());

        datetime.setText(parseDateToddMMyyyy(data.get(pos).getDatetime()));

        rec_id.setText("#ID"+data.get(pos).getReceiver_phoneno());
        rec_name.setText(data.get(pos).getReceiver_nis());

        sent_id.setText("#ID"+wallet_id);
        sent_name.setText(user_name);

        fees_charge.setText("$"+data.get(pos).getTransaction_charges());

        trans_amount.setText("$"+data.get(pos).getSend_amount());
        totalamount.setText("$"+(Integer.parseInt(data.get(pos).getSend_amount())+Integer.parseInt(data.get(pos).getTransaction_charges())));

        typehead.setText("Receiving Code");
        transatype.setText(data.get(pos).getReceiver_code());

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveicon.setImageResource(R.drawable.blue_saved);
                takeScreenShot(dialog,image,data.get(pos).getReceiver_nis(),parseDateToddMMyyyy(data.get(pos).getDatetime()),true);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareicon.setImageResource(R.drawable.blueshare);
                takeScreenShot(dialog,image,data.get(pos).getReceiver_nis(),parseDateToddMMyyyy(data.get(pos).getDatetime()),false);
            }
        });
        dialog.show();

    }

    private void takeScreenShot(Dialog dialog,ImageView cr,String name,String datetime,boolean cond) {

        if(cond==true)
        {
            File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Kujuo_App ScreenShot/");

            if (!folder.exists()) {
                boolean success = folder.mkdir();
            }

            path = folder.getAbsolutePath();
            String signature_pdf_=name, signature_img_=datetime;
            path = path + "/" + signature_pdf_ + datetime + ".pdf";// path where pdf will be stored

            View u = dialog.findViewById(R.id.scroll);
            NestedScrollView z = (NestedScrollView) dialog.findViewById(R.id.scroll); // parent view
            int totalHeight = z.getChildAt(0).getHeight();// parent view height
            int totalWidth = z.getChildAt(0).getWidth();// parent view width

            //Save bitmap to  below path
            String extr = Environment.getExternalStorageDirectory() + "/Kujuo_App Receipts PDF/";
            File file = new File(extr);
            if (!file.exists())
                file.mkdir();
            String fileName = signature_pdf_+signature_img_ + ".jpg";
            File myPath = new File(extr, fileName);
            String imagesUri = myPath.getPath();
            FileOutputStream fos = null;

            b = getBitmapFromView(u, totalHeight, totalWidth);

            // cr.setImageBitmap(b);
            try {
                fos = new FileOutputStream(myPath);
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                createPdf();// create pdf after creating bitmap and saving}
            }
            else
                {
                    BaseClass.toast(context,"Your OS Version is Old");
                }
        }
        else
            {
                File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Kujuo_App ScreenShot/");

                if (!folder.exists()) {
                    boolean success = folder.mkdir();
                }

                path = folder.getAbsolutePath();
                String signature_pdf_=name, signature_img_=datetime;
                path = path + "/" + signature_pdf_ + datetime + ".pdf";// path where pdf will be stored

                View u = dialog.findViewById(R.id.scroll);
                NestedScrollView z = (NestedScrollView) dialog.findViewById(R.id.scroll); // parent view
                int totalHeight = z.getChildAt(0).getHeight();// parent view height
                int totalWidth = z.getChildAt(0).getWidth();// parent view width

                //Save bitmap to  below path
                String extr = Environment.getExternalStorageDirectory() + "/Kujuo_App Receipts PDF/";
                File file = new File(extr);
                if (!file.exists())
                    file.mkdir();
                String fileName = signature_pdf_+signature_img_ + ".jpg";
                File myPath = new File(extr, fileName);
                String imagesUri = myPath.getPath();
                FileOutputStream fos = null;

                b = getBitmapFromView(u, totalHeight, totalWidth);

                shareBitmap(b);
                // cr.setImageBitmap(b);
                try {
                    fos = new FileOutputStream(myPath);
                    b.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf() {

        PdfDocument document = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            document = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(b.getWidth(), b.getHeight(), 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);

            canvas= page.getCanvas();


            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#ffffff"));
            canvas.drawPaint(paint);


            Bitmap bitmap = Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), true);

            paint.setColor(Color.BLUE);
            canvas.drawBitmap(bitmap, 0, 0, null);
            document.finishPage(page);
            File filePath = new File(path);
            try {
                document.writeTo(new FileOutputStream(filePath));
                Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            }

            // close the document
            document.close();

            //openPdf(path);// You can open pdf after complete
        }

    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void shareBitmap(@NonNull Bitmap bitmap)
    {
        //---Save bitmap to external cache directory---//
        //get cache directory
        File cachePath = new File(context.getExternalCacheDir(), "my_images/");
        cachePath.mkdirs();

        //create png file
        File file = new File(cachePath, "Image_123.png");
        FileOutputStream fileOutputStream;
        try
        {
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        //---Share File---//
        //get file uri
        Uri myImageFileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);

        //create a intent
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_STREAM, myImageFileUri);
        intent.setType("image/png");
        context.startActivity(Intent.createChooser(intent, "Share with"));
    }

    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth,totalHeight , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView profile;
        TextView name,contact,trans_amount,datetime;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.transbody);
            profile=itemView.findViewById(R.id.trans_profile);
            name=itemView.findViewById(R.id.transname);
            contact=itemView.findViewById(R.id.transno);
            trans_amount=itemView.findViewById(R.id.trans_amount);
            datetime=itemView.findViewById(R.id.datetime);
        }
    }

    public void filterList(ArrayList<Nis_data_recod> filteredList) {
        data=new ArrayList<>();
        data.addAll(filteredList);

        notifyDataSetChanged();
    }
}
