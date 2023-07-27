package uz.premier.fridge;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fridge")
@AllArgsConstructor
public class FridgeController {
    private final FridgeService fridgeService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> addFridge(@RequestBody FridgeCreateDTO dto) {
        Boolean isCreated = fridgeService.create(dto);
        return ResponseEntity.ok(isCreated);
    }

}
