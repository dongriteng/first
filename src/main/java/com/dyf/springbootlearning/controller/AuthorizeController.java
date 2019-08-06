package com.dyf.springbootlearning.controller;

import com.dyf.springbootlearning.dto.AccessTokenDTO;
import com.dyf.springbootlearning.dto.GithubUser;
import com.dyf.springbootlearning.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("f77a8c3861de2da1992b");
        accessTokenDTO.setClient_secret("7731ed1b7e0b01d9fba9116d68540a55cda96196");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8088/callback");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";


    }
}
