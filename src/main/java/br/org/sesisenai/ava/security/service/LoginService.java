package br.org.sesisenai.ava.security.service;

import br.org.sesisenai.ava.security.model.UserLoginDto;
import br.org.sesisenai.ava.security.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final AuthenticationManager manager;
    private final CookieUtil cookieUtil = new CookieUtil();

    public Boolean login(UserLoginDto dto,
                         HttpServletRequest req,
                         HttpServletResponse res) {
        try {
            UsernamePasswordAuthenticationToken token
                    = new UsernamePasswordAuthenticationToken(
                    dto.getUsername(), dto.getPassword()
            );
            Authentication auth = manager.authenticate(token);
            UserDetails ud = (UserDetails) auth.getPrincipal();

            Cookie cookie = cookieUtil.generateCookie(ud);
            res.addCookie(cookie);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception");
        }
    }

    public void logout(HttpServletRequest req,
                       HttpServletResponse res) {
        res.addCookie(cookieUtil.removeContext());
        res.addCookie(cookieUtil.generateNullCookie());
    }

}
