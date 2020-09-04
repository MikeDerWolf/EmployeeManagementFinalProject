package com.Ausy_Technologies.employee_management.Repository;

import com.Ausy_Technologies.employee_management.Model.DAO.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findById(Integer integer);

    List<Employee> findAllByOrderBySalary();

    List<Employee> findAllByOrderByLastName();
}
