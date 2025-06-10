package fr.diginamic.springsecurity.controleur;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class ControleurBase {

    @GetMapping("/public")
    public String getHelloPublic() throws Exception{
        return "Hello public";
    }

    @GetMapping("/public-auth")
    public String getPAuth(Authentication authentication) throws Exception {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return "hello" + username;
    }

    @GetMapping("/public-auth2")
    public String getT() throws Exception{
        return "Hello ";
    }

    @GetMapping("/say-hello/{name}")
    public String getHelloPrivate(@PathVariable String name) throws Exception{
        return "Hello private";
    }



}