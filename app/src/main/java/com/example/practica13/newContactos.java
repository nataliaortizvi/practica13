package com.example.practica13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class newContactos extends AppCompatActivity implements View.OnClickListener{

    private FirebaseDatabase db;
    private EditText txnombre, txnumber;
    private Button btmas;
    private String usuarioIn;
    private ListView listaContactos;

    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contactos);

        btmas = findViewById(R.id.btmas);
        txnombre = findViewById(R.id.txnombre);
        txnumber = findViewById(R.id.txnumber);
        listaContactos = findViewById(R.id.listaContactos);

        db = FirebaseDatabase.getInstance();
        adapter = new UserAdapter();
        listaContactos.setAdapter(adapter);

        usuarioIn = getIntent().getExtras().getString("username","noHayUser");

        btmas.setOnClickListener(this);

        loadDatabase();
    }

    private void loadDatabase() {
        DatabaseReference ref = db.getReference().child(usuarioIn);
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
            case R.id.btmas:

                String id = db.getReference().child(usuarioIn).push().getKey();
                String number = txnumber.getText().toString();
                String user = txnombre.getText().toString();

                DatabaseReference reference = db.getReference().child(usuarioIn).push();
                Usuario usuario = new Usuario(
                        user,
                        id,
                        number
                );
                reference.setValue(usuario);

                txnombre.setText("");
                txnumber.setText("");

                break;
        }
    }
}