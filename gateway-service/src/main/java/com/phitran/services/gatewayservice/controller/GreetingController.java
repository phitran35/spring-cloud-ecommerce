package com.phitran.services.gatewayservice.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class GreetingController {

    @RequestMapping("/securedPage")
    public String securedPage(@AuthenticationPrincipal OidcUser oidcUser,
                              @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client, Model model, Principal principal) {
        model.addAttribute("username", "2222");
        model.addAttribute("idToken", oidcUser.getIdToken());
        model.addAttribute("accessToken", client.getAccessToken());
        return "securedPage";
    }

    @RequestMapping("/")
    public String index(Model model, Principal principal) {
        return "index";
    }
}