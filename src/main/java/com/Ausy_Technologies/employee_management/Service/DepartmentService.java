package com.Ausy_Technologies.employee_management.Service;

import com.Ausy_Technologies.employee_management.Model.DAO.Department;
import com.Ausy_Technologies.employee_management.Model.DAO.Employee;
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
public class DepartmentService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private JobCategoryRepository jobCategoryRepository;


    public Department saveDepartment(Department department) {

        if(department.getName().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Incorrect value!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }

        return this.departmentRepository.save(department);
    }

    public Department findDepartmentById(int id)
    {
        if(!this.departmentRepository.findById(id).isPresent()){
            CustomException customException = new CustomException(HttpStatus.NOT_FOUND, "Department not found!");
            CustomException.DisplayException(customException, Level.WARNING);
            throw customException;
        }
        return this.departmentRepository.findById(id).get();

    }

    public List<Department> findAllDepartments()
    {
        List<Department> departmentsList = this.departmentRepository.findAll();
        return departmentsList;
    }

    public List<Employee> listEmployeesInDepartment(int id){
        if(!this.departmentRepository.findById(id).isPresent()){
            CustomException customException = new CustomException(HttpStatus.NOT_FOUND, "Department not found!");
            CustomException.DisplayException(customException, Level.WARNING);
            throw customException;
        }
        List<Employee> employeeList = this.departmentRepository.findById(id).get().getEmployees();
        return employeeList;
    }

    public void deleteDepartmentById(int id){

        if(!this.departmentRepository.findById(id).isPresent()){
            CustomException customException = new CustomException(HttpStatus.NOT_FOUND, "Department not found!");
            CustomException.DisplayException(customException, Level.WARNING);
            throw customException;
        }

        this.departmentRepository.deleteById(id);
    }

    public Department updateDepartment(int id, String name){

        Optional<Department> optionalDepartment = this.departmentRepository.findById(id);

        if(!optionalDepartment.isPresent()){
            CustomException customException = new CustomException(HttpStatus.NOT_FOUND, "Department not found!");
            CustomException.DisplayException(customException, Level.WARNING);
            throw customException;
        }
        if(name.isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Incorrect value!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }

        Department existingDepartment = optionalDepartment.get();
        existingDepartment.setName(name);
        return this.departmentRepository.save(existingDepartment);

    }
}
