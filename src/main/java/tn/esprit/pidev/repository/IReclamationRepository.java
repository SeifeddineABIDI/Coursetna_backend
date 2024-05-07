package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Reclamtion;
import tn.esprit.pidev.entities.TypeStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IReclamationRepository extends JpaRepository<Reclamtion,Long> {
/*Queries*/
    @Query("select r from Reclamtion r join r.responses res where r.user.id = :id and res.idrep = :idrep")
    public Reclamtion getReclamationByUserAndResponse(@Param("id") Integer id, @Param("idrep") long idrep);
    @Query("select r from Reclamtion r where r.status = 'traite' and r.user.id = :userId")
    List<Reclamtion> getTraitedReclamationsByUserId(@Param("userId") long userId);


    @Query("select r from Reclamtion r join fetch r.user join fetch r.responses")
    public List<Reclamtion> findAllReclamationsWithUserAndResponse();

    @Query("SELECT r FROM Reclamtion r JOIN FETCH r.user JOIN FETCH r.topic ")
    public List<Reclamtion> findAllWithDetails();

    @Query("SELECT r FROM Reclamtion r WHERE r.user.id = :idUser AND r.topic.id = :idTopic")
    List<Reclamtion> findReclamationsByUserAndTopic(@Param("idUser") Integer idUser, @Param("idTopic") Long idTopic);

    List<Reclamtion> findByUserId(Integer userId);

    Optional<Reclamtion> findByIdrecAndUserId(Long idrec, Integer userId);

    @Query("SELECT r FROM Reclamtion r LEFT JOIN FETCH r.responses")
    List<Reclamtion> findAllWithResponses();
    @Query("SELECT r FROM Reclamtion r WHERE r.creationDate < :timestamp AND r.status = :status")
    List<Reclamtion> findUnattendedReclamationsWithTypeStatus(@Param("timestamp") LocalDateTime timestamp, @Param("status") TypeStatus status);


    List<Reclamtion> findByTitreContainingAndDescriptionContainingAndStatus(String titre, String description, String status);

    long countByStatus(TypeStatus status);
}
