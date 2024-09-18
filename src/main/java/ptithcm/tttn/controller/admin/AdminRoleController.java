package ptithcm.tttn.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ptithcm.tttn.service.RoleService;

@RestController
@RequestMapping("/api/admin/role")
public class AdminRoleController {
    private final RoleService roleService;

    public AdminRoleController(RoleService roleService) {
        this.roleService = roleService;
    }



}
