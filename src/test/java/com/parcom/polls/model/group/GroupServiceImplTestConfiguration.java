package com.parcom.polls.model.group;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GroupServiceImplTestConfiguration {

    @Bean
    GroupService groupService(GroupRepository groupRepository, GroupToUserRepository groupToUserRepository) {
        return  new GroupServiceImpl(groupRepository,groupToUserRepository);
    }
}
