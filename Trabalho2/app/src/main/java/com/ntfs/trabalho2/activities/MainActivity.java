package com.ntfs.trabalho2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ntfs.trabalho2.R;
import com.ntfs.trabalho2.entity.Carro;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onResume() {
        super.onResume();
        adapterCarros.notifyDataSetChanged();
    }

    private EditText editTextNumberCar;
    private ArrayList<Carro> carros;
    private ListView listViewCarros;
    private Button buttonEDIT;
    private Button buttonADD;
    private Button buttonDEL;
    private ArrayAdapter adapterCarros;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("carros");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumberCar = findViewById(R.id.editTextNumberCar);
        listViewCarros = findViewById(R.id.listViewCarros);
        buttonEDIT = findViewById(R.id.buttonEDIT);
        buttonADD = findViewById(R.id.buttonADD);
        buttonDEL = findViewById(R.id.buttonDEL);

        carros = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                carros.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String result = snapshot.getValue().toString();
                    Carro m = snapshot.getValue(Carro.class);
//                    Log.i("Teste", result);
                    carros.add(m);
                }
                if(carros.size() > 0)
                    Carro.setC(carros.get(carros.size() - 1).getId() + 1);
                else
                    Carro.setC(1);
                adapterCarros.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        adapterCarros = new ArrayAdapter(this,android.R.layout.simple_list_item_1,carros);
        listViewCarros.setAdapter(adapterCarros);

        listViewCarros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editTextNumberCar.setText(Integer.toString(carros.get(position).getId()));
                Toast.makeText(getBaseContext(), "ID : "+carros.get(position).getId(),Toast.LENGTH_SHORT).show();
            }
        });

        buttonADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), Activity2.class);
                intent.putExtra("Editando",false);
                startActivityForResult(intent,1);
                content();
            }
        });

        buttonEDIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextNumberCar.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(),"Por favor, digite um ID",Toast.LENGTH_SHORT).show();
                } else {
                    int id = Integer.parseInt(String.valueOf(editTextNumberCar.getText()));
                    Carro carroExistente = null;

                    for (Carro c : carros) {
                        if (c.getId() == id) {
                            carroExistente = c;
                        }
                    }

                    if (carroExistente != null) {

                        Intent intent = new Intent(getBaseContext(), Activity2.class);
                        intent.putExtra("Editando",true);
                        intent.putExtra("CarroExistente",carroExistente);
                        startActivityForResult(intent,1);
                        content();
                    } else {
                        Toast.makeText(getBaseContext(), "Nenhum carro registrado com esse ID", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonDEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextNumberCar.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(),"Por favor, digite um ID",Toast.LENGTH_SHORT).show();
                } else {
                    int id = Integer.parseInt(String.valueOf(editTextNumberCar.getText()));
                    Carro carroExistente = null;

                    for (Carro c : carros) {
                        if (c.getId() == id) {
                            carroExistente = c;
                            id = carros.indexOf(c);
                            break;
                        }
                    }

                    if (carroExistente != null) {
                       carros.remove(id);
                       DatabaseReference removido = databaseReference.child(editTextNumberCar.getText().toString());
                       removido.removeValue();
                       adapterCarros.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getBaseContext(), "Nenhum carro registrado com esse ID", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void content(){
        refresh(10);
    }

    private void refresh(int milisec){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            Carro car = null;
            if (resultCode == RESULT_OK){
                car = (Carro) data.getSerializableExtra("response");

                for (Carro c : carros) {
                    if (c.getId() == car.getId()) {
                        carros.set(carros.indexOf(c),car);
                        databaseReference.child(Integer.toString(c.getId())).setValue(car);
                        return;
                    }
                }
                carros.add(car);
                databaseReference.child(Integer.toString(car.getId())).setValue(car);
                ArrayAdapter adapt = new ArrayAdapter(this,android.R.layout.simple_list_item_1,carros);

            }
        }
    }
}