package com.bookstore.models;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "TB_ROLE")
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Getter
    public enum Values {
        ADMIN(1L),
        LIBRARIAN(2L);

        final long roleId;

        Values(long roleId) {
            this.roleId = roleId;
        }
    }
}
