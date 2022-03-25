package com.softtech.finalproject.service.EntityService;

import com.softtech.finalproject.dao.UserDao;
import com.softtech.finalproject.gen.service.BaseEntityService;
import com.softtech.finalproject.model.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserEntityService extends BaseEntityService<UserEntity, UserDao> {
    public UserEntityService(UserDao dao) {
        super(dao);
    }
    public boolean existsByUserName(String userName) {
        return getDao().existsByUserName(userName);
    }
    public UserEntity findByUserName(String userName){
        return getDao().findByUserName(userName);
    }
}
