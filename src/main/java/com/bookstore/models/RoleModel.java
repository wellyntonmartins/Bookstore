package com.bookstore.models;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_ROLE")
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public enum Values {
        ADMIN(1L),
        LIBRARIAN(2L);

        long roleId;

        Values(long roleId) {
            this.roleId = roleId;
        }

        public long getRoleId() {
            return roleId;
        }
    }
}
