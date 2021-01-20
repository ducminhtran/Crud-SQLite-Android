package com.example.addressmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddAddressActivity extends AppCompatActivity {

    Spinner spinner;
    RadioButton rdDomestic, rdForeign;
    EditText edtZipCode;
    Button btnSave;
    AddressHelper helper;
    String selected = "";

    String Name, Address, Type, ZipCode;
    Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        helper = new AddressHelper(this);

        spinner = findViewById(R.id.spinner_country);
        rdDomestic = findViewById(R.id.rdDomestic);
        rdForeign = findViewById(R.id.rdForeign);
        edtZipCode = findViewById(R.id.edtZipCode);


        btnSave = findViewById(R.id.btnSave);

        TextView title = (TextView) findViewById(R.id.title);
        EditText name = (EditText) findViewById(R.id.edtName);
        EditText address = (EditText) findViewById(R.id.edtAddress);
        EditText zip = (EditText) findViewById(R.id.edtZipCode);
        RadioGroup types = (RadioGroup) findViewById(R.id.types);


        flag = getIntent().getBooleanExtra("flag", false);
        if (flag == true) {

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Foreign, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            title.setText("Edit address");

            Name = getIntent().getStringExtra("name");
            name.setText(Name);
            Address = getIntent().getStringExtra("address");
            address.setText(Address);
            Type = getIntent().getStringExtra("type");
            if (Type.equals("Domestic")) {
                types.check(R.id.rdDomestic);
            } else {
                types.check(R.id.rdForeign);
                if (Type.equals("0")){
                    spinner.getItemAtPosition(0);
                }else if (Type.equals("1")){
                    spinner.getItemAtPosition(1);
                }else if  (Type.equals("2")){
                    spinner.getItemAtPosition(2);
                }
            }



            ZipCode = getIntent().getStringExtra("zip");
            zip.setText(ZipCode);


            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (types.getCheckedRadioButtonId()) {
                        case R.id.rdDomestic:
                            Type = "Domestic";
                            break;
                        case R.id.rdForeign:
                          Type = selected;
                            break;
                        default:
                            break;
                    }

                    Boolean check = helper.update(name.getText().toString(), address.getText().toString(), Type, zip.getText().toString());

                    if (check == true) {
                        Toast.makeText(AddAddressActivity.this, "OK", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AddAddressActivity.this, "Fail", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Address addresses = new Address();


                    addresses.setName(name.getText().toString());
                    addresses.setAddress(address.getText().toString());
                    addresses.setZip(zip.getText().toString());

                    switch (types.getCheckedRadioButtonId()) {
                        case R.id.rdDomestic:
                            addresses.setType("Domestic");
                            break;
                        case R.id.rdForeign:
                            addresses.setType(selected);
                            break;
                        default:
                            break;
                    }

                    Boolean check = helper.insert(addresses.getName(), addresses.getAddress(), addresses.getType(), addresses.getZip());

                    if (check == true) {
                        Toast.makeText(AddAddressActivity.this, "OK", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AddAddressActivity.this, "Fail", Toast.LENGTH_LONG).show();
                    }

//                Intent intent = new Intent(AddAddressActivity.this, MainActivity.class);
//                startActivity(intent);
                }
            });
        }


    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdDomestic:
                if (checked) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Domestic, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    final String domesticZipCodeArray[] = getResources().getStringArray(R.array.Domestic_ZipCode);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            edtZipCode.setText(String.valueOf(domesticZipCodeArray[position]));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                break;
            case R.id.rdForeign:
                if (checked) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Foreign, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    final String foreignZipCodeArray[] = getResources().getStringArray(R.array.Foreign_ZipCode);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            edtZipCode.setText(String.valueOf(foreignZipCodeArray[position]));
                            selected = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();

    }


}