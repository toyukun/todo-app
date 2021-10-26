package com.toyu.simple.http.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TaskController.class)
class TaskControllerTest {
    private static final String BASE_URL = "/tasks";
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    public void getTask() throws Exception {
        // Prepare data
        val taskId = 12345L;
        val mockEntity = TaskEntity.builder()
                                   .id(taskId)
                                   .title("title")
                                   .description("description")
                                   .build();
        // Mock repository
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(mockEntity));

        // Execute and verify
        mockMvc.perform(get(BASE_URL + "/" + taskId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is(mockEntity.getId().intValue())))
               .andExpect(jsonPath("$.title", is(mockEntity.getTitle())))
               .andExpect(jsonPath("$.description", is(mockEntity.getDescription())))
        ;
    }

    @Test
    public void createTask() throws Exception {
        // Prepare data
        val taskId = 12345L;
        val mockEntity = TaskEntity.builder()
                                   .id(taskId)
                                   .title("title")
                                   .description("description")
                                   .build();
        val reqBody = new CreateTaskRequest("title val", "desc val");
        val reqBodyJson = mapper.writeValueAsString(reqBody);
        // Mock repository
        when(taskRepository.save(any())).thenReturn(mockEntity);

        // Execute and verify
        mockMvc.perform(
                       post(BASE_URL)
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(reqBodyJson)
               )
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id", is(mockEntity.getId().intValue())))
               .andExpect(jsonPath("$.title", is(mockEntity.getTitle())))
               .andExpect(jsonPath("$.description", is(mockEntity.getDescription())));
    }
}
