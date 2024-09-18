package ptithcm.tttn.entity;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long category_id;

    @Column
    private String category_name;

    @Column
    private String status;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private Long created_by;

    @Column
    private  Long updated_by;

    @ManyToOne
    @JoinColumn(name = "created_by",insertable = false, updatable = false)
    private Staff created_category;

    @ManyToOne
    @JoinColumn(name = "updated_by",insertable = false, updatable = false)
    private Staff updated_category;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> products;
}
