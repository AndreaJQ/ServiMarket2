package com.grupob.ServiMarket.service;

import com.grupob.ServiMarket.entity.UserEntity;
import com.grupob.ServiMarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void create(UserEntity us) {
        userRepository.save(us);
    }

    @Transactional
    public void delete(Long id) {
        Optional<UserEntity> response = userRepository.findById(id);
        if (response != null) {
            UserEntity userToDelete = response.get();
            userRepository.delete(userToDelete);
        }
    }

    public List<UserEntity> list() {
        List<UserEntity> user = new ArrayList<>();
        user = userRepository.findAll();
        return user;
    }

    @Transactional
    public void update(Long id, UserEntity us) {
        Optional<UserEntity> response = userRepository.findById(id);
        if (response.isPresent()) {
            userRepository.save(us);
        }
    }


    //---------------------GET USER BY ID-----------------
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    //---------------------UPDATE USER-----------------

    public UserEntity updateUser(UserEntity userE) {
        UserEntity updateUser = userRepository.findById(userE.getId()).orElse(null);
        if (updateUser != null) {
            updateUser.setName(userE.getName());
            updateUser.setLastName(userE.getLastName());
            updateUser.setEmail(userE.getEmail());
            updateUser.setContact(userE.getContact());
            updateUser.setAddress(userE.getAddress());
            updateUser.setPassword(userE.getPassword());
            //updateUser.setScore(userE.getScore());
            updateUser.setRole(userE.getRole());
            updateUser.setStatus(true);
            userRepository.save(updateUser);
            return updateUser;
        }
        return null;
    }

    //---------------------SEARCH USER NAME-----------------
    public List<UserEntity> searchUsers(String query) {
        List<UserEntity> users = userRepository.searchUsername(query);
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity us = userRepository.findByEmail(email);

        if(us!=null){

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+us.getRole().toString());
            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", us);

            return new User(us.getEmail(), us.getPassword(), permisos);
        }else{
            return null;
        }
    }
}
