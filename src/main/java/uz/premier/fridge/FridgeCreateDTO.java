package uz.premier.fridge;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FridgeCreateDTO {
    private FridgeColor color;
    private FridgeModel model;
    private FridgeType type;
    private FridgeOrientation orientation;
    private Boolean nickel;
}