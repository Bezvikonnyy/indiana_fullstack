package indiana.indi.indiana.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotNull
    @Size(min = 3, max = 50)
    private String title;

    @Column(name = "details")
    @NotNull
    @Size(min = 50, max = 2000)
    private String details;

    @Column(name = "image_url")
    @NotNull
    private String imageUrl;

    @Column(name = "game_file_url")
    @NotNull
    private String gameFileUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "game_category",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToMany(mappedBy = "purchasedGames")
    private Set<User> buyers;
}
