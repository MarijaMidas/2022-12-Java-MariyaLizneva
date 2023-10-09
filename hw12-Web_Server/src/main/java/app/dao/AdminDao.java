package app.dao;

import app.crm.model.Admin;

import java.util.Optional;

public interface AdminDao {

    public Optional<Admin> findByLogin(String login);

}
