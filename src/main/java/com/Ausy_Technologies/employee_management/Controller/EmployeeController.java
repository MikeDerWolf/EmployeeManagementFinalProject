package com.Ausy_Technologies.employee_management.Controller;

import com.Ausy_Technologies.employee_management.Model.DAO.Department;
import com.Ausy_Technologies.employee_management.Model.DAO.Employee;
import com.Ausy_Technologies.employee_management.Model.DAO.JobCategory;
import com.Ausy_Technologies.employee_management.Model.DTO.EmployeeDto;
import com.Ausy_Technologies.employee_management.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;



    @PostMapping("/addEmployee")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        Employee employeeAdded = this.employeeService.saveEmployee(employee);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "addEmployee");
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(employeeAdded);
    }

    @PostMapping("/addEmployeeDJc/{idDepartment}/{idJobCategory}")
    public ResponseEntity<Employee> saveEmployeeDJc(@RequestBody Employee employee, @PathVariable int idDepartment,
                                                    @PathVariable int idJobCategory)
    {
        Employee employeeAdded = this.employeeService.saveEmployeeDJc(employee, idDepartment, idJobCategory);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "addEmployeeDJC");
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(employeeAdded);

    }



    @GetMapping("/findEmployeeBy/{id}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable int id)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployee");
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(this.employeeService.findEmployeeById(id));
    }

    @GetMapping("/findAllEmployees")
    public ResponseEntity<List<Employee>> findAllEmployees()
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findAllEmployees");
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(this.employeeService.findAllEmployees());
    }


    @DeleteMapping("/deleteEmployeeById/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "deleteEmployee");
        this.employeeService.deleteEmployeeById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body("Delete successful");

    }

    @PutMapping("changeDepartment/{idEmployee}/{idDepartment}")
    public ResponseEntity<Employee> changeDepartment(@PathVariable int idEmployee, @PathVariable int idDepartment){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "changeDepartment");
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(this.employeeService.
                changeDepartment(idEmployee, idDepartment));
    }

    @PutMapping("changeJobCategory/{idEmployee}/{idJobCategory}")
    public ResponseEntity<Employee> changeJobCategory(@PathVariable int idEmployee, @PathVariable int idJobCategory){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "changeJobCategory");
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(this.employeeService.
                changeJobCategory(idEmployee, idJobCategory));
    }

    @PutMapping("updateEmployee/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable int id){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "updateEmployee");
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(this.employeeService.
                updateEmployee(employee,id));
    }


    @GetMapping("/findEmployeeDtoBy/{id}")
    public ResponseEntity<EmployeeDto> findEmployeeDtoById(@PathVariable int id){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeeDto");
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(this.employeeService.findEmployeeDtoById(id));

    }

    @GetMapping("/findAllEmployeesDto")
    public ResponseEntity<List<EmployeeDto>> findAllEmployeesDto()
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findAllEmployeesDto");
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(this.employeeService.findAllEmployeesDto());
    }
}
