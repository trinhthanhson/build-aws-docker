package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Role;
import ptithcm.tttn.repository.RoleRepo;
import ptithcm.tttn.service.RoleService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;

    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public Role findById(Long id) throws Exception {
        Optional<Role> role = roleRepo.findById(id);
        if(role.isPresent()){
            return role.get();
        }
        throw new Exception("not found role wiht id " + id );
    }

    @Override
    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    @Override
    @Transactional
    public Role createRole(Role role) throws Exception {
        Role checkRole = roleRepo.findByName(role.getRole_name());
        if(checkRole != null){
            throw new Exception("Role exist");
        }
        Role create = new Role();
        create.setCreated_at(LocalDateTime.now());
        create.setRole_name(role.getRole_name());
        return roleRepo.save(create);

    }

    @Override
    @Transactional
    public Role updateRole(Long id, Role role) throws Exception {
        Role checkRole = findById(id);
        checkRole.setRole_name(role.getRole_name());
        return roleRepo.save(checkRole);
    }
}
