package com.example.apni_dukaan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private Button buttonLogin;
    EditText etUsername,etPassword;
    TextView tv;
    CallbackFragment callbackFragment;
    String username ,password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public LoginFragment(){}
    public void setCallbackFragment(CallbackFragment callbackFragment)
    {
        this.callbackFragment=callbackFragment;
    }
    public void onAttach(Context context)
    {
        sharedPreferences = context.getSharedPreferences( "usersFile",Context.MODE_PRIVATE);
        editor =sharedPreferences.edit();
        super.onAttach(context);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        etUsername = v.findViewById(R.id.editText);
        etPassword =v.findViewById(R.id.editText2);
        tv = v.findViewById(R.id.textView2);
        tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(callbackFragment!=null)
                {
                    callbackFragment.changeFragmentls();
                }
            }

        });
        buttonLogin=v.findViewById(R.id.button);
        buttonLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                username = etUsername.getText().toString();
                password =etPassword.getText().toString();
                verify();
            }
        });
        return v;
    }
    private void verify()
    {
        final String username1=etUsername.getText().toString().trim();
        final String password1=etPassword.getText().toString().trim();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");
        Query qr = ref.orderByChild("username").equalTo(username1);
        qr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String temp=dataSnapshot.child(username1).child("password").getValue(String.class);
                    if (temp.equals(password1))
                    {
                        Toast.makeText(getContext(),"Loged In",Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getContext(),"Wrong Password",Toast.LENGTH_SHORT).show();}
                }
                else {
                    Toast.makeText(getContext(),"No User",Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Dimri",Toast.LENGTH_SHORT).show();

            }
        });
    }


}
