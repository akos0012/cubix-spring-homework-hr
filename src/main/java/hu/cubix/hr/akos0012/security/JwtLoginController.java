package hu.cubix.hr.akos0012.security;

import hu.cubix.hr.akos0012.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtLoginController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/api/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password()));

        return jwtService.createJwt((UserDetails) authentication.getPrincipal());
    }
}
