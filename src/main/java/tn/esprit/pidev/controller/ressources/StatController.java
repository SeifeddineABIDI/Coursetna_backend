package tn.esprit.pidev.controller.ressources;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pidev.services.ressources.StatService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatController {

    private final StatService statService;

    public StatController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping("/resources/added-by-week")
    public ResponseEntity<Long> getResourcesAddedByWeek(
            @RequestParam("startOfWeek") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startOfWeek,
            @RequestParam("endOfWeek") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endOfWeek) {
        Long count = statService.countRessourcesAddedByWeek(startOfWeek, endOfWeek);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/resources/by-option")
    public ResponseEntity<List<Object[]>> getResourcesByOption() {
        List<Object[]> stats = statService.countRessourcesByOption();
        return ResponseEntity.ok(stats);
    }
}
