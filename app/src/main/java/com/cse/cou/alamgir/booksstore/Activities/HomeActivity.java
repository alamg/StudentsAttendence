package com.cse.cou.alamgir.booksstore.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cse.cou.alamgir.booksstore.Fragments.LectureFragment;
import com.cse.cou.alamgir.booksstore.Model.Subject;
import com.cse.cou.alamgir.booksstore.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
//import com.google.android.gms.auth.api.Auth;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.ArrayList;
import java.util.Arrays;

import mehdi.sakout.fancybuttons.FancyButton;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
        LectureFragment fragment;
    android.support.v4.app.FragmentManager fm;
        private FirebaseAuth mAuth;
        private Toolbar toolbar;
        private DatabaseReference db;
        private FloatingActionButton fab;
        private RecyclerView recyclerView;
        private ArrayList<Subject>subjectlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab=findViewById(R.id.addSubject);
        recyclerView=findViewById(R.id.subject_recylerview);
        subjectlist=new ArrayList<>();
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        db= FirebaseDatabase.getInstance().getReference();
        db=db.child(user.getUid()).child("Subject");
        toolbar.setTitle("Student Attandence");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSubject();
            }
        });
        drawNavigationDrwer();
        toolbar.inflateMenu(R.menu.home);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.log_out){
                    mAuth.signOut();
                }
                return true;
            }
        });

    }

    private void addSubject() {
        AlertDialog.Builder mydialog=new AlertDialog.Builder(this);
        View v=getLayoutInflater().inflate(R.layout.add_subject, null);
        final EditText etSub=v.findViewById(R.id.etSubject);
        final EditText etSem=v.findViewById(R.id.etSemester);
        Button btnsave=v.findViewById(R.id.btn_ddSubject);
        mydialog.setView(v);
        Dialog dialog=mydialog.create();
        dialog.show();
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sub=etSub.getText().toString().trim();
                String sem=etSem.getText().toString().trim();
                Subject subject=new Subject(sub,sem);

                db.push().setValue(subject);
            }
        });
    }

    private void drawNavigationDrwer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //displaySelectedItem(R.id.olsbooks);
    }
    public  void displaySelectedItem(int itemId){
         fragment=null;
        switch (itemId) {
            case R.id.newbooks:
                fragment = new LectureFragment();
                break;
        }
        //if (fragment!=null) {
          //  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
           // ft.add(R.id.content_fram, fragment);
           // ft.commit();
      //  }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        displaySelectedItem(id);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<Subject,MyViewHolder>adapter=new FirebaseRecyclerAdapter<Subject, MyViewHolder>(Subject.class,R.layout.subjectlist,MyViewHolder.class,db) {
            @Override
            protected void populateViewHolder(MyViewHolder viewHolder, final Subject model, final int position) {
               viewHolder.tvSubjectName.setText(model.getSubjectName());
                viewHolder.tvSemester.setText(model.getSemester());
                viewHolder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fragment=new LectureFragment();
                        fm=getSupportFragmentManager();
                        Bundle data=new Bundle();
                        data.putString("data",model.getSubjectName());
                        fragment.setArguments(data);
                        fragment.show(fm,"dialog");


                    }
                });

            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSubjectName, tvSemester;
        View v;

        public MyViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            tvSubjectName = v.findViewById(R.id.subName);
            tvSemester = v.findViewById(R.id.tvSem);
        }
    }
}
