package com.Ausy_Technologies.employee_management.Service;

import com.Ausy_Technologies.employee_management.Model.DAO.Department;
import com.Ausy_Technologies.employee_management.Model.DAO.Employee;
import com.Ausy_Technologies.employee_management.Model.DAO.JobCategory;
import com.Ausy_Technologies.employee_management.Model.DTO.EmployeeDto;
import com.Ausy_Technologies.employee_management.Repository.DepartmentRepository;
import com.Ausy_Technologies.employee_management.Repository.EmployeeRepository;
import com.Ausy_Technologies.employee_management.Repository.JobCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private JobCategoryRepository jobCategoryRepository;



    public Employee saveEmployee(Employee employee){

        return this.employeeRepository.save(employee);
    }


    public Employee saveEmployeeDJc(Employee employee, int idDepartment,int idJobCategory){

        Optional<Department> optDepartment = this.departmentRepository.findById(idDepartment);
        Optional<JobCategory> optJobCategory = this.jobCategoryRepository.findById(idJobCategory);

        if(!optDepartment.isPresent()&&!optJobCategory.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department and job category not found!");
        }
        else if(!optJobCategory.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job category not found!");
        } else if(!optDepartment.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found!");
        }
        else
        {
            employee.setJobCategory(optJobCategory.get());
            employee.setDepartment(optDepartment.get());
            return this.employeeRepository.save(employee);
        }
    }


    public Employee findEmployeeById(int id) {

        if(!this.employeeRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!");
        }
        return this.employeeRepository.findById(id).get();
    }


    public List<Employee> findAllEmployees()
    {
        List<Employee> employeeList = this.employeeRepository.findAll();
        if(employeeList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Content not found!");
        }
        return employeeList;
    }


    public void deleteEmployeeById(int id){

        if(!this.employeeRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!");
        }
        this.employeeRepository.deleteById(id);
    }


    public Employee changeDepartment(int idEmployee, int idDepartment){
        if(!this.employeeRepository.findById(idEmployee).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!");
        }
        if(!this.departmentRepository.findById(idDepartment).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found!");
        }
        Employee existingEmployee = this.employeeRepository.findById(idEmployee).get();
        Department existingDepartment = this.departmentRepository.findById(idDepartment).get();
        existingEmployee.setDepartment(existingDepartment);
        return this.employeeRepository.save(existingEmployee);
    }

    public Employee changeJobCategory(int idEmployee, int idJobCategory){
        if(!this.employeeRepository.findById(idEmployee).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!");
        }
        if(!this.jobCategoryRepository.findById(idJobCategory).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job category not found!");
        }
        Employee existingEmployee = this.employeeRepository.findById(idEmployee).get();
        JobCategory existingJobCategory = this.jobCategoryRepository.findById(idJobCategory).get();
        existingEmployee.setJobCategory(existingJobCategory);
        return this.employeeRepository.save(existingEmployee);
    }

    public Employee updateEmployee(Employee employee, int id){

        Optional<Employee> optionalEmployee = this.employeeRepository.findById(id);

        if(!optionalEmployee.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!");
        }

        Employee existingEmployee = optionalEmployee.get();


        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setManager(employee.isManager());
        existingEmployee.setStartDate(employee.getStartDate());
        existingEmployee.setEndDate(employee.getEndDate());
        existingEmployee.setActive(employee.isActive());
        existingEmployee.setAddress(employee.getAddress());
        existingEmployee.setZipcode(employee.getZipcode());
        existingEmployee.setTelephone(employee.getTelephone());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setBirthday(employee.getBirthday());
        existingEmployee.setNoChildren(employee.getNoChildren());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.setStudies(employee.getStudies());
        existingEmployee.setSocialSecurityNumber(employee.getSocialSecurityNumber());
        existingEmployee.setHasDrivingLicense(employee.getHasDrivingLicense());

        return this.employeeRepository.save(existingEmployee);

    }

    public EmployeeDto convertEmployeeToDto(Employee employee){

        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setStartDate(employee.getStartDate());
        employeeDto.setEndDate(employee.getEndDate());
        employeeDto.setManager(employee.isManager());
        employeeDto.setActive(employee.isActive());
        employeeDto.setDepartment(employee.getDepartment().getName());
        employeeDto.setJobCategory(employee.getJobCategory().getName());

        return employeeDto;
    }

    public EmployeeDto findEmployeeDtoById(int id) {

        Optional<Employee> optionalEmployee = this.employeeRepository.findById(id);

        if(!optionalEmployee.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!");
        }

        Employee employee = optionalEmployee.get();

        return convertEmployeeToDto(employee);
    }

    public List<EmployeeDto> findAllEmployeesDto(){

        List<Employee> employeeList = this.employeeRepository.findAll();

        if(employeeList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Content not found!");
        }

        List<EmployeeDto> employeeDtoList = employeeList.stream().map(e->convertEmployeeToDto(e)).
                collect(Collectors.toList());

        return employeeDtoList;
    }

}
