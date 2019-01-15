package xyz.hasnat.demoapi.Fragments;


import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.demoapi.Activity.MainActivity;
import xyz.hasnat.demoapi.Model.User;
import xyz.hasnat.demoapi.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {

    private EditText nameInput, emailInput, phoneInput, passwordInput;
    Button regBtn;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_registration, container, false);
        nameInput = view.findViewById(R.id.nameInput);
        emailInput = view.findViewById(R.id.emailInput);
        phoneInput = view.findViewById(R.id.phoneInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        regBtn = view.findViewById(R.id.regBtn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
                Log.e("reg button", "clicked");
            }
        });
        return view;
    }

    public void registerUser() {
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(name)){
            MainActivity.appPreference.showToast("Your name is required.");
        } else if (TextUtils.isEmpty(email)){
            MainActivity.appPreference.showToast("Your email is required.");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            MainActivity.appPreference.showToast("Invalid email");
        } else if (TextUtils.isEmpty(password)){
            MainActivity.appPreference.showToast("Password required");
        } else if (password.length() < 6){
            MainActivity.appPreference.showToast("Create a password at least 6 characters long.");
        }
        else {
            Call<User> userCall = MainActivity.serviceApi.doRegistration(name, email, phone, password);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.body().getResponse().equals("inserted")){
                        Log.e("response", response.body().getResponse());
                        nameInput.setText("");
                        emailInput.setText("");
                        phoneInput.setText("");
                        passwordInput.setText("");
                        MainActivity.appPreference.showToast("Registered Successfully");
                    } else if (response.body().getResponse().equals("exists")){
                        MainActivity.appPreference.showToast("This email already exists");
                    } else if (response.body().getResponse().equals("error")){
                        MainActivity.appPreference.showToast("Oops! something went wrong.");
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                }
            });
        }

    }

}
