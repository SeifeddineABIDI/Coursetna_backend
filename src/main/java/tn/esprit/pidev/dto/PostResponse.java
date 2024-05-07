package tn.esprit.pidev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String postName;
    private String url;
    private String imageUrl;
    private String description;
    private String email;
    private String subforumName;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
    private boolean like;
    private boolean dislike;
    public String nom;
    public String prenom;
    public int userId;


}