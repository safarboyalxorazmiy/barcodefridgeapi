package uz.premier.fridge;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "fridge")
public class FridgeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private FridgeColor color;

    private FridgeModel model;

    private FridgeType type;

    private FridgeOrientation orientation;

    private Boolean nickel;

    private String seriya;

    private LocalDateTime createdDate = LocalDateTime.now();
}
