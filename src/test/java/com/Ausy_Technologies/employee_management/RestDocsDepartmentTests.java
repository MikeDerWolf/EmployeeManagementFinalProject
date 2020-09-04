package com.Ausy_Technologies.employee_management;


import com.Ausy_Technologies.employee_management.Controller.DepartmentController;
import com.Ausy_Technologies.employee_management.Controller.EmployeeController;
import com.Ausy_Technologies.employee_management.Model.DAO.Department;
import com.Ausy_Technologies.employee_management.Repository.DepartmentRepository;
import com.Ausy_Technologies.employee_management.Service.DepartmentService;
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
@WebMvcTest(DepartmentController.class)
@AutoConfigureRestDocs
public class RestDocsDepartmentTests {

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
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @Test
    public void getShouldReturnDepartment() throws Exception {
        Department department = new Department();
        department.setId(2);
        department.setName("HR");

        when(departmentService.findDepartmentById(2)).thenReturn(department);

        this.mockMvc.perform(get("/departments/getDepartmentById/{id}", 2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("HR"))
                .andDo(document("{methodName}",
                        pathParameters(
                                parameterWithName("id").description("The id of the department")),

                        responseFields(
                                fieldWithPath("id").description("The ID of the department"),
                                fieldWithPath("name").description("The name of the department")
                        )));
    }

    @Test
    public void addShouldReturnDepartment() throws Exception {
        Department department = new Department();
        department.setId(4);
        department.setName("Finance");

        when(departmentService.saveDepartment(any(Department.class))).thenReturn(department);

        this.mockMvc.perform(post("/departments/addDepartment").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Finance\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("Finance"))
                .andDo(document("{methodName}",
                        requestFields(
                                fieldWithPath("name").description("The name of the department")),

                        responseFields(
                                fieldWithPath("id").description("The ID of the department"),
                        fieldWithPath("name").description("The name of the department")
                )));
    }

    @Test
    public void getAllShouldReturnDepartments() throws Exception {
        Department department1 = new Department();
        department1.setId(1);
        department1.setName("IT");
        Department department2 = new Department();
        department2.setId(2);
        department2.setName("HR");

        List<Department> departmentList = new ArrayList<>();
        departmentList.add(department1);
        departmentList.add(department2);

        when(departmentService.findAllDepartments()).thenReturn(departmentList);

        this.mockMvc.perform(get("/departments/getAllDepartments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("IT"))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].name").value("HR"))
                .andDo(document("{methodName}",
                        responseFields(
                                fieldWithPath("[].id").description("The ID of the department"),
                                fieldWithPath("[].name").description("The name of the department")
                        )));
    }

    @Test
    public void deleteShouldReturnMessage() throws Exception {
        departmentService.deleteDepartmentById(3);
        verify(departmentService, times(1)).deleteDepartmentById(3);

        this.mockMvc.perform(delete("/departments/deleteDepartment/{id}", 3).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Department deleted!"))
                .andDo(document("{methodName}",
                        pathParameters(
                                parameterWithName("id").description("The id of the department to be deleted"))

                        ));
    }

    @Test
    public void updateShouldReturnDepartment() throws Exception {
        Department department = new Department();
        department.setId(2);
        department.setName("HR");

        department = departmentService.updateDepartment(2, "HumanResources");

        when(departmentService.updateDepartment(2,"HumanResources")).thenReturn(department);

        this.mockMvc.perform(put("/departments/updateDepartment/{id}/{name}", 2, "HumanResources").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isResetContent())
                .andDo(print())
                .andDo(document("{methodName}",
                        pathParameters(
                                parameterWithName("id").description("The id of the department"),
                                parameterWithName("name").description("The new name of the department"))

                ));
    }
}
