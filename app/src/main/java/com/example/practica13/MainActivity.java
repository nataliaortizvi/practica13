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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btingresar, btnotengo;
    private EditText txcorreo, txcontra;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btingresar = findViewById(R.id.btingresar);
        btnotengo = findViewById(R.id.btnotengo);
        txcorreo = findViewById(R.id.txcorreo);
        txcontra = findViewById(R.id.txcontra);

        auth = FirebaseAuth.getInstance();

        btingresar.setOnClickListener(this);
        btnotengo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btingresar:
                auth.signInWithEmailAndPassword(txcorreo.getText().toString(),txcontra.getText().toString())
                        .addOnCompleteListener(
                                task -> {
                                    if(task.isSuccessful()){
                                        Intent i = new Intent(this, newContactos.class);
                                        startActivity(i);
                                        finish();

                                    }else{
                                        Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
                break;
            case R.id.btnotengo:
                Intent i = new Intent(this, Registro.class);
                startActivity(i);
                finish();
                break;
        }
    }
}