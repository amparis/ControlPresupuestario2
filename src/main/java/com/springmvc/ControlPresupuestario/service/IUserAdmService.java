package com.springmvc.ControlPresupuestario.service;

import com.springmvc.ControlPresupuestario.model.UserAdm;

import java.util.List;

public interface IUserAdmService {

    public List<UserAdm> getUsers();

    public UserAdm getUser(long id);

    //public UserAdm getUserByEmail(String email);
    public UserAdm getUserByUsernamee(String usernamee);

    public UserAdm saveUser(UserAdm newUser) ;

    public void updateUser(long id, UserAdm newUser) ;

    public void deleteUser(long id) ;
	
}
