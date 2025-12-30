package com.luv2code.cruddemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * You need to exclude the "courses" attribute from ToString because it is lazy loaded.
 */
@Entity
@Table(name="instructor")
@ToString(exclude="courses")
@Getter
@Setter
@NoArgsConstructor
public class Instructor {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="first_name")
    private String first_name;

    @Column(name="last_name")
    private String last_name;

    @Column(name="email")
    private String email;

    /**
     * THIS IS THE PART TO TAKE NOTE OF
     * Cascade type = ALL means changing this will also edit the corresponding
     * linked table. Specify join column with the exact column name.
     */
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="instructor_detail_id")
    private InstructorDetail instructorDetail;

    /**
     * Map by the instructor property in the course class
     * You don't have to specify "LAZY" because it's the default. But I'm just doing this
     * for clarity.
     */
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy="instructor",
            cascade={CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH}
    )
    private List<Course> courses;

    public Instructor(String first_name, String last_name, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    /**
     * Add convenience method for bidirectional relationship between courses and Instructor
     * @param tempCourse
     */
    public void add (Course tempCourse){
        if (courses == null){
            courses = new ArrayList<>();
        }
        courses.add(tempCourse);
        tempCourse.setInstructor(this);
    }
}
