package app.services;

import app.dao.AdminDao;

public class AuthServiceImpl implements AuthService{

    private final AdminDao adminDao;

    public AuthServiceImpl(AdminDao adminDao){
        this.adminDao = adminDao;
    }

    @Override
    public boolean authenticate(String login, String password) {
       return adminDao.findByLogin(login)
               .map(admin -> admin.getPassword().equals(password))
               .orElse(false);
    }
}
