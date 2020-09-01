package com.Ausy_Technologies.employee_management.Repository;

import com.Ausy_Technologies.employee_management.Model.DAO.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, Integer> {

    @Override
    List<JobCategory> findAll();

    @Override
    Optional<JobCategory> findById(Integer integer);
}
