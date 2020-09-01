package com.Ausy_Technologies.employee_management.Service;

import com.Ausy_Technologies.employee_management.Model.DAO.Department;
import com.Ausy_Technologies.employee_management.Model.DAO.Employee;
import com.Ausy_Technologies.employee_management.Model.DAO.JobCategory;
import com.Ausy_Technologies.employee_management.Repository.DepartmentRepository;
import com.Ausy_Technologies.employee_management.Repository.EmployeeRepository;
import com.Ausy_Technologies.employee_management.Repository.JobCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class JobCategoryService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private JobCategoryRepository jobCategoryRepository;


    public JobCategory saveJobCategory(JobCategory jobCategory) {

        return this.jobCategoryRepository.save(jobCategory);
    }

    public JobCategory findJobCategoryById(int id)
    {
        if(!this.jobCategoryRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job category not found!");
        }
        return this.jobCategoryRepository.findById(id).get();

    }

    public List<JobCategory> findAllJobCategories()
    {
        List<JobCategory> jobCategoriesList = this.jobCategoryRepository.findAll();
        if(jobCategoriesList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Content not found!");
        }
        return jobCategoriesList;
    }

    public List<Employee> listEmployeesInJobCategory(int id){
        if(!this.jobCategoryRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job category not found!");
        }
        List<Employee> employeeList = this.jobCategoryRepository.findById(id).get().getEmployees();
        if(employeeList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No employees in job category!");
        }
        return employeeList;
    }

    public void deleteJobCategoryById(int id){

        if(!this.jobCategoryRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job category not found!");
        }
        if(!this.jobCategoryRepository.findById(id).get().getEmployees().isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Job category has employees!");
        }
        this.jobCategoryRepository.deleteById(id);
    }

    public JobCategory updateJobCategory(int id, String name){

        Optional<JobCategory> optionalJobCategory = this.jobCategoryRepository.findById(id);

        if(!optionalJobCategory.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job category not found!");
        }

        JobCategory existingJobCategory = optionalJobCategory.get();
        existingJobCategory.setName(name);
        return this.jobCategoryRepository.save(existingJobCategory);

    }
}
