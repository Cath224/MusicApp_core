package ru.musicapp.coreservice.security;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.musicapp.coreservice.model.UserExtendedDetails;

public class SecurityContextFacade {


    public static UserExtendedDetails get() {
        return (UserExtendedDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
