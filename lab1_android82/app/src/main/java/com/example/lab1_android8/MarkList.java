package com.example.lab1_android8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MarkList extends AppCompatActivity {

    int marks;
    String subjects[];
    RecyclerView recyclerView;
    Button button;
    RadioGroup radioGroup;
    float sum = 0;
    TextView test;
    RadioButton radio;
    private ArrayList<Integer> mNumberList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_list);

        subjects = getResources().getStringArray(R.array.subjects);
        recyclerView = findViewById(R.id.recycler_view);
        button = findViewById(R.id.button_count);


        Bundle pakunek = getIntent().getExtras();
        marks = pakunek.getInt("marks");

        mNumberList = new ArrayList<>();
        for (int i = 0; i < marks; i++)
            mNumberList.add(0);

        MyAdapter myAdapter = new MyAdapter(this, mNumberList, subjects);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sum = 0;
                for (int number : mNumberList)
                    sum += number;
                sum /= Float.valueOf(marks);
                Intent zwrot = new Intent(MarkList.this, MainActivity.class);
                zwrot.putExtra("avg", sum);
                startActivity(zwrot);

/*
                Bundle pakunek_zwrot = new Bundle();
                pakunek_zwrot.putFloat("avg", sum);
                Intent intent_zwrot = new Intent();
                intent_zwrot.putExtras(pakunek_zwrot);
                setResult(RESULT_OK, intent_zwrot);
                finish();

 */
                Toast.makeText(MarkList.this, "Average: "+ sum, Toast.LENGTH_LONG).show();

            }
        });
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {
    private ArrayList<Integer> mNumberList;
    private Activity mActivity;
    private String subjects[];
    public MyAdapter(Activity activity,ArrayList<Integer> numberList, String s[])
    {
        mNumberList=numberList;
        mActivity=activity;
        subjects = s;
    }
    //wywoływane gdy tworzony jest nowy wiersz
    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        //ustawienie rodzica i attachToRoot ->
        //wiersze będą uwzględniały rozmiary listy
        View rowRootView=mActivity.getLayoutInflater().inflate(R.layout.row_marks,parent,false);
        MyAdapterViewHolder myAdapterViewHolder = new MyAdapterViewHolder(rowRootView);
        return myAdapterViewHolder;
    }
    //wywoływany zawsze gdy ma być wyświetlony nowy wiersz
    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
        holder.subject.setText(subjects[position]);

        //najpierw ustawiamy etykietę bo setText wywoła zdarzenie
        holder.mNumberEditText.getTag(position);
        int i = holder.getAdapterPosition();
        holder.mNumberEditText.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.mark_2:

                        mNumberList.set(i,2);
                        break;
                    case R.id.mark_3:

                        mNumberList.set(i,3);
                        break;
                    case R.id.mark_4:

                        mNumberList.set(i,4);
                        break;
                    case R.id.mark_5:

                        mNumberList.set(i,5);
                        break;
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mNumberList.size();
    }
    //view holder zarządza pojedynczym wierszem listy
    //to dobre miejsce na zaimplementowanie słuchaczy
    class MyAdapterViewHolder extends RecyclerView.ViewHolder
    {
        RadioGroup mNumberEditText;
        TextView subject;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mNumberEditText = itemView.findViewById(R.id.radiogroup);
            subject = itemView.findViewById(R.id.subject_name);

        }

    }}