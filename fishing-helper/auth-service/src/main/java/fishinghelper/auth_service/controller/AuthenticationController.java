package fishinghelper.auth_service.controller;


import fishinghelper.auth_service.dto.TokenRequest;
import fishinghelper.auth_service.dto.UserDTORequestAuthorization;
import fishinghelper.auth_service.service.AuthorizationService;
import fishinghelper.auth_service.service.impl.AuthorizationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final AuthorizationService authorizationService;

    @Autowired
    public AuthenticationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/authorization")
    public ResponseEntity<?> authorization(@RequestBody UserDTORequestAuthorization userDTORequestAuthorization) {
        return authorizationService.userAuthorization(userDTORequestAuthorization);
    }

    @PostMapping("/introspect")
    public ResponseEntity<?> introspect(@RequestBody TokenRequest tokenRequest) {
        authorizationService.checkExpireToken(tokenRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRequest tokenRequest) {
        return authorizationService.refreshToken(tokenRequest);
    }

    @GetMapping("/update/user/{id}/password")
    public ResponseEntity<?> requestUpdatePassword(@PathVariable("id") Integer id) {
        authorizationService.requestUpdatePassword(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update/password")
    public ResponseEntity<?> updatePassword(HttpServletRequest httpServletRequest) {
        authorizationService.updatePassword(httpServletRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') and  hasAuthority('READ') ")
    @GetMapping("/testSecurity")
    public void testSecurityRole(){
        System.out.println("access");
    }
}
