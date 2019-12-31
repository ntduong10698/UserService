package com.tavi.duongnt.user_service.service_imp.user;

import com.tavi.duongnt.user_service.entities.user.UserEntity;
import com.tavi.duongnt.user_service.payload.user.RegisterForm;
import com.tavi.duongnt.user_service.repository.user.UserRepository;
import com.tavi.duongnt.user_service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.tavi.duongnt.user_service.utils.EncodeUtils.getSHA256;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    private static final Logger LOGGER = Logger.getLogger(UserServiceImp.class.getName());

    @Override
    public int deleteList(List<Integer> listId) {
        try {
            int count = userRepository.deleted(listId);
            System.out.println(UserServiceImp.class.getName()+": "+count+" users were deleted");
            return count;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-error: {0}", ex.getMessage());
            return 0;
        }
    }

    @Override
    public int delete(int id) {
        return deleteList(Collections.singletonList(id));
    }

    @Override
    public UserEntity findById(int id, boolean deleted) {
        try {
            return userRepository.findByIdAndDeleted(id, deleted);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-id-error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        try {
            return userRepository.save(userEntity);
        } catch (Exception ex){
            LOGGER.log(Level.SEVERE, "update-user-error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public UserEntity findByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception ex){
            LOGGER.log(Level.SEVERE, "find-user-error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public long countByUsernameAndDeleted(String username, boolean deleted) {
        try {
            return userRepository.countByUsernameAndDeleted(username, deleted);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "count-by-username-and-deleted error: {0}", ex.getMessage());
            return 0;
        }
    }

    @Override
    public UserEntity register(RegisterForm registerForm) {
        try {
            return userRepository.save(UserEntity.builder()
                    .username(registerForm.getUsername())
                    .password(getSHA256(registerForm.getPassword()))
                    .email(registerForm.getEmail())
                    .initDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
                    .status(1)
                    .deleted(false)
                    .build());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "register error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public long countByEmailAndDeleted(String email, boolean deleted) {
        try {
            return userRepository.countByEmailAndDeleted(email, deleted);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "count-by-email-and-deleted error: {0}", ex.getMessage());
            return 0;
        }
    }

    @Override
    public UserEntity findByUsernameAndPasswordAndDeleted(String username, String password, boolean deleted) {
        try {
            return userRepository.findByUsernameAndPasswordAndDeleted(username, getSHA256(password), deleted);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-username-and-password-and-deleted error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public Page<UserEntity> findAll(Boolean deleted,int page, int size) {
        try {
            return userRepository.findAll(deleted,PageRequest.of(page -1 , size, Sort.by("id").descending()));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-pageable error: {0}", ex.getMessage());
            return null;
        }
    }



}
