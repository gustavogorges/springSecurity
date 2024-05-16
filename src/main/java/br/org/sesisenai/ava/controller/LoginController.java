package br.org.sesisenai.ava.controller;

import br.org.sesisenai.ava.security.model.UserLoginDto;
import br.org.sesisenai.ava.security.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin
public class LoginController {
    private final LoginService loginService;
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody UserLoginDto dto,
            HttpServletRequest req,
            HttpServletResponse res
    ) {
        try {
            return new ResponseEntity<>
                    (loginService.login(dto, req, res),
                            HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>
                    (HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            HttpServletRequest req,
            HttpServletResponse res
    ) {
        try {
            loginService.logout(req, res);
            return new ResponseEntity<>
                    (HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>
                    (HttpStatus.CONFLICT);
        }
    }
}
