/**
 * Author: vanilson muhongo
 * Date:16/06/2025
 * Time:15:47
 * Version:
 */

package ufp.esof.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufp.esof.project.models.Review;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
