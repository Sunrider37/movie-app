package com.example.movies.controller;

import com.example.movies.model.Result;
import com.example.movies.model.User;
import com.example.movies.repository.UserRepository;
import com.example.movies.utils.ResultEnum;
import com.example.movies.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    @PostMapping("/signup")
    public Result signup(@RequestParam("username") String username
            , @RequestParam("password") String password,
                         @RequestParam Integer permission){
        if(userRepository.findByUsername(username) != null){
            return ResultUtil.error(ResultEnum.USER_DUPLICATE);
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPermission(permission);
        userRepository.save(user);
        return ResultUtil.success(ResultEnum.SIGN_UP);
    }

    @PostMapping("/signin")
    public Result signIn(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("permission") Integer permission){
        User user = userRepository.findByUsername(username);
        if (null == user) {
            return ResultUtil.error(ResultEnum.USER_MISSED);
        }
        if (!user.getPassword().equals(password)) {
            return ResultUtil.error(ResultEnum.WRONG_PASSWORD);
        }
        if (!user.getPermission().equals(permission)) {
            return ResultUtil.error(ResultEnum.PERMISSION_DENIED);
        }
        return ResultUtil.success(ResultEnum.SIGN_IN);
    }
}
