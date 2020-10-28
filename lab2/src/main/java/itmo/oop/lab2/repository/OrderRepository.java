package itmo.oop.lab2.repository;

import itmo.oop.lab2.model.ProcessedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<ProcessedOrder, UUID> {
}
