package com.cse.cou.alamgir.booksstore.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cse.cou.alamgir.booksstore.Model.Student;
import com.cse.cou.alamgir.booksstore.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    ArrayList<Student>studentsist;
    static DatabaseReference db;

    public MyAdapter(ArrayList<Student> studentsist, DatabaseReference db) {
        this.studentsist = studentsist;
        this.db=db;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.attendancelist_ayout,parent,false);
        MyViewHolder mv=new MyViewHolder(v);
        return mv;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
              Student student=studentsist.get(position);
              String key=db.getKey();
              holder.tvName.setText(student.getStudentName());
              holder.tvPercentage.setText(String.valueOf(student.getPercentage()));
              holder.tvLecture.setText(String.valueOf(student.getLecture()));
              holder.tvAttadence.setText(String.valueOf(student.getTotalAttandene()));
              holder.tvRoll.setText(student.getStudentRoll());
              holder.setUpdate(student,key);
    }

    @Override
    public int getItemCount() {
        return studentsist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public  TextView tvName,tvPercentage,tvRoll,tvAttadence,tvLecture;
        CheckBox checkBox;
         View v;
        public MyViewHolder(View itemView) {
            super(itemView);
            v=itemView;
            tvName = v.findViewById(R.id.tvName);
            tvPercentage = v.findViewById(R.id.tvPercentage);
            tvRoll = v.findViewById(R.id.tvRoll);
            tvAttadence = v.findViewById(R.id.tvAttance);
            tvLecture = v.findViewById(R.id.tvecture);
            checkBox = v.findViewById(R.id.Checkbox);
        }



        public void setUpdate(final Student student, final String key) {

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int attandence=student.getTotalAttandene()+1;
                    Student student1=new Student(student.getStudentName(),student.getStudentRoll(),attandence,student.getPercentage(),student.getLecture());
                    db.child(key).setValue(student1);
                }
            });

        }
    }
}
