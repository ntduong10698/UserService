package com.tavi.duongnt.user_service.service_imp.user;

import com.tavi.duongnt.user_service.entities.user.UserEntity;
import com.tavi.duongnt.user_service.repository.user.UserRepository;
import com.tavi.duongnt.user_service.service.user.UserService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        return deleteList(Arrays.asList(id));
    }

    @Override
    public int deleteBySocietyId(int societyId) {
        try {
            int count = userRepository.deleteBySocietyId(societyId);
            System.out.println(UserServiceImp.class.getName()+": "+count+" users who have societyId = "+ societyId +" were deleted");
            return count;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-by-societyId-error: {0}", ex.getMessage());
            return 0;
        }
    }

    @Override
    public UserEntity findById(int id, boolean deleted) {
        try {
            return userRepository.findByIdAndDeleted(id, deleted);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-by-societyId-error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public long countByUsernameAndDeleted(String username, boolean deleted) {
        try {
            return userRepository.countByUsernameAndDeleted(username, deleted);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "count-by-username-and-deleted: {0}", ex.getMessage());
            return 0;
        }
    }

    @Override
    public UserEntity register(UserEntity userEntity) {
        try {
            userEntity.setPassword(getSHA256(userEntity.getPassword()));
            userEntity.setSociety(null);
            userEntity.setSocietyUser(null);
            LocalDate date = LocalDate.now();
            userEntity.setInitDate(date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
            return userRepository.save(userEntity);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "register: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public long countByEmailAndDeleted(String email, boolean deleted) {
        try {
            return userRepository.countByEmailAndDeleted(email, deleted);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "count-by-email-and-deleted: {0}", ex.getMessage());
            return 0;
        }
    }

    @Override
    public UserEntity findByUsernameAndPasswordAndDeleted(String username, String password, boolean deleted) {
        try {
            UserEntity user = userRepository.findByUsernameAndPasswordAndDeleted(username, getSHA256(password), deleted);
            return user;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-username-and-password-and-deleted: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public Page<UserEntity> findAll(int page, int size) {
        try {
            return userRepository.findAll(PageRequest.of(page -1 , size, Sort.by("id").descending()));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-pageable: {0}", ex.getMessage());
            return null;
        }
    }

    public String getSHA256(String password) {
        String rs = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            rs = bytesToHex(md.digest());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "get-sha256: {0}", ex.getMessage());
        }
        return rs;
    }

    public String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

}
