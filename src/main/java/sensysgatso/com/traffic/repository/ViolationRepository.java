package sensysgatso.com.traffic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sensysgatso.com.traffic.entity.Violation;

import java.util.UUID;

public interface ViolationRepository extends JpaRepository<Violation, UUID> {
}
