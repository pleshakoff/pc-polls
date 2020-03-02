package com.parcom.polls;

import com.parcom.security_client.UserDetailsPC;
import com.parcom.security_client.UserUtils;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;
import java.util.List;

@TestConfiguration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityTestConfiguration {

    public static final long ID_USER_ADMIN = 1L;
    public static final long ID_USER_MEMBER = 2L;
    public static final long ID_USER_CHILDFREE = 3L;
    public static final long ID_USER_PARENT = 4L;
    public static final long ID_USER_FROM_ANOTHER_GROUP = 5L;


    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        List<UserDetailsPC> testUsers = Arrays.asList(
                new UserDetailsPC("admin@parcom.com", "password",ID_USER_ADMIN, AuthorityUtils.createAuthorityList(UserUtils.ROLE_ADMIN), true,1L, 1L),
                new UserDetailsPC("member@parcom.com", "password",ID_USER_MEMBER, AuthorityUtils.createAuthorityList(UserUtils.ROLE_MEMBER),true, 1L, 2L),
                new UserDetailsPC("childFreeMember@parcom.com", "password", ID_USER_CHILDFREE, AuthorityUtils.createAuthorityList(UserUtils.ROLE_MEMBER),true, 1L, null),
                new UserDetailsPC("parent@parcom.com", "password", ID_USER_PARENT, AuthorityUtils.createAuthorityList(UserUtils.ROLE_PARENT), true,1L, 3L),
                new UserDetailsPC("fromAnotherGroup@parcom.com", "password", ID_USER_FROM_ANOTHER_GROUP, AuthorityUtils.createAuthorityList(UserUtils.ROLE_MEMBER), true,2L, 4L)
        );

        return username -> testUsers.stream().filter(userDetailsPC ->  userDetailsPC.getUsername().equals(username)).
                findFirst().orElseThrow(() -> new RuntimeException("Access denied in test"));
    }


}

