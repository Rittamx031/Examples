package ecommecre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommecre.model.entity.PayDetail;
import ecommecre.model.entity.PayDetailId;

public interface PayDetailRepository extends JpaRepository<PayDetail, PayDetailId> {

}
