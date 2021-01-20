package com.example.addressmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Address> addressList = new ArrayList<Address>();
    Cursor curAddress = null;
    AddressAdapter adapter = null;
    AddressHelper helper;
    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new AddressHelper(this);

        list = (ListView) findViewById(R.id.address);
        list.setOnItemClickListener(onListClick);
        list.setOnItemLongClickListener(onListLongClick);

        curAddress = helper.getAll();
        startManagingCursor(curAddress);
        adapter = new AddressAdapter(curAddress);
        list.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.insert) {
            Intent intent = new Intent(MainActivity.this, AddAddressActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    class AddressAdapter extends CursorAdapter {


        public AddressAdapter(Cursor c) {
            super(MainActivity.this, c);
        }

        public AddressAdapter(Context context, Cursor c) {
            super(context, c);
            // TODO Auto-generated constructor stub
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, viewGroup, false);
            return row;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            View row = view;

            ((TextView) row.findViewById(R.id.name)).setText(cursor.getPosition()+1+"");
            ((TextView) row.findViewById(R.id.address)).setText(helper.getAddress(cursor) + helper.getZip(cursor));
            ImageView icon = (ImageView) row.findViewById(R.id.icon);
            String type = helper.getType(cursor);
            if (type.equals("Domestic"))
                icon.setImageResource(R.drawable.vietnam);
            else if (type.equals("USA"))
                icon.setImageResource(R.drawable.usa);
            else if (type.equals("Korean"))
                icon.setImageResource(R.drawable.southkorea);
            else
                icon.setImageResource(R.drawable.japan);
        }
    }

    private final AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this, AddAddressActivity.class);

            curAddress.moveToPosition(position);

            intent.putExtra("flag",true);
            intent.putExtra("name",helper.getName(curAddress));
            intent.putExtra("address",helper.getAddress(curAddress));
            intent.putExtra("type",helper.getType(curAddress));
            intent.putExtra("zip",helper.getZip(curAddress));

            startActivity(intent);
        }
    };

    private final AdapterView.OnItemLongClickListener onListLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            curAddress.moveToPosition(i);

            String name = helper.getName(curAddress);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Confirm delete address");
            builder.setMessage("Are you sure you want to delete this address?");
            builder.setIcon(android.R.drawable.ic_delete);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Boolean check = helper.delete(name);

                    if (check == true) {
                        Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_LONG).show();
                        restart(MainActivity.this);
                    } else {
                        Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_LONG).show();
                    }
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.create().show();
            return true;
        }
    };

    void restart(AppCompatActivity appCompatActivity){
        appCompatActivity.recreate();
    }

}