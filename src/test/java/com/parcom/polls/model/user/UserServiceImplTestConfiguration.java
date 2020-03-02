package com.parcom.polls.model.user;

import com.parcom.polls.model.group.GroupToUserRepository;
import com.parcom.polls.model.student.StudentToUserRepository;
import com.parcom.network.Network;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UserServiceImplTestConfiguration {

    @Bean
    UserService userService(UserRepository userRepository, Network network, GroupToUserRepository groupToUserRepository, StudentToUserRepository studentToUserRepository) {
        return  new UserServiceImpl(userRepository,network,groupToUserRepository,studentToUserRepository);
    }
}
