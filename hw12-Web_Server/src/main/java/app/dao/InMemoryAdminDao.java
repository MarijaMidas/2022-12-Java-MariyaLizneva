package app.dao;

import app.crm.model.Admin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAdminDao implements AdminDao{

    private final Map<Long, Admin> admins;

    public InMemoryAdminDao(){
        admins = new HashMap<>();
        admins.put(1L, new Admin(1L,  "user1", "1111"));
        admins.put(2L, new Admin(2L,  "user2", "2222"));
        admins.put(3L, new Admin(3L,  "user3", "3333"));
        admins.put(4L, new Admin(4L,  "user4", "4444"));
        admins.put(5L, new Admin(5L,  "user5", "5555"));
        admins.put(6L, new Admin(6L,  "user6", "6666"));
        admins.put(7L, new Admin(7L,  "user7", "7777"));
    }

    @Override
    public Optional<Admin> findByLogin(String login) {
        return admins.values().stream().filter(v->v.getLogin().equals(login)).findFirst();
    }
}
