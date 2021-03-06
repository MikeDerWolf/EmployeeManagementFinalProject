package com.Ausy_Technologies.employee_management.Controller;

import com.Ausy_Technologies.employee_management.Model.DAO.Department;
import com.Ausy_Technologies.employee_management.Model.DAO.Employee;
import com.Ausy_Technologies.employee_management.Model.DAO.JobCategory;
import com.Ausy_Technologies.employee_management.Model.DTO.EmployeeDto;
import com.Ausy_Technologies.employee_management.RestErrorHandling.CustomException;
import com.Ausy_Technologies.employee_management.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;



    @PostMapping("/addEmployee")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        Employee employeeAdded = this.employeeService.saveEmployee(employee);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "addEmployee");
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeAdded);
    }

    @PostMapping("/addEmployee/{idDepartment}/{idJobCategory}")
    public ResponseEntity<Employee> saveEmployeeDJc(@RequestBody Employee employee, @PathVariable int idDepartment,
                                                    @PathVariable int idJobCategory)
    {
        Employee employeeAdded = this.employeeService.saveEmployeeDJc(employee, idDepartment, idJobCategory);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "addEmployeeDJC");
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(employeeAdded);

    }



    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable int id)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployee");
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(this.employeeService.findEmployeeById(id));
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> findAllEmployees()
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findAllEmployees");
        List<Employee> departmentsList = this.employeeService.findAllEmployees();

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(this.employeeService.findAllEmployees());
    }


    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "deleteEmployee");
        try {
            this.employeeService.deleteEmployeeById(id);
        } catch (CustomException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("Not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Deleted!");

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

    @PutMapping("updateEmployee/{idEmployee}/{idDepartment}/{idJobCategory}")
    public ResponseEntity<Employee> updateEmployee2(@RequestBody Employee employee,
                                                    @PathVariable int idEmployee, @PathVariable int idDepartment,
                                                    @PathVariable int idJobCategory){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "updateEmployeeDepartmentAndJob");
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).headers(httpHeaders).body(this.employeeService.
                updateEmployee2(employee, idEmployee, idDepartment, idJobCategory));
    }

    @PutMapping("updateEmployee/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable int id){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "updateEmployee");
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).headers(httpHeaders).body(this.employeeService.
                updateEmployee(employee,id));
    }


    @GetMapping("/getEmployeeDTO/{id}")
    public ResponseEntity<EmployeeDto> findEmployeeDtoById(@PathVariable int id){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeeDto");
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(this.employeeService.findEmployeeDtoById(id));

    }

    @GetMapping("/getAllEmployeesDTO")
    public ResponseEntity<List<EmployeeDto>> findAllEmployeesDto()
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findAllEmployeesDto");
        List<EmployeeDto> employeeDtoList = this.employeeService.findAllEmployeesDto();

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDtoList);
    }

    @GetMapping("/getEmployeesByDepartment/{idDepartment}")
    public ResponseEntity<List<EmployeeDto>> findEmployeesByDep(@PathVariable int idDepartment){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeesByDepartment");
        List<EmployeeDto> employeesList = this.employeeService.findEmployeesByDep(idDepartment);

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeesList);
    }

    @GetMapping("/getEmployeesByJob/{idJob}")
    public ResponseEntity<List<EmployeeDto>> findEmployeesByJob(@PathVariable int idJob){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeesByJobCategory");
        List<EmployeeDto> employeesList = this.employeeService.findEmployeesByJob(idJob);

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeesList);
    }

    @GetMapping("/getEmployeesByDepartmentAndJob/{departmentId}/{jobCategoryId}")
    public ResponseEntity<List<EmployeeDto>> findEmployeesByDepAndJob(@PathVariable int departmentId,
                                                                      @PathVariable int jobCategoryId){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeesByDepartmentAndJob");
        List<EmployeeDto> employeesList = this.employeeService.findEmployeesByDepAndJob(departmentId, jobCategoryId);

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeesList);
    }

    @GetMapping("/getEmployeesOrderBySalary")
    public ResponseEntity<List<EmployeeDto>> getEmployeesOrderBySalary(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeesByJobCategory");
        List<EmployeeDto> employeesList = this.employeeService.findEmployeesOrderBySalary();

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeesList);
    }

    @GetMapping("/getEmployeesOrderByLastName")
    public ResponseEntity<List<EmployeeDto>> getEmployeesOrderByLastName(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeesByJobCategory");
        List<EmployeeDto> employeesList = this.employeeService.findEmployeesOrderByLastName();

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeesList);
    }
}
