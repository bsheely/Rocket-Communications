package com.rocketcommunications.services;

import com.rocketcommunications.models.Alert;
import com.rocketcommunications.models.Contact;
import com.rocketcommunications.models.User;
import com.rocketcommunications.repositories.AlertRepository;
import com.rocketcommunications.repositories.ContactRepository;
import com.rocketcommunications.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    private final UserRepository userRepository;
    private final AlertRepository alertRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public DataService(UserRepository userRepository, AlertRepository alertRepository, ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.alertRepository = alertRepository;
        this.contactRepository = contactRepository;
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<Alert> getAlerts() {
        return alertRepository.findAll();
    }

    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }
}
