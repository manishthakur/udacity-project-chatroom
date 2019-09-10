package edu.udacity.java.nano;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void login() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void userJoin() throws Exception {
        mockMvc.perform((get("/index").param("username", "user01")))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("user01")));
    }

    @Test
    public void chat() throws Exception {
        mockMvc.perform((get("/index").param("username", "user01")))
                .andExpect(status().isOk())
                .andExpect(view().name("chat"));
    }

    @Test
    public void leave() throws Exception {
        mockMvc.perform((get("/logout")))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("Chat Room")));

    }
}