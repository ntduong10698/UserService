package com.tavi.duongnt.user_service.repository.user;

import com.tavi.duongnt.user_service.entities.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update UserEntity u set u.deleted = true where u.id in (?1)")
    int deleted(List<Integer> listId);

    @Transactional
    @Modifying
    @Query("update UserEntity u set u.deleted = true where u.society.id = ?1")
    int deleteBySocietyId(int societyId);

    UserEntity findByIdAndDeleted(int id, boolean deleted);

    @Query("select count(u) from UserEntity u where u.username = ?1 and u.deleted = ?2")
    long countByUsernameAndDeleted(String username, boolean deleted);

    @Query("select count(u) from UserEntity u where u.email = ?1 and u.deleted = ?2")
    long countByEmailAndDeleted(String email, boolean deleted);

    @Query("select u from UserEntity u where u.username = ?1 and u.deleted = false ")
    UserEntity findByUsername(String username);

    UserEntity findByUsernameAndPasswordAndDeleted(String username, String password, boolean deleted);

    Page<UserEntity> findAll(Pageable pageable);

//    @Query("select u from UserEntity u where u.username like concat('%',?1,'%') " +
//            "and u.email like concat('%', ?2, '%') " +
//            "and u.phoneNumber like concat('%', ?2, '%')" +
//            "and u.lastName like concat('%', ?3, '%') " +
//            "and u.firstName like concat('%', ?4, '%')" +
//            "and (u.gender = ?5 or u.gender is null)" +
//            "and u.address like concat('%',?5,'%') " +
//            "and u.birthday like concat('%',?6,'%') " +
//            "and u.socieity.")
//    List<UserEntity> filter()
}
