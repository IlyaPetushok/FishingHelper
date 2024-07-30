package fishinghelper.auth_service.controller;


import fishinghelper.auth_service.dto.UserDTORequestAuthorization;
import fishinghelper.auth_service.service.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return new ResponseEntity<>(authorizationService.userAuthorization(userDTORequestAuthorization), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        authorizationService.refreshToken(request, response);
    }

    @GetMapping("/update/user/{id}/password")
    public ResponseEntity<?> requestUpdatePassword(@PathVariable("id") Integer id){
        authorizationService.requestUpdatePassword(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update/password")
    public ResponseEntity<?> requestUpdatePassword(HttpServletRequest httpServletRequest){
        authorizationService.updatePassword(httpServletRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
