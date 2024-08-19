package fishinghelper.admin_service.controller;

import fishinghelper.admin_service.dto.ConstrainDTO;
import fishinghelper.admin_service.dto.UserDTORequest;
import fishinghelper.admin_service.dto.filter.UserDTOFilter;
import fishinghelper.admin_service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin")
@RestController
public class AdminController {

    public AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasAuthority('UPDATE') and (hasRole('ROLE_ADMIN') or" +
            " (hasRole('ROLE_MODERATOR') and @adminServiceImpl.checkUpdateUserRole(#userDTORequest,#id)))")
    @PutMapping("/user/{id}/role")
    public ResponseEntity<?> adminUpdateRole(@PathVariable("id") Integer id, @RequestBody UserDTORequest userDTORequest) {
        adminService.updateRoleForUser(userDTORequest, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ') and (hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR'))")
    @PostMapping("/users/filter")
    public ResponseEntity<?> findUserByFilter(@RequestBody UserDTOFilter userDTOFilter) {
        return new ResponseEntity<>(adminService.findUserByFilter(userDTOFilter), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('CREATE')")
    @PostMapping("/users/{id}/constrain")
    public ResponseEntity<?> userSetConstrain(@PathVariable("id") Integer id, @RequestBody ConstrainDTO constrainDTO){
        adminService.setConstrainUser(constrainDTO,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('DELETE')")
    @DeleteMapping("/users/{id}/constrain")
    public ResponseEntity<?> userDeleteConstrain(@PathVariable("id") Integer id, @RequestBody ConstrainDTO constrainDTO){
        adminService.deleteConstrainUser(constrainDTO,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('READ')")
    @GetMapping("/users/{role}")
    public ResponseEntity<?> findUsersByRole(@PathVariable("role") String role){
        return  new ResponseEntity<>(adminService.findUsersByRole(role),HttpStatus.OK);
    }
}
