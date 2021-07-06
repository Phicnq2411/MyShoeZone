package com.philconnal.shoezone.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.philconnal.shoezone.common.enums.AppStatus;
import com.philconnal.shoezone.common.enums.UserRole;
import com.philconnal.shoezone.entity.auditable.AuditableDomain;
import com.philconnal.shoezone.util.Constant;
import com.philconnal.shoezone.util.DateUtil;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
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
    @JsonFormat(pattern = Constant.API_FORMAT_DATE_TIME,shape = JsonFormat.Shape.STRING)
    private Date lastLoginDate;
    @JsonFormat(pattern = Constant.API_FORMAT_DATE_TIME,shape = JsonFormat.Shape.STRING)

    private Date logInDateDisplay;
    private boolean isActive;
    private boolean isNotLocked;

}
