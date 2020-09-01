package com.Ausy_Technologies.employee_management.Controller;

import com.Ausy_Technologies.employee_management.Model.DAO.Department;
import com.Ausy_Technologies.employee_management.Model.DAO.Employee;
import com.Ausy_Technologies.employee_management.Model.DAO.JobCategory;
import com.Ausy_Technologies.employee_management.Service.JobCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobCategories")
public class JobCategoryController {

    @Autowired
    private JobCategoryService jobCategoryService;

    @PostMapping("/addJobCategory")
    public ResponseEntity<JobCategory> saveJobCategory(@RequestBody JobCategory jobCategory) {

        JobCategory jobCategoryAdded = this.jobCategoryService.saveJobCategory(jobCategory);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "addJobCategory");
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(jobCategoryAdded);
    }


    @GetMapping("/findJobCategoryBy/{id}")
    public ResponseEntity<JobCategory> findJobCategoryById(@PathVariable int id)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findJobCategory");
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(this.jobCategoryService.findJobCategoryById(id));
    }

    @GetMapping("/findAllJobCategories")
    public ResponseEntity<List<JobCategory>> findAllJobCategories()
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findAllJobCategories");
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(this.jobCategoryService.findAllJobCategories());
    }

    @GetMapping("/listEmployeesInJobCategory/{idJobCategory}")
    public ResponseEntity<List<Employee>> listEmployeesInJobCategory(@PathVariable int idJobCategory)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeesInJobCategory");
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(this.jobCategoryService.listEmployeesInJobCategory(idJobCategory));
    }

    @DeleteMapping("/deleteJobCategoryById/{id}")
    public ResponseEntity<String> deleteJobCategory(@PathVariable int id){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "deleteJobCategory");
        this.jobCategoryService.deleteJobCategoryById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body("Delete successful");
    }


    @PutMapping("updateJobCategory/{id}/{name}")
    public ResponseEntity<JobCategory> updateJobCategory(@PathVariable int id, @PathVariable String name){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "updateJobCategory");
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(this.jobCategoryService.updateJobCategory(id, name));
    }
}
