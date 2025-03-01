package uk.theoneamin.starling.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.theoneamin.starling.entity.Base.BaseEntity;

@Data
@Entity
@Table(name = "account_entity")
@EqualsAndHashCode(callSuper = true)
public class AccountEntity extends BaseEntity {

    @Column(name = "account_uid")
    private String accountUid;

    @Column(name = "name")
    private String name;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "default_category")
    private String defaultCategory;

    @Column(name = "currency")
    private String currency;

    @Column(name = "created_at")
    private String createdAt;
}
