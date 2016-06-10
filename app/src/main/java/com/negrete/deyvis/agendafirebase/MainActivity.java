package com.negrete.deyvis.agendafirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText id, nombre, cantidad,contenido,valor;
    Button agregar, modificar, eliminar, ver, limpiar, buscar;
    Integer contContactos=0;

    private static final String FIREBASE_URL="https://agendapeluches.firebaseio.com/";
    private Firebase firebasedatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = (EditText) findViewById(R.id.eID);
        nombre = (EditText) findViewById(R.id.eNombre);
        cantidad = (EditText) findViewById(R.id.eCantidad);
       contenido = (EditText) findViewById(R.id.eContenido);
        valor = (EditText) findViewById(R.id.eValor);
        agregar = (Button) findViewById(R.id.bAgregar);
        modificar = (Button) findViewById(R.id.bModificar);
        eliminar = (Button) findViewById(R.id.bEliminar);
        ver = (Button) findViewById(R.id.bIventario);
        limpiar = (Button) findViewById(R.id.bLimpiar);
        buscar = (Button) findViewById(R.id.bBuscar);

        Firebase.setAndroidContext(this);
        firebasedatos = new Firebase(FIREBASE_URL);

        agregar.setOnClickListener(this);
        modificar.setOnClickListener(this);
        eliminar.setOnClickListener(this);
        ver.setOnClickListener(this);
        limpiar.setOnClickListener(this);
        buscar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        case R.id.bLimpiar:
        id.getText().clear();
        nombre.getText().clear();
        cantidad.getText().clear();
        contenido.getText().clear();
        break;

       case R.id.bAgregar:
        Toast.makeText(MainActivity.this, "Agregar", Toast.LENGTH_SHORT).show();
        String name = nombre.getText().toString();
        String phone = cantidad.getText().toString();
        String costo = valor.getText().toString();
        Firebase firebd = firebasedatos.child("contacto " + contContactos);
        CONTACTOS contactos = new CONTACTOS(String.valueOf(contContactos),name,phone,costo);
        firebd.setValue(contactos);
        id.getText().clear();
        nombre.getText().clear();
        cantidad.getText().clear();
        valor.getText().clear();
        contContactos++;

           break;


            case R.id.bIventario:
                firebasedatos.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        contenido.setText(dataSnapshot.getValue().toString());

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                break;

            case R.id.bBuscar:
                String codigo = id.getText().toString();
                final String idS = "contacto "+codigo;

                firebasedatos.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(idS).exists()){
                            contenido.setText(dataSnapshot.child(idS).getValue().toString());
                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                break;

            case  R.id.bModificar:
                codigo = id.getText().toString();
                name = nombre.getText().toString();
                phone = cantidad.getText().toString();
                firebd = firebasedatos.child("contacto "+codigo);
                Map<String, Object> nuevoNombre = new HashMap<>();
                nuevoNombre.put("nombre",name);
                firebd.updateChildren(nuevoNombre);
                Map<String, Object> nuevoCantidad = new HashMap<>();
                nuevoCantidad.put("cantidad",phone);
                firebd.updateChildren(nuevoCantidad);
                id.getText().clear();
                nombre.getText().clear();
                cantidad.getText().clear();
                break;

            case R.id.bEliminar:
                codigo = id.getText().toString();
                firebd = firebasedatos.child("contacto "+codigo);
                firebd.removeValue();


        }
    }
}