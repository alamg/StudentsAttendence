package com.cse.cou.alamgir.booksstore.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cse.cou.alamgir.booksstore.Activities.StudentsActivity;
import com.cse.cou.alamgir.booksstore.Model.Lecture;
import com.cse.cou.alamgir.booksstore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement th
 * to handle interaction events.

 * create an instance of this fragment.
 */
public class LectureFragment extends DialogFragment {
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private EditText etLecture;
    private TextView tvecture;
    private CheckBox checkBox;
    private Button saveLecture;
    private DatabaseReference db;
    private Lecture lecture;
    private String key;
    String subName;

    public LectureFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=getLayoutInflater().inflate(R.layout.fragment_new_books, container, false);
        etLecture=v.findViewById(R.id.electure);
        tvecture=v.findViewById(R.id.tvlectre);
        checkBox=v.findViewById(R.id.btn_checkbox);
        saveLecture=v.findViewById(R.id.btnadd);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        String email=user.getUid();
        db= FirebaseDatabase.getInstance().getReference().child(email).child("lecture");
        lecture=new Lecture();
        saveLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     update();
                }
        });
        //tvecture.setText(lecture.getLecture());
        getDialog().setTitle("Lecture");
        subName=getArguments().getString("data");
        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Lecture");
    }

    private void update() {
        int lec=lecture.getLecture();
        lec=lec+1;
        lecture=new Lecture(lec);
        db.child(subName).child(key).setValue(lecture);
        Toast.makeText(getContext(),"Update successfull",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(getActivity(),StudentsActivity.class);
        intent.putExtra("subName",subName);
        intent.putExtra("lecture",lecture.getLecture());
        startActivity(intent);

    }

    public  void save()
    {
        String mlecture=etLecture.getText().toString().trim();
        int lec=Integer.parseInt(mlecture);
         lecture=new Lecture(lec);
        db.child(subName).push().setValue(lecture);
    }

    @Override
    public void onStart() {
        super.onStart();
        db.child(subName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    lecture=ds.getValue(Lecture.class);
                    key=ds.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}