package uz.premier.fridge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeRepository extends JpaRepository<FridgeEntity, Long> {
    long countByColor(FridgeColor color);
}