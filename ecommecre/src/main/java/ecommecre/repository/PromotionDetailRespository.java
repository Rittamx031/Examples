package ecommecre.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommecre.model.entity.PromotionDetail;

/**
 * PromotionDetailRespository
 */
public interface PromotionDetailRespository extends JpaRepository<PromotionDetail , UUID > {

}
