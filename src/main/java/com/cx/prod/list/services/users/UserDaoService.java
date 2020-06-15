package com.cx.prod.list.services.users;

import com.cx.prod.list.model.users.User;
import com.cx.prod.list.repos.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserDaoService {

    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(UserDaoService.class);

    public List<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).toList();
    }

    public List<User> getUsersByLastNameSortedByBirthDate(String name, Pageable pageable) {
        return userRepository.findByLastNameOrderByBirthDate("%" + name + "%", pageable);
    }

    public List<User> getOldestUserWithPhoneNumber() {
        return userRepository.findOldestUserWithPhoneNumber();
    }


    // for better performance should use userRepository.saveAll(..)
    // on users collection with existing users (recognised by existing phone number) removed
    public void mergeUsers(Collection<User> users) {
        for (User user : users) {
            List<User> existingUsers = userRepository.findByPhoneNumber(user.getPhoneNumber());
            if (existingUsers.isEmpty() || user.getPhoneNumber() == null) {
                userRepository.save(user);
            } else {
                logger.warn(user.toString() + " already stored");
            }
        }
    }

    public Integer getUserCount() {
        return userRepository.getTotalUserCount();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
