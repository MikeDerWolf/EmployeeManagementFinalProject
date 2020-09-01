package com.Ausy_Technologies.employee_management.Repository;

import com.Ausy_Technologies.employee_management.Model.DAO.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Override
    List<Department> findAll();

    @Override
    Optional<Department> findById(Integer integer);
}
