package com.philconnal.shoezone.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.philconnal.shoezone.entity.auditable.AuditableDomain;
import com.philconnal.shoezone.util.Constant;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Entity(name = "new_user")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class NewUser extends AuditableDomain<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false)

    private String first_name;
    @Column(nullable = false)

    private String last_name;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)

    private String address;
    @Column(nullable = false)

    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.API_FORMAT_DATE)
    private Date dob;
}
