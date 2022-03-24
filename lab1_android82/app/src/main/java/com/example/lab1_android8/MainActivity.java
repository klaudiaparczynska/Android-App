package com.example.lab1_android8;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView name, surname, marks, result;
    Button button, button_end;
    boolean correctName = false;
    boolean correctSurname = false;
    boolean correctMarks = false;
    TextView srednia;
    float avg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.imie);
        surname = findViewById(R.id.nazwisko);
        marks = findViewById(R.id.oceny);
        button = findViewById(R.id.button);
        button.setVisibility(View.INVISIBLE);
        button_end = findViewById(R.id.button_end);
        button_end.setVisibility(View.INVISIBLE);
        srednia = findViewById(R.id.srednia);
        result = findViewById(R.id.result);

    }

    @Override
    protected void onResume()
    {

        super.onResume();
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && name.getText().toString().isEmpty())
                {
                    name.setError("Nie podano imienia");
                    Toast.makeText(MainActivity.this,"Podaj imie",Toast.LENGTH_SHORT).show();
                }
            }
        });
        surname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && surname.getText().toString().isEmpty())
                {
                    surname.setError("Nie podano nazwiska");
                    Toast.makeText(MainActivity.this,"Podaj nazwisko",Toast.LENGTH_SHORT).show();
                }
            }
        });
        marks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && marks.getText().toString().isEmpty())
                {
                    marks.setError("Nie podano liczby ocen");
                    Toast.makeText(MainActivity.this,"Podaj liczbe ocen",Toast.LENGTH_SHORT).show();
                }
                else if( !hasFocus && !marks.getText().toString().isEmpty())
                {
                    int value = Integer.valueOf(marks.getText().toString());
                    if(value < 5 || value > 15)
                    {
                        marks.setError("Błędna liczba ocen");
                        Toast.makeText(MainActivity.this,"Podaj liczbe ocen z zakresu 5-15",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(!name.getText().toString().isEmpty()) correctName = true;
                else correctName = false;
                if (correctName && correctSurname && correctMarks)
                {
                    button.setVisibility(View.VISIBLE);
                }
                else button.setVisibility(View.INVISIBLE);
            }
        });
        surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!surname.getText().toString().isEmpty()) correctSurname = true;
                else correctSurname = false;
                if (correctName && correctSurname && correctMarks)
                {
                    button.setVisibility(View.VISIBLE);
                }
                else button.setVisibility(View.INVISIBLE);
            }
        });
        marks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!marks.getText().toString().isEmpty())
                {
                    if(Integer.valueOf(marks.getText().toString()) >= 5 &&
                            Integer.valueOf(marks.getText().toString()) <= 15) correctMarks = true;
                    else correctMarks = false;
                }
                else correctMarks = false;
                if (correctName && correctSurname && correctMarks)
                {
                    button.setVisibility(View.VISIBLE);
                }
                else button.setVisibility(View.INVISIBLE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencja = new Intent(MainActivity.this, MarkList.class);
                intencja.putExtra("marks", Integer.valueOf(marks.getText().toString()));
                startActivity(intencja);
            }
        });

        Intent average = getIntent();
        avg  = average.getFloatExtra("avg", 0);
        if( avg != 0) srednia.setText("Uzyskana średnia: " + Float.toString(avg));

        if (avg >= 3.0) {
            button_end.setVisibility(View.VISIBLE);
        }
        if (avg >= 2.0 && avg < 3.0){
            button_end.setText("Tym razem nie poszlo");
            button_end.setVisibility(View.VISIBLE);
        }
        button_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(avg >= 3.0) result.setText("Gratulacje! Otrzymujesz zaliczenie!");
                else result.setText("Wysyłam podanie o zaliczenie warunkowe");
            }
        });

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("n", name.getText().toString());
        outState.putString("s", surname.getText().toString());
        outState.putString("m", marks.getText().toString());
        outState.putString("r", result.getText().toString());
        outState.putString("sr", srednia.getText().toString());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstance) {
        super.onRestoreInstanceState(savedInstance);
        name.setText(savedInstance.getString("n"));
        surname.setText(savedInstance.getString("s"));
        marks.setText(savedInstance.getString("m"));
        result.setText(savedInstance.getString("r"));
        srednia.setText(savedInstance.getString("sr"));
        if (name.getText().toString().isEmpty() == false &&
                surname.getText().toString().isEmpty() == false &&
                Integer.valueOf(marks.getText().toString()) >= 5 &&
                Integer.valueOf(marks.getText().toString()) <= 15)
        {
            button.setVisibility(View.VISIBLE);
        }
        else button.setVisibility(View.INVISIBLE);


    }


}