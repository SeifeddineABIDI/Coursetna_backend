package tn.esprit.pidev.controller;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.services.StatService;
import tn.esprit.pidev.services.StatService;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/stats")
@CrossOrigin
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
