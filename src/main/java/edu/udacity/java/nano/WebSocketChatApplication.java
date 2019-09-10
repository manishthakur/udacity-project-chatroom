package edu.udacity.java.nano;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
public class WebSocketChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketChatApplication.class, args);
    }

    /**
     * Login Page
     */
    @GetMapping("/")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    /**
     * Chatroom Page
     */
    @GetMapping("/index")
    public ModelAndView index(@NotEmpty String username, HttpServletRequest request) throws UnknownHostException {
        ModelAndView modelAndView = new ModelAndView("chat");
        if (StringUtils.isEmpty(username)) {
            username = "Anonymous";
        }
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    /**
     * Logout Page
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("login");
    }
}
