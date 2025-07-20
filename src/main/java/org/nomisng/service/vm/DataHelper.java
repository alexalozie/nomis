package org.nomisng.service.vm;

import org.nomisng.domain.entity.User;
import org.nomisng.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DataHelper {
    private final UserService userService;

    public Long getCurrentCboProjectId() {
        Optional<User> user = userService.getUserWithRoles();
        Long cboProjectId = 0L;
        if (user.isPresent()) {
            cboProjectId = user.get().getCurrentCboProjectId();
        }
        return cboProjectId;
    }

    @SuppressWarnings("all")
    public DataHelper(final UserService userService) {
        this.userService = userService;
    }
}
