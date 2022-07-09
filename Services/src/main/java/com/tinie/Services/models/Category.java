package com.tinie.Services.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "category_details")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;

    @Column(name = "category_type", unique = true, nullable = false)
    private String name;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="category_id", nullable=false)
    @ToString.Exclude
    private Set<SubCategory> subCategories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Category category = (Category) o;
        return id == category.id &&
                name.equals(category.name) &&
                subCategories.equals(category.subCategories);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
