package com.cse.cou.alamgir.booksstore.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.cse.cou.alamgir.booksstore.Adapters.MyAdapter;
import com.cse.cou.alamgir.booksstore.Model.Lecture;
import com.cse.cou.alamgir.booksstore.Model.Student;
import com.cse.cou.alamgir.booksstore.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.

 * create an instance of this fragment.
 */
public class StudentsActivity extends AppCompatActivity {
    private ArrayList<Student> catagoryList;
    private RecyclerView recyclerView;
    private static DatabaseReference db;
    private FloatingActionButton fab;
    private ProgressDialog mprogress;
    String subject=null;
    static int lec=0;
    Intent intent;
    FirebaseAuth mAuth;
    FirebaseUser user;
   FirebaseRecyclerAdapter<Student, MyViewHolder> adapter;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_old_books);
        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        intent=new Intent();
        subject=getIntent().getExtras().getString("subName");
        lec=getIntent().getExtras().getInt("lecture");
        db = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(subject);
        mprogress = new ProgressDialog(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBookCatagory();
            }
        });
        catagoryList = new ArrayList<>();
        ;
    }
    @Override
    public void onStart() {
        super.onStart();
       // final MyAdapter adapter=new MyAdapter(catagoryList,db.child("Student"));
        adapter=new FirebaseRecyclerAdapter<Student,MyViewHolder>(Student.class,R.layout.attendancelist_ayout,MyViewHolder.class,db.child("Student")) {


            @Override
            protected void populateViewHolder(MyViewHolder holder, Student student, int position) {
               String key=getRef(position).getKey();
                holder.tvName.setText(student.getStudentName());
                holder.tvPercentage.setText(String.valueOf(student.getPercentage()));
                holder.tvLecture.setText(String.valueOf(student.getLecture()));
                holder.tvAttadence.setText(String.valueOf(student.getTotalAttandene()));
                holder.tvRoll.setText(student.getStudentRoll());
                holder.setUpdate(student,key);
            }
        };
        db.child("Student").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Student student=dataSnapshot.getValue(Student.class);
                catagoryList.add(student);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
    private void addBookCatagory() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(this);
        View v = getLayoutInflater().inflate(R.layout.catagorylist, null);
        final EditText etName = v.findViewById(R.id.etName);
        final EditText etRoll=v.findViewById(R.id.etRoll);

        Button save = v.findViewById(R.id.saveData);
        mydialog.setView(v);
        Dialog dialog = mydialog.create();
        dialog.show();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_data(etName.getText().toString().trim(),etRoll.getText().toString().trim());
            }
        });
    }
    private void save_data(final String name, String roll) {

        mprogress.setMessage("Post to blog....");
        mprogress.show();
        Student student=new Student(name,roll,0,0,lec);
        db.child("Student").push().setValue(student);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPercentage, tvRoll, tvAttadence, tvLecture;
        CheckBox checkBox;
        View v;

        public MyViewHolder(View itemView) {
            super(itemView);
            v = itemView;
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
                    int attandence = student.getTotalAttandene() + 1;
                    Student student1 = new Student(student.getStudentName(), student.getStudentRoll(), attandence, student.getPercentage(),lec);
                    db.child("Student").child(key).setValue(student1);
                }
            });

        }
    }

}
