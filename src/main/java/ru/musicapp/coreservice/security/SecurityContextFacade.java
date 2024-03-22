package ru.musicapp.coreservice.security;

import ru.musicapp.coreservice.model.UserDetails;

public class SecurityContextFacade {


    public static UserDetails get() {
        //(UserDetails) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return new UserDetails();
    }

}
