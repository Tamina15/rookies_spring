/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rookiesspring.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rookiesspring.dto.response.custom.CategoryResponseDTOShort;
import rookiesspring.model.Category;

/**
 *
 * @author HP
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    @Query(value = "select c from Category c left join fetch c.products")
    List<Category> findAll();

    /**
     * For Join Table Use Only
     *
     * @param id
     * @return
     */
//    @Override
//    @Query(value = "select c from Category c join fetch c.products join fetch c.products.product")
//    List<Category> findAll();
    @Query(value = "select c from Category c join c.products where c.id = ?1")
    Optional<Category> findId(long id);

    List<CategoryResponseDTOShort> findAllProjectedBy();

    Optional<CategoryResponseDTOShort> findOneProjectedById(long id);

    List<Category> findAllByNameContainsIgnoreCase(String name);

    public List<CategoryResponseDTOShort> findAllProjectedByNameContainsIgnoreCase(String name);

    boolean existsById(long id);

}