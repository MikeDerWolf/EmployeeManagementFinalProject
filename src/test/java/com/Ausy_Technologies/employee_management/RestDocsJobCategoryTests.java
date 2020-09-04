package com.Ausy_Technologies.employee_management;


import com.Ausy_Technologies.employee_management.Controller.DepartmentController;
import com.Ausy_Technologies.employee_management.Controller.EmployeeController;
import com.Ausy_Technologies.employee_management.Controller.JobCategoryController;
import com.Ausy_Technologies.employee_management.Model.DAO.Department;
import com.Ausy_Technologies.employee_management.Model.DAO.JobCategory;
import com.Ausy_Technologies.employee_management.Repository.DepartmentRepository;
import com.Ausy_Technologies.employee_management.Repository.JobCategoryRepository;
import com.Ausy_Technologies.employee_management.Service.DepartmentService;
import com.Ausy_Technologies.employee_management.Service.JobCategoryService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(JobCategoryController.class)
@AutoConfigureRestDocs
public class RestDocsJobCategoryTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    private RestDocumentationResultHandler document;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception
    {
        this.document = document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));


        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).
                apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.document)
                .build();
    }

    @MockBean
    private JobCategoryService jobCategoryService;

    @MockBean
    private JobCategoryRepository jobCategoryRepository;

    @Test
    public void getShouldReturnJobCategory() throws Exception {
        JobCategory jobCategory = new JobCategory();
        jobCategory.setId(3);
        jobCategory.setName("Tester");

        when(jobCategoryService.findJobCategoryById(3)).thenReturn(jobCategory);

        this.mockMvc.perform(get("/jobcategories/getJobCategoryById/{id}", 3).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Tester"))
                .andDo(document("{methodName}",
                        pathParameters(
                                parameterWithName("id").description("The id of the job category")),

                        responseFields(
                                fieldWithPath("id").description("The ID of the job category"),
                                fieldWithPath("name").description("The name of the job category")
                        )));
    }

    @Test
    public void addShouldReturnJobCategory() throws Exception {
        JobCategory jobCategory = new JobCategory();
        jobCategory.setId(5);
        jobCategory.setName("Administrator");

        when(jobCategoryService.saveJobCategory(any(JobCategory.class))).thenReturn(jobCategory);

        this.mockMvc.perform(post("/jobcategories/addJobCategory").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Administrator\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.name").value("Administrator"))
                .andDo(document("{methodName}",
                        requestFields(
                                fieldWithPath("name").description("The name of the job category")),

                        responseFields(
                                fieldWithPath("id").description("The ID of the job category"),
                                fieldWithPath("name").description("The name of the job category")
                        )));
    }

    @Test
    public void getAllShouldReturnJobCategories() throws Exception {
        JobCategory jobCategory1 = new JobCategory();
        jobCategory1.setId(3);
        jobCategory1.setName("Tester");
        JobCategory jobCategory2 = new JobCategory();
        jobCategory2.setId(4);
        jobCategory2.setName("Recruiter");

        List<JobCategory> jobCategoryList = new ArrayList<>();
        jobCategoryList.add(jobCategory1);
        jobCategoryList.add(jobCategory2);

        when(jobCategoryService.findAllJobCategories()).thenReturn(jobCategoryList);

        this.mockMvc.perform(get("/jobcategories/getAllJobCategories").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(3))
                .andExpect(jsonPath("$.[0].name").value("Tester"))
                .andExpect(jsonPath("$.[1].id").value(4))
                .andExpect(jsonPath("$.[1].name").value("Recruiter"))
                .andDo(document("{methodName}",
                        responseFields(
                                fieldWithPath("[].id").description("The ID of the job category"),
                                fieldWithPath("[].name").description("The name of the job category")
                        )));
    }

    @Test
    public void deleteJobCategoryShouldReturnMessage() throws Exception {
        jobCategoryService.deleteJobCategoryById(3);
        verify(jobCategoryService, times(1)).deleteJobCategoryById(3);

        this.mockMvc.perform(delete("/jobcategories/deleteJobCategory/{id}", 3).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Job category deleted!"))
                .andDo(document("{methodName}",
                        pathParameters(
                                parameterWithName("id").description("The id of the job category to be deleted"))

                ));
    }

    @Test
    public void updateShouldReturnJobCategory() throws Exception {
        JobCategory jobCategory = new JobCategory();
        jobCategory.setId(4);
        jobCategory.setName("Recruiter");

        jobCategory = jobCategoryService.updateJobCategory(4, "Recruiter Chef");

        when(jobCategoryService.updateJobCategory(4,"Recruiter Chef")).thenReturn(jobCategory);

        this.mockMvc.perform(put("/jobcategories/updateJobCategory/{id}/{name}", 4, "Recruiter chef").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isResetContent())
                .andDo(document("{methodName}",
                        pathParameters(
                                parameterWithName("id").description("The id of the job category"),
                                parameterWithName("name").description("The new name of the job category"))

                ));
    }

}
