package com.example.practica13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Registro extends AppCompatActivity implements View.OnClickListener {

    private Button btregistrar, btyatengo;
    private EditText txnombre, txcorreo, txcontra, txcontrar;
    private FirebaseDatabase db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btregistrar = findViewById(R.id.btregistrar);
        btyatengo = findViewById(R.id.btyatengo);
        txnombre = findViewById(R.id.txnombre);
        txcorreo = findViewById(R.id.txcorreo);
        txcontra = findViewById(R.id.txcontra);
        txcontrar = findViewById(R.id.txcontrar);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        btregistrar.setOnClickListener(this);
        btyatengo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btregistrar:
                if(txnombre.getText().toString().equals(null) || txnombre.getText().toString().equals("")){
                    Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_LONG).show();
                }else{

                    if(txcontra.getText().toString().equals(txcontrar.getText().toString())){
                        auth.createUserWithEmailAndPassword(txcorreo.getText().toString(), txcontra.getText().toString())
                                .addOnCompleteListener(
                                        task -> {
                                            if(task.isSuccessful()){
                                                String id = auth.getCurrentUser().getUid();
                                                Userlog userlog = new Userlog(
                                                        txnombre.getText().toString(),
                                                        txcorreo.getText().toString(),
                                                        id,
                                                        txcontra.getText().toString()
                                                );
                                                db.getReference().child("usuarios").child(id).setValue(userlog)
                                                        .addOnCompleteListener(
                                                                taskdb -> {
                                                                    if(taskdb.isSuccessful()){
                                                                        Intent i = new Intent(this, newContactos.class);
                                                                        startActivity(i);
                                                                        finish();
                                                                    }
                                                                }
                                                        );

                                            }else{
                                                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                );
                    }else{
                        Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                    }
                }



                break;
            case R.id.btyatengo:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}