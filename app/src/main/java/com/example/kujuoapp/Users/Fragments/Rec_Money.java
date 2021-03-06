package com.example.kujuoapp.Users.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.Adapter.AllTransHistoryAdapter;
import com.example.kujuoapp.Users.Adapter.AllTransHistoryAdapterNav;
import com.example.kujuoapp.Users.BaseClass;
import com.example.kujuoapp.Users.DataClass.AllTrans;
import com.example.kujuoapp.Users.Feautures.Full_trans_history;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Rec_Money extends Fragment {


    public Rec_Money() {
        // Required empty public constructor
    }

    View view;

    List<AllTrans> data=new ArrayList<>();
    RecyclerView recyclerView;
    TextView no;
    AllTransHistoryAdapterNav adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_rec__money, container, false);


        no=view.findViewById(R.id.nohistory);

    recyclerView();

        Full_trans_history.serach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!(data.isEmpty())){
                    filter(s.toString());
                }
            }


        });

        return view;
    }

    private void recyclerView()
    {
        recyclerView=view.findViewById(R.id.transhistory);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        if(BaseClass.isNetworkConnected(getContext()))
            fetchdata();
        else
            BaseClass.toast(getContext(),"Check Your Internet Connection");


    }

    private void fetchdata() {
        BaseClass.progress(getContext());

    //    BaseClass.progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"recieved_trans_history.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        BaseClass.progressDialog.dismiss();
             //           Toast.makeText(getContext(),ServerResponse.toString(),Toast.LENGTH_SHORT).show();
                        if(ServerResponse.trim().equals("0")){
                            no.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(),"Not Found",Toast.LENGTH_SHORT).show();
                        }
                        else{

                            try {
                                JSONArray jsonArray = new JSONArray(ServerResponse);
                                if (jsonArray.length() > 0) {
                                    for (int j = 0; j < jsonArray.length(); j++) {

                                        JSONObject info = jsonArray.getJSONObject(j);
                                        data.add(new AllTrans(info.getString("trans_id"),
                                                info.getString("rec_pic"),info.getString("rec_name"),
                                                info.getString("rec_phone"),info.getString("date_time"),
                                                info.getString("date_time"),info.getString("send_amount")
                                                ,info.getString("sender_message")
                                                ,info.getString("transaction_charges"),
                                                info.getString("transaction_type"),
                                                info.getString("send_amount"),
                                                info.getString("receiver_id")));

                                    }// levelAdapter.notifyDataSetChanged();
                                    adapter=new AllTransHistoryAdapterNav(getContext(),data);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        BaseClass.progressDialog.dismiss();
                        Toast.makeText(getContext(),volleyError.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getContext());

                String userid=preferences1.getString("user_id", "");
                String wallet_id=preferences1.getString("wallet_id", "");
                String user_phoneno=preferences1.getString("user_phoneno", "");
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                if(userid!=null && wallet_id!=null) {
                    params.put("userid", userid);
                    params.put("wallet_id", wallet_id);
                    params.put("user_phoneno", user_phoneno);
                }

                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);

    }
    private void filter(String text) {

        if (text != "") {

            ArrayList<AllTrans> filteredList = new ArrayList<>();

            for (AllTrans item : data) {

                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            if(filteredList !=null)
            {adapter.filterList(filteredList);}
        }
        else{
            Toast.makeText(getContext(),"No Teacher",Toast.LENGTH_SHORT).show();
        }
    }
}