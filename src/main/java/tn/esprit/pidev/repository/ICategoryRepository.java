package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Category;
@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
