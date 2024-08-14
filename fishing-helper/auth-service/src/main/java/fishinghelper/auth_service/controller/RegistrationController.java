package fishinghelper.auth_service.controller;

import fishinghelper.auth_service.dto.UserDTORequestRegistration;
import fishinghelper.auth_service.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class RegistrationController {
    public final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registrationUser(@RequestBody UserDTORequestRegistration userDTORequestRegistration) {
        registrationService.createUser(userDTORequestRegistration);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/confirm/email/{mail}")
    public ResponseEntity<?> confirmMail(@PathVariable("mail") String mail) {
        registrationService.updateStatusConfirmEmail(mail);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
