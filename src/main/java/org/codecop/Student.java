package org.codecop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(name = "STUDENT_NAME", length = 50, nullable = true, unique = false)
    private String name;

    public Student() {
    }

    public Student(String string) {
        setName(string);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

// see https://www.baeldung.com/jpa-entities
