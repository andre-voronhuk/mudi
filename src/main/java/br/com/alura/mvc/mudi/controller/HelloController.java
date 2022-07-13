package br.com.alura.mvc.mudi.controller;

import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")

    public String hello(HttpServletRequest request) {
        request.setAttribute("nome", "Andre");

        return "hello";
    }
}
