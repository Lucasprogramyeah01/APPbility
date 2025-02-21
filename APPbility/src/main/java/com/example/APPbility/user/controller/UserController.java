package com.example.APPbility.user.controller;

import com.example.APPbility.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService usuarioService;

}
