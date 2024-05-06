package ecommecre.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommecre.model.entity.Promotion;

/**
 * PromotionRespository
 */
public interface PromotionRespository extends JpaRepository< Promotion , UUID> {

}
