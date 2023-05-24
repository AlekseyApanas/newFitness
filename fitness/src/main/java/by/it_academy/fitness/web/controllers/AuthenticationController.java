package by.it_academy.fitness.web.controllers;

import by.it_academy.fitness.core.dto.user.UserDTO;
import by.it_academy.fitness.core.dto.user.UserLogInDTO;
import by.it_academy.fitness.core.dto.user.UserRegistrationDTO;
import by.it_academy.fitness.facade.api.user.IAuthenticationFacade;
import by.it_academy.fitness.web.utils.UserHolder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RestController
@RequestMapping("/api/v1/users")
public class AuthenticationController {
    private IAuthenticationFacade authenticationFacade;

    public AuthenticationController(IAuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
        authenticationFacade.registration(userRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "/verification")
    public ResponseEntity<?> verification(@RequestParam("code") @NotBlank String code, @RequestParam("mail") @Email String mail) {
        authenticationFacade.verification(code, mail);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> logIn(@RequestBody @Valid UserLogInDTO userLogInDTO) {
        UserDTO userDTO = authenticationFacade.logIn(userLogInDTO);
        return ResponseEntity.status(HttpStatus.OK).body(authenticationFacade.getToken().generateToken(userDTO));
    }

    @GetMapping(path = "/me")
    public ResponseEntity<UserDTO> get() {
        UserHolder userHolder = new UserHolder();
        return ResponseEntity.status(HttpStatus.OK).body(userHolder.getUser());
    }
}
