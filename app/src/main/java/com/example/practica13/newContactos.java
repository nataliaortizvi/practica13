package com.example.practica13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class newContactos extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth auth;
    private FirebaseDatabase db;

    private TextView txwelcome;
    private EditText txnombre, txnumber;
    private Button btmas, btout;
    private String usuarioIn;
    private ListView listaContactos;
    private Userlog userlog;

    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contactos);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        if(auth.getCurrentUser() == null){
            gotoLogin();
        }else{
            btmas = findViewById(R.id.btmas);
            btout = findViewById(R.id.btout);
            txnombre = findViewById(R.id.txcorreo);
            txnumber = findViewById(R.id.txnumber);
            txwelcome = findViewById(R.id.txwelcome);
            listaContactos = findViewById(R.id.listaContactos);

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CALL_PHONE,
            }, 1);

            adapter = new UserAdapter();
            listaContactos.setAdapter(adapter);

            btmas.setOnClickListener(this);
            btout.setOnClickListener(this);

            recoverUser();
        }
    }

    private void gotoLogin() {
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
        finish();
    }

    private void recoverUser() {
        if(auth.getCurrentUser() != null){
            String id = auth.getCurrentUser().getUid();
            usuarioIn = id;
            db.getReference().child("usuarios").child(id).addListenerForSingleValueEvent( //ONCE
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot data) {
                            userlog = data.getValue(Userlog.class);
                            txwelcome.setText("Hola, "+ userlog.getNombre());

                            loadDatabase();
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    }
            );
        }
    }

    private void loadDatabase() {
        DatabaseReference ref = db.getReference().child("usuarios").child(usuarioIn).child("Contactos");
        ref.addValueEventListener( //ON
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot data) {
                        adapter.clear();
                        for (DataSnapshot child : data.getChildren()){
                            Usuario usuario = child.getValue(Usuario.class);
                            adapter.addUser(usuario);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                }
        );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Cerrar sesión")
                        .setMessage("¿Esta seguro que desea cerrar sesión?")
                        .setNegativeButton("NO", (dialog, id) -> {
                            dialog.dismiss();
                        })
                        .setPositiveButton("SI", (dialog, id) -> {
                            auth.signOut();
                            gotoLogin();
                        });
                builder.show();
                break;
            case R.id.btmas:

                String id = db.getReference().child("usuarios").child(usuarioIn).child("Contactos").push().getKey();
                String number = txnumber.getText().toString();
                String user = txnombre.getText().toString();

                DatabaseReference reference = db.getReference().child("usuarios").child(usuarioIn).child("Contactos").child(id);
                Usuario usuario = new Usuario(
                        user,
                        id,
                        number
                );
                reference.setValue(usuario);

                adapter.laRama(usuarioIn);

                txnombre.setText("");
                txnumber.setText("");

                break;
        }
    }
}