package ptithcm.tttn.service;

import ptithcm.tttn.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role findById(Long role_id) throws Exception;

    List<Role> findAll();
    Role createRole(Role role) throws Exception;
    Role updateRole(Long id, Role role) throws Exception;
}
