package com.Ausy_Technologies.employee_management.Controller;

import com.Ausy_Technologies.employee_management.Model.DAO.Department;
import com.Ausy_Technologies.employee_management.Model.DAO.Employee;
import com.Ausy_Technologies.employee_management.RestErrorHandling.CustomException;
import com.Ausy_Technologies.employee_management.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/addDepartment")
    public ResponseEntity<Department> saveDepartment(@RequestBody Department department) {

        Department departmentAdded = this.departmentService.saveDepartment(department);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "addDepartment");
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(departmentAdded);
    }


    @GetMapping("/findDepartment")
    public ResponseEntity<Department> findDepartmentById(@RequestParam int id)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findDepartment");

        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(this.departmentService.findDepartmentById(id));
    }

    @GetMapping("/findAllDepartments")
    public ResponseEntity<List<Department>> findAllDepartments()
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findAllDepartments");
        List<Department> departmentsList = this.departmentService.findAllDepartments();

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(departmentsList);
    }

    @GetMapping("/listEmployeesInDepartment")
    public ResponseEntity<List<Employee>> listEmployeesInDepartment(@RequestParam int idDepartment)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeesInDepartment");

        List<Employee> employeesList = this.departmentService.listEmployeesInDepartment(idDepartment);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeesList);
    }

    @DeleteMapping("/deleteDepartment")
    public ResponseEntity<String> deleteDepartment(@RequestParam int id){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "deleteDepartment");
        try {
            this.departmentService.deleteDepartmentById(id);
        } catch (CustomException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("No value present!");
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Department deleted!");
    }


    @PutMapping("/updateDepartment")
    public ResponseEntity<Department> updateDepartment(@RequestParam int id, @RequestParam String name){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "updateDepartment");
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).headers(httpHeaders).body(this.departmentService.updateDepartment(id, name));
    }
}
