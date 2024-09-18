package ptithcm.tttn.controller.manager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Role;
import ptithcm.tttn.repository.RoleRepo;
import ptithcm.tttn.response.ListEntityResponse;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/manager/role")
public class ManagerRoleController {

    private final RoleRepo roleRepo;

    public ManagerRoleController(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @PostMapping("/add")
    public ResponseEntity<Role> addRoleByManager(@RequestBody Role r){
        Role role = new Role();
        if(!r.getRole_name().equals("")){
        role.setRole_name(r.getRole_name());
        role.setCreated_at(LocalDateTime.now());
        }
        return new ResponseEntity<>(roleRepo.save(role), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse> findAllRole(@RequestHeader("Authorization") String jwt){
        ListEntityResponse res = new ListEntityResponse();
        res.setData(roleRepo.findAll());
        res.setStatus(HttpStatus.OK);
        res.setCode(HttpStatus.OK.value());
        res.setMessage("success");
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
