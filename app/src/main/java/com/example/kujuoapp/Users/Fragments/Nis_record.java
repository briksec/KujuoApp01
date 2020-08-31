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
import com.example.kujuoapp.Users.Adapter.AllTransHistoryAdapterNav;
import com.example.kujuoapp.Users.Adapter.Nis_History_Adapter;
import com.example.kujuoapp.Users.BaseClass;
import com.example.kujuoapp.Users.DataClass.AllTrans;
import com.example.kujuoapp.Users.DataClass.Nis_data_recod;
import com.example.kujuoapp.Users.Feautures.Full_trans_history;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Nis_record extends Fragment {



    public Nis_record() {
        // Required empty public constructor
    }

    View view;

    List<Nis_data_recod> data=new ArrayList<>();
    RecyclerView recyclerView;
    TextView no;
    Nis_History_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_nis_record, container, false);

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"fetch_nis_record.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        BaseClass.progressDialog.dismiss();
                        Toast.makeText(getContext(),ServerResponse.toString(),Toast.LENGTH_SHORT).show();
                        if(ServerResponse.trim().equals("0")){
                            no.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(),"Not Found",Toast.LENGTH_SHORT).show();
                        }
                        else{

                            try {
                                JSONArray jsonArray = new JSONArray(ServerResponse);
                                if (jsonArray.length() > 0) {
                                    for (int j = 0; j < jsonArray.length(); j++) {
                           /*            	$temp=[
					'trans_nis_id'=>$row['trans_nis_id'],
					'send_amount'=>$row['send_amount'],
					'transaction_charges'=>$row['transaction_charges'],
					'receiver_phoneno'=>$row['receiver_phoneno'],
					'receiver_nis'=>$row['receiver_nis'],
					'receiver_code'=>$row['receiver_code'],
					'receive_status'=>$row['receive_status'],
					'datetime'=>$row['datetime'],
						];
						];*/
                                        JSONObject info = jsonArray.getJSONObject(j);
                                        data.add(new Nis_data_recod(
                                                info.getString("trans_nis_id"),
                                                info.getString("send_amount"),
                                                info.getString("transaction_charges"),
                                                info.getString("receiver_phoneno")
                                                ,info.getString("receiver_nis")
                                                ,info.getString("receiver_code")
                                                ,info.getString("receive_status")
                                                ,info.getString("datetime")
                                        ));

                                    }// levelAdapter.notifyDataSetChanged();
                                    adapter=new Nis_History_Adapter(getContext(),data);
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

            ArrayList<Nis_data_recod> filteredList = new ArrayList<>();

            for (Nis_data_recod item : data) {

                if (item.getReceiver_nis().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            if(filteredList !=null)
            {
                adapter.filterList(filteredList);}
        }
        else{
            Toast.makeText(getContext(),"No Teacher",Toast.LENGTH_SHORT).show();
        }
    }
}