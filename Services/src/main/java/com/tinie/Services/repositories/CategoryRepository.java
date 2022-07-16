package com.tinie.Services.repositories;

import com.tinie.Services.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> { }
