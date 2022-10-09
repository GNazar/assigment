package sensysgatso.com.traffic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sensysgatso.com.traffic.entity.Event;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
