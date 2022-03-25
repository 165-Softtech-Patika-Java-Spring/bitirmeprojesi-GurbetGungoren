package com.softtech.finalproject.user;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.softtech.finalproject.BaseIntegrationTest;
import com.softtech.finalproject.dao.UserDao;
import com.softtech.finalproject.dto.user.UserSaveRequestDto;
import com.softtech.finalproject.dto.user.UserUpdateRequestDto;
import com.softtech.finalproject.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserControllerTest extends BaseIntegrationTest{
    private static final String BASE_PATH = "/api/v1/users";
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    UserDao userDao;
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }
    @Test
    @Sql(scripts = "/clean-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_create_user() throws Exception{
        UserSaveRequestDto userSaveRequestDto=UserSaveRequestDto.builder()
                .name("test_name")
                .surname("test_surname")
                .userName("test_username")
                .password("test_password")
                .build();
        String content = objectMapper.writeValueAsString(userSaveRequestDto);
        MvcResult result = mockMvc.perform(
                post(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);
    }

    @Test
    @Sql(scripts = "/user-create.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_not_create_user_when_username_already_exist_in_db() throws Exception{
        UserSaveRequestDto userSaveRequestDto=UserSaveRequestDto.builder()
                .name("test_name")
                .surname("test_surname")
                .userName("username1")
                .password("test_password")
                .build();
        String content = objectMapper.writeValueAsString(userSaveRequestDto);
        MvcResult result = mockMvc.perform(
                post(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();
        boolean isSuccess = isSuccess(result);
        assertFalse(isSuccess);
        UserEntity entity = userDao.findByUserName("test_username");
        assertThat(entity).isNull();
    }
    @Test
    @Sql(scripts = "/user-create.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_delete_user() throws Exception{
        MvcResult result = mockMvc.perform(
                delete(BASE_PATH + "/1001").content("1001").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        Optional<UserEntity> optionalUser = userDao.findById(1001L);
        assertThat(optionalUser).isEmpty();
    }
    @Test
    void should_not_delete_user_when_id_not_exist() throws Exception{
        ResultActions resultActions = mockMvc.perform(
                delete(BASE_PATH + "/1001").content("1001").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
        Optional<UserEntity> optionalUser = userDao.findById(1001L);
        assertThat(optionalUser).isEmpty();
    }
    @Test
    @Sql(scripts = "/user-create.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_update_user() throws Exception{
        UserUpdateRequestDto userUpdateRequestDto=UserUpdateRequestDto.builder()
                .id(1001L)
                .name("new name")
                .surname("new surname")
                .userName("new username")
                .password("new pass")
                .build();
        String content = objectMapper.writeValueAsString(userUpdateRequestDto);
        MvcResult result = mockMvc.perform(
                put(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);
        Optional<UserEntity> entity = userDao.findById(1001L);
        assertThat(entity).isNotEmpty();
        assertThat(entity.get().getUserName()).isEqualTo("new username");
        assertThat(entity.get().getName()).isEqualTo("new name");
        assertThat(entity.get().getSurname()).isEqualTo("new surname");
    }
    @Test
    void should_not_update_user_id_not_exist_in_db() throws Exception{
        UserUpdateRequestDto userUpdateRequestDto=UserUpdateRequestDto.builder()
                .id(1001L)
                .name("new name")
                .surname("new surname")
                .userName("new username")
                .password("new pass")
                .build();
        String content = objectMapper.writeValueAsString(userUpdateRequestDto);
        MvcResult result = mockMvc.perform(
                put(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();
        boolean isSuccess = isSuccess(result);
        assertFalse(isSuccess);
        Optional<UserEntity> entity = userDao.findById(1001L);
        assertThat(entity).isEmpty();
    }
}
