package com.tinie.Services.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "business_details")
public class BusinessDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_id")
    private int id;

    @Column(name = "business_type", unique = true, nullable = false)
    private String name;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "longitude")
    private float longitude;

    @ManyToOne
    @JoinColumn(name="subcategory_id")
    private SubCategory subCategory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BusinessDetails that = (BusinessDetails) o;
        return this.id == that.id && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
