package com.philconnal.shoezone.service;

import com.philconnal.shoezone.entity.NewUser;
import com.philconnal.shoezone.repository.NewerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewerServiceImpl implements NewerService {
    private final NewerRepository userRepository;

    public NewerServiceImpl(NewerRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<NewUser> getAllUsers() {
        return (List<NewUser>) userRepository.findAll();
    }

    @Override
    public void saveUser(NewUser user) {
        userRepository.save(user);
    }

}
