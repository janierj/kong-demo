package com.example.demo.security;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;
import java.util.HashSet;

/**
 * @author ekiras
 */

@Transactional
public class SUserDetailsService implements UserDetailsService {


    private UserRepository userRepository;

    public SUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                return null;
            }
            /**
             * ALWAYS ADMIN !
             */
            GrantedAuthority admin = new SimpleGrantedAuthority("ADMIN");
            HashSet authorities = new HashSet<GrantedAuthority>();
            authorities.add(admin);

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

//    private Set<GrantedAuthority> getAuthorities(User user) {
//        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
//        for (Role role : user.getRoles()) {
//            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
//            authorities.add(grantedAuthority);
//        }
//        LOGGER.debug("user authorities are " + authorities.toString());
//        return authorities;
//    }

}
