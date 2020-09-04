package com.Ausy_Technologies.employee_management.Controller;

import com.Ausy_Technologies.employee_management.Model.DAO.Department;
import com.Ausy_Technologies.employee_management.Model.DAO.Employee;
import com.Ausy_Technologies.employee_management.Model.DAO.JobCategory;
import com.Ausy_Technologies.employee_management.RestErrorHandling.CustomException;
import com.Ausy_Technologies.employee_management.Service.JobCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobcategories")
@CrossOrigin(origins = "*")
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


    @GetMapping("/getJobCategoryById/{id}")
    public ResponseEntity<JobCategory> findJobCategoryById(@PathVariable int id)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findJobCategory");

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(this.jobCategoryService.findJobCategoryById(id));
    }

    @GetMapping("/getAllJobCategories")
    public ResponseEntity<List<JobCategory>> findAllJobCategories()
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findAllJobCategories");
        List<JobCategory> jobCategoriesList = this.jobCategoryService.findAllJobCategories();

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(jobCategoriesList);
    }

    @GetMapping("/listEmployeesInJobCategory")
    public ResponseEntity<List<Employee>> listEmployeesInJobCategory(@RequestParam int idJobCategory)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "findEmployeesInJobCategory");

        List<Employee> employeesList = this.jobCategoryService.listEmployeesInJobCategory(idJobCategory);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeesList);
    }

    @DeleteMapping("/deleteJobCategory/{id}")
    public ResponseEntity<String> deleteJobCategory(@PathVariable int id){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "deleteJobCategory");
        try {
            this.jobCategoryService.deleteJobCategoryById(id);
        } catch (CustomException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("No value present!");
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Job category deleted!");
    }


    @PutMapping("/updateJobCategory/{id}/{name}")
    public ResponseEntity<JobCategory> updateJobCategory(@PathVariable int id, @PathVariable String name){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "updateJobCategory");
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).headers(httpHeaders).body(this.jobCategoryService.updateJobCategory(id, name));
    }
}
