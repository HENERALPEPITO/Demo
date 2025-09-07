package services_impl;

import models.LoginModel;
import services.AuthService;

import java.util.ArrayList;
import java.util.List;

public class AuthServiceImpl implements AuthService {
    private List<LoginModel> loginCredentials = new ArrayList<>();

    public AuthServiceImpl() {
        // seed credentials
        loginCredentials.add(new LoginModel("admin", "12345", "admin"));
        loginCredentials.add(new LoginModel("customer", "12345", "customer"));
    }

    @Override
    public LoginModel authenticate(String username, String password) {
        if (username == null || password == null) return null;
        for (LoginModel lm : loginCredentials) {
            if (lm.getUsername().equals(username) && lm.getPassword().equals(password)) {
                return lm;
            }
        }
        return null;
    }
}
