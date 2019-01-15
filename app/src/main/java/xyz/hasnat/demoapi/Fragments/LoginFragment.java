package xyz.hasnat.demoapi.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.demoapi.Activity.MainActivity;
import xyz.hasnat.demoapi.Model.User;
import xyz.hasnat.demoapi.R;
import xyz.hasnat.demoapi.Services.MyInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private MyInterface loginFromActivityListener;
    private TextView registerTV;

    private EditText emailInput, passwordInput;
    private Button loginBtn;

    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        // for login
        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        loginBtn = view.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        registerTV = view.findViewById(R.id.registerTV);
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFromActivityListener.register();
            }
        });
        return view;
    } //ending onCreateView

    private void loginUser() {
        String Email = emailInput.getText().toString();
        String Password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(Email)){
            MainActivity.appPreference.showToast("Your email is required.");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            MainActivity.appPreference.showToast("Invalid email");
        } else if (TextUtils.isEmpty(Password)){
            MainActivity.appPreference.showToast("Password required");
        } else if (Password.length() < 6){
            MainActivity.appPreference.showToast("Password  may be at least 6 characters long.");
        } else {
            Call<User> userCall = MainActivity.serviceApi.doLogin(Email, Password);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.body().getResponse().equals("data")){
                        MainActivity.appPreference.setLoginStatus(true); // set login status in sharedPreference
                        loginFromActivityListener.login(
                                response.body().getName(),
                                response.body().getEmail(),
                                response.body().getCreated_at());
                    } else if (response.body().getResponse().equals("login_failed")){
                        MainActivity.appPreference.showToast("Error. Login Failed");
                        emailInput.setText("");
                        passwordInput.setText("");
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                }
            });
        }
    } //ending loginUser()

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        loginFromActivityListener = (MyInterface) activity;
    }

}
