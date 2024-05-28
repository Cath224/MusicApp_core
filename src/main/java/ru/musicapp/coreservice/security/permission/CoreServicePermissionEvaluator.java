package ru.musicapp.coreservice.security.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.musicapp.coreservice.controller.api.v1.*;
import ru.musicapp.coreservice.model.entity.user.User;
import ru.musicapp.coreservice.repository.UserRepository;
import ru.musicapp.coreservice.security.SecurityContextFacade;

import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CoreServicePermissionEvaluator {

    private static final Set<String> ADMIN_AUTHORITY = Set.of("ADMIN_AUTHORITY");

    private final UserRepository userRepository;


    public boolean canModify(Object o) {
        if (o instanceof PlaylistController
                || o instanceof LikeController
                || o instanceof HistoryController
                || o instanceof PlayController
                || o instanceof RegisterController
                || o instanceof AuthController
        ) {
            return true;
        }


        return isUserAdmin();
    }

    public boolean canModify(Object id, Object o) {
        if (o instanceof PlaylistController
                || o instanceof LikeController
                || o instanceof HistoryController
                || o instanceof PlayController
                || o instanceof RegisterController
                || o instanceof AuthController
        ) {
            return true;
        }

        if (o instanceof UserController) {
            if (isUserAdmin()) {
                return true;
            }
            return userRepository.findById((UUID) id).map(User::getId).orElseThrow(RuntimeException::new).equals(SecurityContextFacade.get().getId());
        }

        return isUserAdmin();
    }

    public boolean hasAccess(Object o) {
        if (o instanceof UserController) {
            return isUserAdmin();
        }
        return true;
    }

    public boolean hasAccess(UUID id, Object o) {
        if (o instanceof UserController) {
            return SecurityContextFacade.get().getId().equals(id);
        }
        return true;
    }

    private boolean isUserAdmin() {
        Boolean adminUser = userRepository.existsByIdAndAuthorities(SecurityContextFacade.get().getId(), ADMIN_AUTHORITY);

        return Boolean.TRUE.equals(adminUser);
    }
}
