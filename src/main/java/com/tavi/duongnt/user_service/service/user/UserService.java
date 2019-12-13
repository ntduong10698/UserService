package com.tavi.duongnt.user_service.service.user;

import com.tavi.duongnt.user_service.entities.user.UserEntity;

import java.util.List;

public interface UserService {
    int deleteList(List<Integer> listId);
    int delete(int id);
    int deleteBySocietyId(int societyId);
    UserEntity findById(int id, boolean status);
    long countByUsernameAndStatus(String username, boolean status);
    UserEntity register(UserEntity userEntity);
    long countByEmailAndStatus(String email, boolean status);
    UserEntity findByUsernameAndPasswordAndStatus(String username, String password, boolean status);
}
