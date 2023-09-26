package com.hcl.fsc.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
