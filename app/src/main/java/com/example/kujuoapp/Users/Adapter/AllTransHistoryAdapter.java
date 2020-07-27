package com.example.kujuoapp.Users.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.DataClass.AllTrans;
import com.example.kujuoapp.Users.DataClass.TransHistoryData;
import com.example.kujuoapp.Users.Feautures.ViewAll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AllTransHistoryAdapter extends RecyclerView.Adapter<AllTransHistoryAdapter.MyviewHolder>{

    Context context;
    List<AllTrans> data;
    public static String rec_id,rec_same,tran_amount,fees,total_amount;
    View view;
   public String  path;
   Canvas canvas;
    Bitmap b;
    public AllTransHistoryAdapter(Context context, List<AllTrans> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_recent_trans_card,parent,false);
        return new AllTransHistoryAdapter.MyviewHolder(view);
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
                .load(data.get(position).getProfile())
                .placeholder(R.drawable.cap_boy)
                .error(R.drawable.cap_boy).circleCrop()
                .into(holder.profile);
        holder.name.setText(data.get(position).getName());
        holder.trans_amount.setText("-$ "+data.get(position).getTrasaction());

       String lastFourDigits = "*****"+data.get(position).getContact().substring(data.get(position).getContact().length() - 4);

        holder.contact.setText(lastFourDigits);

        holder.datetime.setText(parseDateToddMMyyyy(data.get(position).getDate()));
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

    public void tran_popup(int pos)
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
        final LinearLayout share =dialog.findViewById(R.id.share);
        final LinearLayout save =dialog.findViewById(R.id.save);

        trans_id.setText("#ID"+data.get(pos).getId());

        datetime.setText(parseDateToddMMyyyy(data.get(pos).getDate()));

        rec_id.setText("#ID"+data.get(pos).getRecid());
        rec_name.setText(data.get(pos).getName());

        sent_id.setText("#ID"+wallet_id);
        sent_name.setText(user_name);

        fees_charge.setText("$"+data.get(pos).getCharges());

        trans_amount.setText("$"+data.get(pos).getTrasaction());
        totalamount.setText("$"+data.get(pos).getSend_amount());

        transatype.setText(data.get(pos).getTransaction_type());

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               takeScreenShot(dialog,image);
            }
        });
        dialog.show();

    }

    private void takeScreenShot(Dialog dialog,ImageView cr) {

        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Kujuo/");

        if (!folder.exists()) {
            boolean success = folder.mkdir();
        }

        path = folder.getAbsolutePath();
        String signature_pdf_="nx", signature_img_="xxxx";
        path = path + "/" + signature_pdf_ + System.currentTimeMillis() + ".pdf";// path where pdf will be stored

        View u = dialog.findViewById(R.id.scroll);
        NestedScrollView z = (NestedScrollView) dialog.findViewById(R.id.scroll); // parent view
       int totalHeight = z.getChildAt(0).getHeight();// parent view height
        int totalWidth = z.getChildAt(0).getWidth();// parent view width

        //Save bitmap to  below path
        String extr = Environment.getExternalStorageDirectory() + "/Signature/";
        File file = new File(extr);
        if (!file.exists())
            file.mkdir();
        String fileName = signature_img_ + ".jpg";
        File myPath = new File(extr, fileName);
        String imagesUri = myPath.getPath();
        FileOutputStream fos = null;

         b = getBitmapFromView(u, totalHeight, totalWidth);

        cr.setImageBitmap(b);
        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            createPdf();// create pdf after creating bitmap and saving}
        }
    }

 /*   private void takeScreenShot1(Dialog dialog,ImageView cr)
    {
        View u = dialog.findViewById(R.id.scroll);

        NestedScrollView z = dialog.findViewById(R.id.scroll);
        int totalHeight = z.getChildAt(0).getHeight();
        int totalWidth = z.getChildAt(0).getWidth();

        Bitmap b = getBitmapFromView(u,totalHeight,totalWidth);

        cr.setImageBitmap(b);
        //Save bitmap
        String extr = Environment.getExternalStorageDirectory()+"/Folder/";
        String fileName = "report.jpg";
        File myPath = new File(extr, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(context.getContentResolver(), b, "Screen", "screen");
        }catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
*/
 @RequiresApi(api = Build.VERSION_CODES.KITKAT)
 private void createPdf() {

     PdfDocument document = null;

     if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
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
         Toast.makeText(context, path, Toast.LENGTH_LONG).show();

     } catch (IOException e) {
         e.printStackTrace();
         Toast.makeText(context, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
     }

     // close the document
     document.close();

     //openPdf(path);// You can open pdf after complete
     }

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
}
