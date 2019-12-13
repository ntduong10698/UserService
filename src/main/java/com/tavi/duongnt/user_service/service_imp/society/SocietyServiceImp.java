package com.tavi.duongnt.user_service.service_imp.society;

import com.tavi.duongnt.user_service.entities.society.SocietyEntity;
import com.tavi.duongnt.user_service.repository.society.SocietyRepository;
import com.tavi.duongnt.user_service.service.society.SocietyService;
import com.tavi.duongnt.user_service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SocietyServiceImp implements SocietyService {

    private static final Logger LOGGER = Logger.getLogger(SocietyServiceImp.class.getName());

    @Autowired
    private SocietyRepository societyRepository;

    @Autowired
    private UserService userService;

    @Override
    public SocietyEntity upload(SocietyEntity societyEntity) {
        try {
            return societyRepository.save(societyEntity);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public int deleteList(List<Integer> listId) {
        try {
            int count = societyRepository.deleted(listId);
            System.out.println(SocietyRepository.class.getName()+": "+count+" societies were deleted");
            if (count > 0) {
                listId.forEach(id -> {
                    userService.deleteBySocietyId(id);
                });
                return societyRepository.deleted(listId);
            } else {
                return 0;
            }
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-error: {0}", ex.getMessage());
            return 0;
        }
    }

    @Override
    public int delete(int id) {
        return deleteList(Arrays.asList(id));
    }

    @Override
    public long countSocietyByNameAndStatus(String name, boolean status) {
        try {
            return societyRepository.countSocietyByNameAndStatus(name, status);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "count-society-by-name-and-status-error: {0}", ex.getMessage());
            return 0;
        }
    }

    @Override
    public List<SocietyEntity> findByNameAndStatus(String name, boolean status) {
        try {
            return societyRepository.findByNameAndStatus(name,status);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-name-and-status-error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public List<SocietyEntity> findAll() {
        try {
            return societyRepository.findByStatus(true);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all: {0}", ex.getMessage());
            return null;
        }
    }
}
