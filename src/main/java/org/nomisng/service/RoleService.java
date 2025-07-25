package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.nomisng.web.apierror.RecordExistException;
import org.nomisng.domain.dto.RoleDTO;
import org.nomisng.domain.entity.Permission;
import org.nomisng.domain.entity.Role;
import org.nomisng.repository.PermissionRepository;
import org.nomisng.repository.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    @PersistenceContext
    EntityManager em;

    public Role save(RoleDTO roleDTO) {
        Optional<Role> RoleOptional = roleRepository.findByName(roleDTO.getName());
        if (RoleOptional.isPresent()) throw new RecordExistException(Role.class, "Name", roleDTO.getName());
        try {
            Role role = new Role();
            role.setName(roleDTO.getName());
            role.setCode(UUID.randomUUID().toString());
            HashSet<Permission> permissions = getPermissions(roleDTO.getPermissions());
            role.setPermission(permissions);
            return roleRepository.save(role);
        } catch (Exception e) {
            throw e;
        }
    }

    public Role get(Long id) {
        Optional<Role> roleOptional = this.roleRepository.findById(id);
        if (!roleOptional.isPresent()) throw new EntityNotFoundException(Role.class, "Id", id + "");
        return roleOptional.get();
    }

    public Role updateName(long id, String name) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (!roleOptional.isPresent()) throw new EntityNotFoundException(Role.class, "Id", id + "");
        Role updatedRole = roleOptional.get();
        updatedRole.setName(name);
        return roleRepository.save(updatedRole);
    }

    public Role updatePermissions(long id, List<Permission> permissions) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (!roleOptional.isPresent()) throw new EntityNotFoundException(Role.class, "Id", id + "");
        Role updatedRole = roleOptional.get();
        HashSet<Permission> permissionsSet = getPermissions(permissions);
        updatedRole.setPermission(permissionsSet);
        return roleRepository.save(updatedRole);
    }

    private HashSet<Permission> getPermissions(List<Permission> permissions) {
        HashSet permissionsSet = new HashSet<>();
        Permission permissionToAdd = new Permission();
        for (Permission p : permissions) {
            try {
                // add permissions by either id or name
                if (null != p.getName()) {
                    permissionToAdd = permissionRepository.findByNameAndArchived(p.getName(), UN_ARCHIVED).get();
                } else {
                    ResponseEntity.badRequest();
                    return null;
                }
                permissionsSet.add(permissionToAdd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return permissionsSet;
    }

    /* @Transactional
    public List<UserDTO> getAllUsersByRoleId(Long id, String programCode){
        HashSet<Role> roles = new HashSet<>();
        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(Role.class, "id", ""+id));
        roles.add(role);
        userRepository.findAllByRoleIn(roles).forEach(user -> {
            if(!programCode.equalsIgnoreCase("*")) {
                user.setManagedPatientCount(applicationUserPatientRepository.findCountOfPatientManagedByUserInASpecificProgram(user.getId(), programCode, UN_ARCHIVED));
            } else {
                user.setManagedPatientCount(applicationUserPatientRepository.findCountOfPatientManagedByUserInAllProgram(user.getId(), UN_ARCHIVED));
            }
        });

        //TODO: find by user in organisation Unit...
        return userMapper.usersToUserDTOs(userRepository.findAllByRoleIn(roles));
    }*/
}
