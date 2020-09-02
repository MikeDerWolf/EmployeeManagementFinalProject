package com.Ausy_Technologies.employee_management.Service;

import com.Ausy_Technologies.employee_management.Model.DAO.Department;
import com.Ausy_Technologies.employee_management.Model.DAO.Employee;
import com.Ausy_Technologies.employee_management.Model.DAO.JobCategory;
import com.Ausy_Technologies.employee_management.Repository.DepartmentRepository;
import com.Ausy_Technologies.employee_management.Repository.EmployeeRepository;
import com.Ausy_Technologies.employee_management.Repository.JobCategoryRepository;
import com.Ausy_Technologies.employee_management.RestErrorHandling.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Service
public class JobCategoryService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private JobCategoryRepository jobCategoryRepository;


    public JobCategory saveJobCategory(JobCategory jobCategory) {

        if(jobCategory.getName().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Incorrect value!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }

        return this.jobCategoryRepository.save(jobCategory);
    }

    public JobCategory findJobCategoryById(int id)
    {
        if(!this.jobCategoryRepository.findById(id).isPresent()){
            CustomException customException = new CustomException(HttpStatus.NOT_FOUND, "Job category not found!");
            CustomException.DisplayException(customException, Level.WARNING);
            throw customException;
        }
        return this.jobCategoryRepository.findById(id).get();

    }

    public List<JobCategory> findAllJobCategories()
    {
        List<JobCategory> jobCategoriesList = this.jobCategoryRepository.findAll();
        return jobCategoriesList;
    }

    public List<Employee> listEmployeesInJobCategory(int id){
        if(!this.jobCategoryRepository.findById(id).isPresent()){
            CustomException customException = new CustomException(HttpStatus.NOT_FOUND, "Job category not found!");
            CustomException.DisplayException(customException, Level.WARNING);
            throw customException;
        }
        List<Employee> employeeList = this.jobCategoryRepository.findById(id).get().getEmployees();
        return employeeList;
    }

    public void deleteJobCategoryById(int id){

        if(!this.jobCategoryRepository.findById(id).isPresent()){
            CustomException customException = new CustomException(HttpStatus.NOT_FOUND, "Job category not found!");
            CustomException.DisplayException(customException, Level.WARNING);
            throw customException;
        }

        this.jobCategoryRepository.deleteById(id);
    }

    public JobCategory updateJobCategory(int id, String name){

        Optional<JobCategory> optionalJobCategory = this.jobCategoryRepository.findById(id);

        if(!optionalJobCategory.isPresent()){
            CustomException customException = new CustomException(HttpStatus.NOT_FOUND, "Job category not found!");
            CustomException.DisplayException(customException, Level.WARNING);
            throw customException;
        }
        if(name.isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Incorrect value!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }

        JobCategory existingJobCategory = optionalJobCategory.get();
        existingJobCategory.setName(name);
        return this.jobCategoryRepository.save(existingJobCategory);

    }
}
