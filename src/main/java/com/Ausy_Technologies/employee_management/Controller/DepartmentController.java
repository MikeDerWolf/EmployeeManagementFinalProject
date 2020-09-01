package com.Ausy_Technologies.employee_management.Controller;

import com.Ausy_Technologies.employee_management.Model.DAO.Department;
import com.Ausy_Technologies.employee_management.Model.DAO.Employee;
import com.Ausy_Technologies.employee_management.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
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


    @GetMapping("/findDepartmentBy/{id}")
    public ResponseEntity<Department> findDepartmentById(@PathVariable int id)
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
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(this.departmentService.findAllDepartments());
    }

    @GetMapping("/listEmployeesInDepartment/{idDepartment}")
    public ResponseEntity<List<Employee>> listEmployeesInDepartment(@PathVariable int idDepartment)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeesInDepartment");
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(this.departmentService.listEmployeesInDepartment(idDepartment));
    }

    @DeleteMapping("/deleteDepartmentById/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable int id){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "deleteDepartment");
        this.departmentService.deleteDepartmentById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body("Delete successful");
    }


    @PutMapping("updateDepartment/{id}/{name}")
    public ResponseEntity<Department> updateDepartment(@PathVariable int id, @PathVariable String name){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "updateDepartment");
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(this.departmentService.updateDepartment(id, name));
    }
}
