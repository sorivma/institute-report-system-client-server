package com.institute.instituteserver.init;


import com.institute.instituteserver.entity.Privilege;
import com.institute.instituteserver.entity.Role;
import com.institute.instituteserver.entity.User;
import com.institute.instituteserver.repository.PrivilegeRepository;
import com.institute.instituteserver.repository.RoleRepository;
import com.institute.instituteserver.repository.UserRepository;
import com.institute.instituteserver.utils.ConfigUtility;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
public class InitialDataLoader implements ApplicationRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final ConfigUtility configUtility;
    private final PasswordEncoder passwordEncoder;

    public InitialDataLoader(UserRepository userRepository,
                             RoleRepository roleRepository,
                             PrivilegeRepository privilegeRepository,
                             ConfigUtility configUtility,
                             PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.configUtility = configUtility;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Optional<Privilege> privilege = privilegeRepository.findByName(name);

        if (privilege.isEmpty()) {
            Privilege initialPrivilege = new Privilege(name);
            privilegeRepository.save(initialPrivilege);
            return initialPrivilege;
        }
        return privilege.get();
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {
        Optional<Role> role = roleRepository.findByName(name);

        if (role.isEmpty()) {
            Role initialRole = Role.builder()
                    .name(name)
                    .privileges(privileges)
                    .build();
            roleRepository.save(initialRole);
            return initialRole;
        }
        return role.get();
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        Role adminRole
                = createRoleIfNotFound(
                "ADMIN_ROLE", Arrays.asList(readPrivilege, writePrivilege));

                  createRoleIfNotFound(
                "USER_ROLE", Arrays.asList(readPrivilege, writePrivilege));

        User adminUser = User.builder()
                .email(configUtility.getProperty("adminMail"))
                .password(passwordEncoder.encode(configUtility.getProperty("adminPassword")))
                .roles(Collections.singletonList(adminRole))
                .build();

        Optional<User> checkUser
                = userRepository.findByEmail(configUtility.getProperty("adminMail"));
        if (checkUser.isEmpty()) {
            userRepository.save(adminUser);
        }
    }
}
