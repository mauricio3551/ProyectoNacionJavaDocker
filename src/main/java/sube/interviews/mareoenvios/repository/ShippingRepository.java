package sube.interviews.mareoenvios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sube.interviews.mareoenvios.entity.Shipping;

import java.util.List;

public interface ShippingRepository extends JpaRepository<Shipping, Integer> {
    List<Shipping> findBySendDateBetween(String sendDateFrom, String sendDateTo);
    List<Shipping> findByState(String state);
}
