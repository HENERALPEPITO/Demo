package services;

import models.LoginModel;

public interface AuthService {
    LoginModel authenticate(String username, String password);
}
