package com.tavi.duongnt.user_service.service.society;

import com.tavi.duongnt.user_service.entities.society.SocietyEntity;

import java.util.List;

public interface SocietyService {
    SocietyEntity upload(SocietyEntity societyEntity);
    int deleteList(List<Integer> listId);
    int delete(int id);
    long countSocietyByNameAndStatus(String name, boolean status);
    List<SocietyEntity> findByNameAndStatus(String Name, boolean status);
    List<SocietyEntity> findAll();
}
