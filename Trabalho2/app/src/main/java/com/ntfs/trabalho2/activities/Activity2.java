package com.ntfs.trabalho2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ntfs.trabalho2.R;
import com.ntfs.trabalho2.entity.Carro;

public class Activity2 extends AppCompatActivity {

    private EditText editTextFabricante;
    private EditText editTextNumberAno;
    private EditText editTextModelo;
    private Button buttonCancel;
    private Button buttonSave;
    private boolean editando;
    private Carro carro = new Carro();
    private  Intent dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        buttonCancel = findViewById(R.id.buttonCancel);
        buttonSave = findViewById(R.id.buttonSave);
        editTextFabricante = findViewById(R.id.editTextFabricante);
        editTextNumberAno = findViewById(R.id.editTextNumberAno);
        editTextModelo = findViewById(R.id.editTextModelo);

        dados = getIntent();
        editando = dados.getBooleanExtra("Editando", false);

        editTextFabricante.setHint("Fabricante :");
        editTextModelo.setHint("Modelo :");
        editTextNumberAno.setHint("Ano :");
        
        if(editando) {

            carro = (Carro) dados.getSerializableExtra("CarroExistente");
            editTextFabricante.setHint(carro.getFabricante());
            editTextModelo.setHint(carro.getModelo());
            editTextNumberAno.setHint(Integer.toString(carro.getAno()));

        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editando) {
                    if (!editTextFabricante.getText().toString().equals(""))
                        carro.setFabricante(editTextFabricante.getText().toString());
                    if (!editTextModelo.getText().toString().equals(""))
                        carro.setModelo(editTextModelo.getText().toString());
                    if (!editTextNumberAno.getText().toString().equals(""))
                        carro.setAno(Integer.parseInt(editTextNumberAno.getText().toString()));

                } else {
                    String fabricante = editTextFabricante.getText().toString();
                    String modelo = editTextModelo.getText().toString();
                    int ano = Integer.parseInt(editTextNumberAno.getText().toString());
                    carro = new Carro(fabricante,modelo,ano);
                }

                Intent response = new Intent();
                response.putExtra("response",carro);

                setResult(RESULT_OK,response);
                finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}