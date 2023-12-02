package com.example.accountpayrolls.services;

import com.example.accountpayrolls.entities.AppUser;
import com.example.accountpayrolls.entities.AppUserAdapter;
import com.example.accountpayrolls.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    public AppUserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Email not found"));
        return new AppUserAdapter(appUser);
    }
}
