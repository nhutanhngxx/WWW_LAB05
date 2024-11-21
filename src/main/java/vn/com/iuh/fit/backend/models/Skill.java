package vn.com.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "skill", schema = "works")
public class Skill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id", nullable = false)
    private Long id;

    @Column(name = "skill_desc")
    private String skillDesc;

    @Column(name = "skill_name")
    private String skillName;

    @Column(name = "skill_type")
    private Byte skillType;

}