package com.philconnal.shoezone.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.philconnal.shoezone.common.enums.AppStatus;
import com.philconnal.shoezone.common.enums.UserRole;
import com.philconnal.shoezone.entity.auditable.AuditableDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "user")
@ToString
public class User extends AuditableDomain<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(nullable = false)
    private UserRole role;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private AppStatus status;
}
