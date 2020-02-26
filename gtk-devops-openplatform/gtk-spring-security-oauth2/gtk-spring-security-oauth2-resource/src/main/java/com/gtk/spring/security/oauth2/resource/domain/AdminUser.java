package com.gtk.spring.security.oauth2.resource.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "admin_user_0")
public class AdminUser implements Serializable {
    @Column(name = "id")
    private Integer id;

    @Column(name = "`role`")
    private Integer role;

    private static final long serialVersionUID = 1L;
}