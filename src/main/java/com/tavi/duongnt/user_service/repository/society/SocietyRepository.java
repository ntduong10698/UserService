package com.tavi.duongnt.user_service.repository.society;

import com.tavi.duongnt.user_service.entities.society.SocietyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SocietyRepository extends JpaRepository<SocietyEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update SocietyEntity s set s.status = false where s.id in (?1)")
    int deleted(List<Integer> listId);

    @Query("select count(s) from SocietyEntity s where upper(s.name) = upper(?1) and s.status = ?2")
    long countSocietyByNameAndStatus(String name, boolean status);

    @Query("select s from SocietyEntity s where upper(s.name) like concat('%',?1,'%') and s.status = ?2")
    List<SocietyEntity> findByNameAndStatus(String Name, boolean status);

    List<SocietyEntity> findByStatus(boolean status);
}
