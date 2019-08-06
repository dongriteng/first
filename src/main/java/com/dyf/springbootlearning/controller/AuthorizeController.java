package com.dyf.springbootlearning.controller;

import com.dyf.springbootlearning.dto.AccessTokenDTO;
import com.dyf.springbootlearning.dto.GithubUser;
import com.dyf.springbootlearning.model.User;
import com.dyf.springbootlearning.provider.GithubProvider;
import com.dyf.springbootlearning.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
     UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("f77a8c3861de2da1992b");
        accessTokenDTO.setClient_secret("7731ed1b7e0b01d9fba9116d68540a55cda96196");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8088/callback");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null){
            request.getSession().setAttribute("user",githubUser);
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModefied(user.getGmtCreate());
            user.setName(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            System.out.println(githubUser.getName());
            userMapper.insert(user);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}
