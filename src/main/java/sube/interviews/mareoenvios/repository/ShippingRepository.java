package sube.interviews.mareoenvios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sube.interviews.mareoenvios.entity.Shipping;

public interface ShippingRepository extends JpaRepository<Shipping, Integer> {
}
