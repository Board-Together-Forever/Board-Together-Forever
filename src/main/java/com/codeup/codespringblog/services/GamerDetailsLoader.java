package com.codeup.codespringblog.services;

import com.codeup.codespringblog.models.Gamer;
import com.codeup.codespringblog.models.GamerWithRoles;
import com.codeup.codespringblog.repositories.GamerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class GamerDetailsLoader implements UserDetailsService {
    private final GamerRepository gamerDao;


    public GamerDetailsLoader(GamerRepository gamerDao) {
        this.gamerDao = gamerDao;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Gamer gamer = gamerDao.findByUsername(username);
        if (gamer == null) {
            throw new UsernameNotFoundException("No user found for " + username);
        }

        return new GamerWithRoles(gamer);
    }
}