package com.example.mad_tute4;

import androidx.appcompat.app.AppCompatActivity;
import Database.DBHandler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnSelectAll, btnAdd, btnSignIn, btnDelete, btnUpdate;

    DBHandler database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = findViewById(R.id.Username);
        txtPassword = findViewById(R.id.Password);

        btnAdd = findViewById(R.id.btnAdd);
        btnSelectAll = findViewById(R.id.btnSelectAll);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        database = new DBHandler(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUsername.getText().length() <= 0 || txtPassword.getText().length() <= 0){
                    Toast.makeText(getApplicationContext(), "Please enter username and password.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                database.AddInfo(txtUsername.getText().toString(), txtPassword.getText().toString());

                Toast.makeText(getApplicationContext(), "Record added successfully.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> usernames = database.ReadAllInfo();

                for (String usr: usernames) {
                    Log.i("db",usr);
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUsername.getText().length() <= 0 || txtPassword.getText().length() <= 0){
                    Toast.makeText(getApplicationContext(), "Please enter username and password.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                List<String> matchList = database.readInfo(txtUsername.getText().toString(), txtPassword.getText().toString());

                if(matchList.size() > 0){
                    Toast.makeText(getApplicationContext(), "Username and password is correct",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Username and password is wrong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUsername.getText().length() <= 0){
                    Toast.makeText(getApplicationContext(), "Please enter username to delete",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                database.DeleteInfo(txtUsername.getText().toString());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUsername.getText().length() <= 0 || txtPassword.getText().length() <= 0){
                    Toast.makeText(getApplicationContext(), "Please enter username and password.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                database.updateInfo(txtUsername.getText().toString(), txtPassword.getText().toString());
            }
        });
    }
}