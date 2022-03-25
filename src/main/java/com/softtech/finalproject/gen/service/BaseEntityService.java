package com.softtech.finalproject.gen.service;

import com.softtech.finalproject.gen.entity.BaseEntity;
import com.softtech.finalproject.gen.enums.GenErrorMessage;
import com.softtech.finalproject.gen.exceptions.ItemNotFoundException;
import com.softtech.finalproject.model.UserEntity;
import com.softtech.finalproject.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public abstract class BaseEntityService<E extends BaseEntity, D extends JpaRepository<E,Long>> {
    private final D dao;
    private AuthenticationService authenticationService;
@Autowired
    public void setAuthenticationService(@Lazy AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public List<E> findAll(){
        return dao.findAll();
    }
    public Optional<E> findById(Long id){
        return dao.findById(id);
    }
    public E save(E entity) {
        return (E)dao.save(entity);
    }
    public void delete(E entity){
        dao.delete(entity);
    }
    public E getByIdWithControl(Long id){
        Optional<E> entityOptional = findById(id);
        E entity;
        if(entityOptional.isPresent()){
            entity=entityOptional.get();
        }
        else{
            throw  new ItemNotFoundException(GenErrorMessage.ITEM_NOT_FOUND);
        }
        return entity;
    }
    public boolean existsById(Long id){
        return dao.existsById(id);
    }
    public D getDao() {
        return dao;
    }
    private String getUserName(){
        String userName = authenticationService.getCurrentUser().getUserName();
        return userName;
    }
}