package com.philconnal.shoezone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.philconnal.shoezone.common.enums.ProductStatus;
import com.philconnal.shoezone.common.enums.ProductType;
import com.philconnal.shoezone.entity.auditable.AuditableDomain;
import com.philconnal.shoezone.util.ParamError;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity(name = "product")
public class Product extends AuditableDomain<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = ParamError.FIELD_NAME)
    private String name;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
    @Column(nullable = false)
    @NotNull(message = ParamError.FIELD_NAME)

    private Double price;
    @Column(nullable = false)
    @NotBlank(message = ParamError.FIELD_NAME)

    private String imgUrl;
    @Column(nullable = false)

    private ProductStatus status;
    @Column(nullable = false)
    @NotBlank(message = ParamError.FIELD_NAME)

    private String description;
    @Column(nullable = false)

    private ProductType type;
    // discount, evaluate
}
