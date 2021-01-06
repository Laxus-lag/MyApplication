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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupFragment extends Fragment {

    private DatabaseReference dr;
    private FirebaseDatabase fd;

    EditText etUsername,etPassword,etEmail,etConfirm,etphone;
    TextView tv;
    Button buttonSignup;
    CallbackFragment callbackFragment;
    String username,password,email,confirmPassword,phone;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public SignupFragment(){}
    public void onAttach(Context context)
    {
        sharedPreferences = context.getSharedPreferences( "usersFile",Context.MODE_PRIVATE);
        editor =sharedPreferences.edit();
        super.onAttach(context);
    }
    public void setCallbackFragment(CallbackFragment callbackFragment)
    {
        this.callbackFragment=callbackFragment;
    }
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        etUsername = view.findViewById(R.id.editText);
        etPassword =view.findViewById(R.id.editText2);
        etConfirm=view.findViewById(R.id.editText4);
        etEmail=view.findViewById(R.id.editText3);
        etphone=view.findViewById(R.id.editText5);
        buttonSignup=view.findViewById(R.id.button);
        buttonSignup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                username=etUsername.getText().toString();
                password=etPassword.getText().toString();
                confirmPassword=etConfirm.getText().toString();
                email=etEmail.getText().toString();
                phone=etphone.getText().toString();
                if(password.equals(confirmPassword))
                {
                    editor.putString("username",username);
                    editor.putString("password",password);
                    editor.putString("email",email);
                    editor.apply();
                    Toast.makeText(getContext(),"Signed In",Toast.LENGTH_SHORT).show();
                    fd= FirebaseDatabase.getInstance();
                    dr = fd.getReference("Users");
                    DatabaseHelper dh = new DatabaseHelper(username,email,phone,password);
                    dr.child(username).setValue(dh);
                }
                else{
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                }

            }
        });
        tv = view.findViewById(R.id.textView2);
        tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(callbackFragment !=null)
                {
                    callbackFragment.changeFragmentsl();
                }
            }

        });
        return view;
    }
}