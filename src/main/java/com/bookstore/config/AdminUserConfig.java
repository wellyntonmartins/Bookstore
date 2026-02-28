package com.bookstore.config;

import com.bookstore.models.RoleModel;
import com.bookstore.models.UserModel;
import com.bookstore.repositories.RoleRepository;
import com.bookstore.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@Configuration
public class AdminUserConfig implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminUserConfig(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Set<RoleModel> roleList = new HashSet<>();
        RoleModel roleAdmin = roleRepository.findByName("ADMIN");
        roleList.add(roleAdmin);

        UserModel userAdmin = userRepository.findByEmail("admin@gmail.com");

        if (!Objects.isNull(userAdmin)) {
            System.out.println("Admin exists.");
        } else {
            UserModel userAdminModel = new UserModel();
            userAdminModel.setName("admin");
            userAdminModel.setEmail("admin@gmail.com");
            userAdminModel.setPassword(bCryptPasswordEncoder.encode("admin123"));
            userAdminModel.setRoleModels(roleList);
            userRepository.save(userAdminModel);
        }
    }
}
