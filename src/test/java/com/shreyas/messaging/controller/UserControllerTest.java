package com.shreyas.messaging.controller;

import com.shreyas.messaging.data.UserDTO;
import com.shreyas.messaging.model.User;
import com.shreyas.messaging.service.UserService;
import com.shreyas.messaging.util.UserUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@WebAppConfiguration
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserUtil userUtil;

    private MockMvc mockMvc;

    private final User mockUser = Mockito.mock(User.class);
    private final List<User> mockListUser = Mockito.mock(List.class);

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCreateUser_HTTP_SUCCESS() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("111");
        userDTO.setName("homie555");

        when(userUtil.dtoToModel(userDTO)).thenReturn(mockUser);

        userController.createUser(userDTO);

        String createNewUserJson = "{\n" +
                "    \"id\": \"111\",\n" +
                "    \"name\": homie555\n" +
                "}";
        MvcResult result = mockMvc.perform(
                post("/messaging/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createNewUserJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertThat(result).isNotNull();
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testGetAllUsers_HTTP_SUCCESS() throws Exception {
        when(userService.getAllUsers()).thenReturn(mockListUser);

        userController.getAllUsers();

        MvcResult result = mockMvc.perform(
                get("/messaging/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertThat(result).isNotNull();
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testGetUser_HTTP_SUCCESS() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("111");
        userDTO.setName("homie555");

        when(userService.getUserById(anyLong())).thenReturn(mockUser);

        userController.getUser(anyLong());

        MvcResult result = mockMvc.perform(
                get("/messaging/users/{id}", userDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertThat(result).isNotNull();
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testDeleteUser_HTTP_SUCCESS() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());

        userController.deleteUser(anyLong());

        MvcResult result = mockMvc.perform(
                delete("/messaging/users/111")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertThat(result).isNotNull();
        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
    }
}