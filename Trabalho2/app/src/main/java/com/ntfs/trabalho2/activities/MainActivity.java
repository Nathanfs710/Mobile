package com.ntfs.trabalho2.activities;

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
    private TextView tv;
    private ArrayAdapter adapterCarros;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumberCar = findViewById(R.id.editTextNumberCar);
        listViewCarros = findViewById(R.id.listViewCarros);
        buttonEDIT = findViewById(R.id.buttonEDIT);
        buttonADD = findViewById(R.id.buttonADD);

        carros = new ArrayList<>();
        Carro c1 = new Carro("Honda","Civic",2000);
        Carro c2 = new Carro("Volkswagen","Gol",2000);
        Carro c3 = new Carro("Honda","Accord",2010);
        Carro c4 = new Carro("Honda","Fit",2015);
        Carro c5 = new Carro("Volkswagen","T-Cross",2020);
        Carro c6 = new Carro("Volkswagen","Jetta",2018);
        carros.add(c1);
        carros.add(c2);
        carros.add(c3);
        carros.add(c4);
        carros.add(c5);
        carros.add(c6);

        adapterCarros = new ArrayAdapter(this,android.R.layout.simple_list_item_1,carros);
        listViewCarros.setAdapter(adapterCarros);

        tv = findViewById(R.id.tv);

        tv.setText("Em fase de testes");

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
                        return;
                    }
                }
                carros.add(car);
                ArrayAdapter adapt = new ArrayAdapter(this,android.R.layout.simple_list_item_1,carros);

            }
        }
    }
}