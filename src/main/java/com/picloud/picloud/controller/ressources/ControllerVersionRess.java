package com.picloud.picloud.controller.ressources;

import com.picloud.picloud.entities.ressources.VersionRessource;
import com.picloud.picloud.services.ressources.IGestionVersionRess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
