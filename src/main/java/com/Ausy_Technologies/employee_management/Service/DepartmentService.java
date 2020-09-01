package com.Ausy_Technologies.employee_management.Service;

import com.Ausy_Technologies.employee_management.Model.DAO.Department;
import com.Ausy_Technologies.employee_management.Model.DAO.Employee;
import com.Ausy_Technologies.employee_management.Repository.DepartmentRepository;
import com.Ausy_Technologies.employee_management.Repository.EmployeeRepository;
import com.Ausy_Technologies.employee_management.Repository.JobCategoryRepository;
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

        return this.departmentRepository.save(department);
    }

    public Department findDepartmentById(int id)
    {
        if(!this.departmentRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found!");
        }
        return this.departmentRepository.findById(id).get();

    }

    public List<Department> findAllDepartments()
    {
        List<Department> departmentsList = this.departmentRepository.findAll();
        if(departmentsList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Content not found!");
        }
        return departmentsList;
    }

    public List<Employee> listEmployeesInDepartment(int id){
        if(!this.departmentRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found!");
        }
        List<Employee> employeeList = this.departmentRepository.findById(id).get().getEmployees();
        if(employeeList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No employees in department!");
        }
        return employeeList;
    }

    public void deleteDepartmentById(int id){

        if(!this.departmentRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found!");
        }
        if(!this.departmentRepository.findById(id).get().getEmployees().isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Department has employees!");
        }
        this.departmentRepository.deleteById(id);
    }

    public Department updateDepartment(int id, String name){

        Optional<Department> optionalDepartment = this.departmentRepository.findById(id);

        if(!optionalDepartment.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found!");
        }

        Department existingDepartment = optionalDepartment.get();
        existingDepartment.setName(name);
        return this.departmentRepository.save(existingDepartment);

    }
}
