package com.tavi.duongnt.user_service.service.user;

import com.tavi.duongnt.user_service.entities.user.UserEntity;
import com.tavi.duongnt.user_service.payload.user.RegisterForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    int deleteList(List<Integer> listId);

    int delete(int id);

    UserEntity findById(int id, boolean deleted);

    UserEntity updateUser(UserEntity userEntity);

    UserEntity findByUsername(String username);

    long countByUsernameAndDeleted(String username, boolean deleted);

    UserEntity register(RegisterForm registerForm);

    long countByEmailAndDeleted(String email, boolean deleted);

    UserEntity findByUsernameAndPasswordAndDeleted(String username, String password, boolean deleted);

    Page<UserEntity> findAll(Boolean deleted,int page, int size);
}
