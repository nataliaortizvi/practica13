package com.example.practica13;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {

    //Data
    private ArrayList<Usuario> usuarios;
    private String ramita;

    public UserAdapter(){
        usuarios = new ArrayList<>();
    }

    public void addUser(Usuario usuario){
        usuarios.add(usuario);
        notifyDataSetChanged();
    }

    public void clear(){
        usuarios.clear();
        notifyDataSetChanged();
    }

    public void laRama(String rama){
        ramita = rama;
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int i) {
        return usuarios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //dotar de UI a un arraylist
    @Override
    public View getView(int pos, View renglon, ViewGroup lista) {

        LayoutInflater inflater = LayoutInflater.from(lista.getContext());
        View renglonView = inflater.inflate(R.layout.row, null);

        Usuario usuario = usuarios.get(pos);

        Button btdelete = renglonView.findViewById(R.id.btdelete);
        Button btcall = renglonView.findViewById(R.id.btcall);
        TextView numberRow = renglonView.findViewById(R.id.numberRow);
        TextView nombreRow = renglonView.findViewById(R.id.nombreRow);

        nombreRow.setText(usuario.getUser());
        numberRow.setText(usuario.getNumber());


        btdelete.setOnClickListener(
                (v)->{
                    String id = usuario.getId();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(ramita).child(id);
                    reference.setValue(null);
                    Log.d("AAAAAA", ""+ramita);
                }
        );

        btcall.setOnClickListener(
                (v)->{
                    String tel = "tel:"+usuario.getNumber();
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse(tel));
                    lista.getContext().startActivity(i);
                }
        );

        return renglonView;
    }
}
