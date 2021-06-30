package com.philconnal.shoezone.entity;

import com.philconnal.shoezone.entity.auditable.AuditableDomain;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity(name = "branch")
public class Branch extends AuditableDomain<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)

    private String name;
    @Column(nullable = false)

    private String address;
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Product> products;
}
