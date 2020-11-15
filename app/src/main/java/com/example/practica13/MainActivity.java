package com.example.practica13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btingresar;
    private EditText txname;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btingresar = findViewById(R.id.btingresar);
        txname = findViewById(R.id.txnombre);
        db = FirebaseDatabase.getInstance();

        btingresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String nombresito = txname.getText().toString();
        if(revisar(nombresito)){
            Intent i = new Intent(this, newContactos.class);
            i.putExtra("username", nombresito);
            startActivity(i);
        }


        /*String id = db.getReference().child("Usuario").push().getKey();
        DatabaseReference reference = db.getReference().child("Usuario").child(id);
        Usuario usuario = new Usuario(
                txname.getText().toString(),
                id
        );
        reference.setValue(usuario);

        Intent i = new Intent(this, newContactos.class);
        startActivity(i);
        */
    }

    public boolean revisar(String nombresito){
        return (nombresito.equals("") || nombresito == null) ? false : true;
    }
}