package tn.esprit.pidev.controller.ressources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.ressources.VersionRessource;
import tn.esprit.pidev.services.ressources.IGestionVersionRess;

import java.util.List;

@RestController
@RequestMapping("/version")
public class ControllerVersionRess {
  @Autowired
    private IGestionVersionRess versionservice;

  @GetMapping("/getAll")
  public List<VersionRessource> listerVers() {
      return versionservice.recupererToutesLesVersions();
  }
    @PostMapping("/addVersion")
    public VersionRessource addVersion(@RequestBody VersionRessource versionRessource) {
        return versionservice.creerNouvelleVersion(versionRessource);
    }

    @DeleteMapping("/removeVersion/{id}")
    public String removeById(@PathVariable("id") long id) {
        versionservice.supprimerVersion(id);
        return "version avec l'ID " + id + " supprimé avec succès";
    }

}
