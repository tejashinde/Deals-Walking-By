package com.example.tejas.dealswalkingby;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class AreaSearchMain extends AppCompatActivity {

    EditText subzone = null;
    String subzonestring = "";
    Spinner mSpinner;
    int categorySelected;
    String categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_search_main);
        subzone = findViewById(R.id.zomato_subzone);
        mSpinner = (Spinner) findViewById(R.id.spinner_categories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_id_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                categorySelected = (int) parent.getItemIdAtPosition(pos);

                switch (categorySelected)
                {
                    case 0:
                        categoryId = "1";
                        break;

                    case 1:
                        categoryId = "2";
                        break;

                    case 2:
                        categoryId = "3";
                        break;

                    case 3:
                        categoryId = "4";
                        break;

                    case 4:
                        categoryId = "5";
                        break;

                    case 5:
                        categoryId = "6";
                        break;

                    case 6:
                        categoryId = "7";
                        break;

                    case 7:
                        categoryId = "8";
                        break;

                    case 8:
                        categoryId = "9";
                        break;

                    case 9:
                        categoryId = "10";
                        break;

                    case 10:
                        categoryId = "11";
                        break;

                    case 11:
                        categoryId = "12";
                        break;

                    case 12:
                        categoryId = "13";
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        }




    public void GoToDetails(View view) {
        Intent intent = new Intent(AreaSearchMain.this,JsonDataIntoRecyclerView.class);
        subzonestring = subzone.getText().toString();
        intent.putExtra("subzone", subzonestring);
        intent.putExtra("categoryID",categoryId);
        resettext(subzone);
        subzonestring = "";
        categoryId="";
        startActivity(intent);
    }

    public void resettext(EditText mEditText){
        mEditText.setText(null);
    }
}
