package fishinghelper.user_service.controller;

import fishinghelper.user_service.dto.UserDTORequest;
import fishinghelper.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> showInfoUser(@RequestBody UserDTORequest userDTORequest){
        return new ResponseEntity<>(userService.showInfoUser(userDTORequest.getLogin()), HttpStatus.OK);
    }
}
