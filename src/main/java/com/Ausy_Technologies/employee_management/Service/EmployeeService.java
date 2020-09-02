package com.Ausy_Technologies.employee_management.Service;

import com.Ausy_Technologies.employee_management.Model.DAO.Department;
import com.Ausy_Technologies.employee_management.Model.DAO.Employee;
import com.Ausy_Technologies.employee_management.Model.DAO.JobCategory;
import com.Ausy_Technologies.employee_management.Model.DTO.EmployeeDto;
import com.Ausy_Technologies.employee_management.Repository.DepartmentRepository;
import com.Ausy_Technologies.employee_management.Repository.EmployeeRepository;
import com.Ausy_Technologies.employee_management.Repository.JobCategoryRepository;
import com.Ausy_Technologies.employee_management.RestErrorHandling.CustomException;
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
        if(employee.getDepartment()==null){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing department!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        } else if(!this.departmentRepository.findById(employee.getDepartment().getId()).isPresent()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Department doesn't exist!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getJobCategory()==null){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing job category!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        } else if(!this.jobCategoryRepository.findById(employee.getJobCategory().getId()).isPresent()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Job category doesn't exist!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getFirstName()==null||employee.getFirstName().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing first name!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getLastName()==null||employee.getLastName().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing last name!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getStartDate()==null){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing start date!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getAddress()==null||employee.getAddress().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing address!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getZipcode()==null||employee.getZipcode().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing zipcode!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getTelephone()==null||employee.getTelephone().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing telephone!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getBirthday()==null){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing birthday!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getSalary()==null||employee.getSalary()<0){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing correct salary!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }

        return this.employeeRepository.save(employee);
    }


    public Employee saveEmployeeDJc(Employee employee, int idDepartment,int idJobCategory){

        Optional<Department> optDepartment = this.departmentRepository.findById(idDepartment);
        Optional<JobCategory> optJobCategory = this.jobCategoryRepository.findById(idJobCategory);

        if(!optDepartment.isPresent()&&!optJobCategory.isPresent()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Department and job category don't exist!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        else if(!optJobCategory.isPresent()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Job category doesn't exist!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        } else if(!optDepartment.isPresent()) {
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Department doesn't exist!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }

        if(employee.getFirstName()==null||employee.getFirstName().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing first name!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getLastName()==null||employee.getLastName().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing last name!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getStartDate()==null){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing start date!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getAddress()==null||employee.getAddress().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing address!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getZipcode()==null||employee.getZipcode().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing zipcode!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getTelephone()==null||employee.getTelephone().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing telephone!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getBirthday()==null){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing birthday!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getSalary()==null||employee.getSalary()<0){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing correct salary!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }

        employee.setJobCategory(optJobCategory.get());
        employee.setDepartment(optDepartment.get());
        return this.employeeRepository.save(employee);

    }


    public Employee findEmployeeById(int id) {

        if(!this.employeeRepository.findById(id).isPresent()){
            CustomException customException = new CustomException(HttpStatus.NOT_FOUND, "Employee not found!");
            CustomException.DisplayException(customException, Level.WARNING);
            throw customException;
        }
        return this.employeeRepository.findById(id).get();
    }


    public List<Employee> findAllEmployees()
    {
        List<Employee> employeeList = this.employeeRepository.findAll();
        return employeeList;
    }


    public void deleteEmployeeById(int id){

        if(!this.employeeRepository.findById(id).isPresent()){
            CustomException customException = new CustomException(HttpStatus.NOT_FOUND, "Employee not found!");
            CustomException.DisplayException(customException, Level.WARNING);
            throw customException;
        }
        this.employeeRepository.deleteById(id);
    }


    public Employee changeDepartment(int idEmployee, int idDepartment){
        if(!this.employeeRepository.findById(idEmployee).isPresent()){
            CustomException customException = new CustomException(HttpStatus.NOT_FOUND, "Employee not found!");
            CustomException.DisplayException(customException, Level.WARNING);
            throw customException;
        }
        if(!this.departmentRepository.findById(idDepartment).isPresent()){
            CustomException customException = new CustomException(HttpStatus.NOT_FOUND, "Department not found!");
            CustomException.DisplayException(customException, Level.WARNING);
            throw customException;
        }
        Employee existingEmployee = this.employeeRepository.findById(idEmployee).get();
        Department existingDepartment = this.departmentRepository.findById(idDepartment).get();
        existingEmployee.setDepartment(existingDepartment);
        return this.employeeRepository.save(existingEmployee);
    }

    public Employee changeJobCategory(int idEmployee, int idJobCategory){
        if(!this.employeeRepository.findById(idEmployee).isPresent()){
            CustomException customException = new CustomException(HttpStatus.NOT_FOUND, "Employee not found!");
            CustomException.DisplayException(customException, Level.WARNING);
            throw customException;
        }
        if(!this.jobCategoryRepository.findById(idJobCategory).isPresent()){
            CustomException customException = new CustomException(HttpStatus.NOT_FOUND, "Job category not found!");
            CustomException.DisplayException(customException, Level.WARNING);
            throw customException;
        }
        Employee existingEmployee = this.employeeRepository.findById(idEmployee).get();
        JobCategory existingJobCategory = this.jobCategoryRepository.findById(idJobCategory).get();
        existingEmployee.setJobCategory(existingJobCategory);
        return this.employeeRepository.save(existingEmployee);
    }

    public Employee updateEmployee(Employee employee, int id){

        Optional<Employee> optionalEmployee = this.employeeRepository.findById(id);

        if(!optionalEmployee.isPresent()){
            CustomException customException = new CustomException(HttpStatus.NOT_FOUND, "Employee not found!");
            CustomException.DisplayException(customException, Level.WARNING);
            throw customException;
        }
        if(employee.getDepartment()==null){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing department!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        } else if(!this.departmentRepository.findById(employee.getDepartment().getId()).isPresent()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Department doesn't exist!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getJobCategory()==null){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing job category!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        } else if(!this.jobCategoryRepository.findById(employee.getJobCategory().getId()).isPresent()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Job category doesn't exist!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getFirstName()==null||employee.getFirstName().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing first name!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getLastName()==null||employee.getLastName().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing last name!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getStartDate()==null){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing start date!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getAddress()==null||employee.getAddress().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing address!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getZipcode()==null||employee.getZipcode().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing zipcode!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getTelephone()==null||employee.getTelephone().isEmpty()){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing telephone!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getBirthday()==null){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing birthday!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }
        if(employee.getSalary()==null||employee.getSalary()<0){
            CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, "Missing correct salary!");
            CustomException.DisplayException(customException, Level.SEVERE);
            throw customException;
        }

        Employee existingEmployee = optionalEmployee.get();


        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setDepartment(this.departmentRepository.findById(employee.getDepartment().getId()).get());
        existingEmployee.setJobCategory(this.jobCategoryRepository.findById(employee.getJobCategory().getId()).get());
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
            CustomException customException = new CustomException(HttpStatus.NOT_FOUND, "EmployeeDTO not found!");
            CustomException.DisplayException(customException, Level.WARNING);
            throw customException;
        }

        Employee employee = optionalEmployee.get();

        return convertEmployeeToDto(employee);
    }

    public List<EmployeeDto> findAllEmployeesDto(){

        List<Employee> employeeList = this.employeeRepository.findAll();

        List<EmployeeDto> employeeDtoList = employeeList.stream().map(e->convertEmployeeToDto(e)).
                collect(Collectors.toList());

        return employeeDtoList;
    }

    public List<EmployeeDto> findEmployeesByDep(int departmentId){
        List<Employee> employeeList = this.employeeRepository.findAll().stream().filter(e -> e.getDepartment().getId() == departmentId).
                collect(Collectors.toList());;

        List<EmployeeDto> employeeDtoList = employeeList.stream().map(e->convertEmployeeToDto(e)).
                collect(Collectors.toList());

        return employeeDtoList;
    }

    public List<EmployeeDto> findEmployeesByJob(int jobCategoryId){
        List<Employee> employeeList = this.employeeRepository.findAll().stream().filter(e -> e.getJobCategory().getId() == jobCategoryId).
                collect(Collectors.toList());;

        List<EmployeeDto> employeeDtoList = employeeList.stream().map(e->convertEmployeeToDto(e)).
                collect(Collectors.toList());

        return employeeDtoList;
    }

    public List<EmployeeDto> findEmployeesByDepAndJob(int departmentId, int jobCategoryId){
        List<Employee> employeeList = this.employeeRepository.findAll().stream().filter(e -> e.getDepartment().getId() == departmentId
        && e.getJobCategory().getId() == jobCategoryId).
                collect(Collectors.toList());;

        List<EmployeeDto> employeeDtoList = employeeList.stream().map(e->convertEmployeeToDto(e)).
                collect(Collectors.toList());

        return employeeDtoList;
    }
}
