package com.example.expirytracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    FloatingActionButton add_product_btn,scan_qr_code_btn,product_form_btn;
    Boolean isTrue = true;
    CardView scan_qr_msg, form_msg;
    Dialog product_form;

    private RecyclerView productRecyclerView;
    private List<Object> viewItems = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        add_product_btn = findViewById(R.id.home_add_product_btn);
        scan_qr_code_btn = findViewById(R.id.qr_code_btn);
        product_form_btn = findViewById(R.id.form_btn);
        scan_qr_msg = findViewById(R.id.barcode_msg);
        form_msg = findViewById(R.id.form_msg);

        Animation rotate = AnimationUtils.loadAnimation(this,R.anim.rotate);
        Animation rotateBack = AnimationUtils.loadAnimation(this,R.anim.rotate_back);
        Animation fabOpen = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        Animation fabClose = AnimationUtils.loadAnimation(this,R.anim.fab_close);

        product_form = new Dialog(this);






        add_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTrue) {
                    add_product_btn.startAnimation(rotate);
                    scan_qr_code_btn.startAnimation(fabOpen);
                    product_form_btn.startAnimation(fabOpen);
                    scan_qr_code_btn.setVisibility(View.VISIBLE);
                    scan_qr_code_btn.setClickable(true);
                    scan_qr_msg.setVisibility(View.VISIBLE);
                    product_form_btn.setVisibility(View.VISIBLE);
                    product_form_btn.setClickable(true);
                    form_msg.setVisibility(View.VISIBLE);
                    isTrue = false;
                } else {
                    add_product_btn.startAnimation(rotateBack);
                    scan_qr_code_btn.startAnimation(fabClose);
                    product_form_btn.startAnimation(fabClose);
                    scan_qr_code_btn.setVisibility(View.INVISIBLE);
                    scan_qr_code_btn.setClickable(false);
                    scan_qr_msg.setVisibility(View.INVISIBLE);
                    product_form_btn.setVisibility(View.INVISIBLE);
                    product_form_btn.setClickable(false);
                    form_msg.setVisibility(View.INVISIBLE);
                    isTrue = true;
                }
            }
        });

        product_form_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_form.setContentView(R.layout.pop_up_add_product_form);

                ImageView closeBtn = (ImageView) product_form.findViewById(R.id.close_btn);
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        product_form.dismiss();
                    }
                });

                product_form.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                product_form.show();

                EditText product_name_field = (EditText) product_form.findViewById(R.id.add_product_name);
                product_name_field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus){
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(product_name_field.getWindowToken(), 0);
                        }
                    }
                });




                EditText product_expiry_date_field = (EditText) product_form.findViewById(R.id.add_product_expiry_date);
                product_expiry_date_field.setInputType(InputType.TYPE_NULL);
                product_expiry_date_field.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = Calendar.getInstance();
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int month = calendar.get(Calendar.MONTH);
                        int year = calendar.get(Calendar.YEAR);

                        DatePickerDialog picker = new DatePickerDialog(HomeActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        product_expiry_date_field.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    }
                                }, year, month, day);

                        picker.show();

                    }
                });


            }
        });

        scan_qr_code_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(HomeActivity.this);
                intentIntegrator.setPrompt("Scan a barcode or QRCode");
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setCaptureActivity(CaptureAct.class);
                intentIntegrator.initiateScan();
            }
        });

         productRecyclerView = (RecyclerView) findViewById(R.id.productListRecyclerView);
         layoutManager = new LinearLayoutManager(this);
         productRecyclerView.setLayoutManager(layoutManager);

         mAdapter = new RecyclerAdapter(this, viewItems);
         productRecyclerView.setAdapter(mAdapter);

         addItemsFromJSON();


    }

    private void addItemsFromJSON() {
        FileUtility fileUtility = new FileUtility();
        try {
            String jsonDataString = fileUtility.readJSONDataFromFile(getResources().openRawResource(R.raw.products));
            JSONArray jsonArray = new JSONArray(jsonDataString);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject itemObject = jsonArray.getJSONObject(i);
                String name = itemObject.getString("name");
                String expiry_date = itemObject.getString("expiry_date");

                Products products = new Products(name,expiry_date);
                viewItems.add(products);
            }
        } catch (JSONException e) {
            Log.d("HomeActivity","addItemsFromJSON: ",e);
        } catch (IOException e) {
            Log.d("HomeActivity","addItemsFromJSON: ",e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getBaseContext(),"Cancelled",Toast.LENGTH_LONG).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

                builder.setTitle("Result");
                builder.setMessage(result.getContents());
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}