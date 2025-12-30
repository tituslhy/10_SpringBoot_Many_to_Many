package com.luv2code.cruddemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="course")
@Getter
@Setter
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="title")
    private String title;

    /**
     * Note: We don't want cascade type to be all.
     */
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="instructor_id")
    private Instructor instructor;

    /**
     * If we delete the course we want to delete all associated reviews
     */
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="course_id")
    private List<Review> reviews;

    /**
     * Sets up many-to-many mapping. Remember to specify both the join and inverse join columns.
     * Join columns: specify the join column with respect to the table represented by this entity.
     * Inverse join columns: specify the join column on the reverse side.
     */
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    )
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name="course_id"),
            inverseJoinColumns = @JoinColumn(name="student_id")
    )
    private List<Student> students;

    public Course(String title) {
        this.title = title;
    }

    public void addStudent(Student theStudent){
        if (students == null){
            students = new ArrayList<>();
        }
        students.add(theStudent);
    }

    public void addReview(Review theReview){
        if (reviews == null){
            reviews = new ArrayList<>();
        }
        reviews.add(theReview);
    }

    /**
     * Remember to not include the Instructor class or toString
     * will fail
     * @return String
     */
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
