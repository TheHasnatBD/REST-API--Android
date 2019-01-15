package xyz.hasnat.demoapi.Model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("response")
    private String response;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    @SerializedName("created_at")
    private String created_at;

    public String getResponse() {
        return response;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "response='" + response + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
