package com.luv2code.cruddemo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="instructor_detail")
@NoArgsConstructor
@Getter
@Setter
public class InstructorDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="youtube_channel")
    private String youtubeChannel;

    @Column(name="hobby")
    private String hobby;

    /**
     * Add this if you want a bi-directional one-to-one mapping: i.e.
     * you can pull the instructor class out if you query for an instructor
     * detail. Otherwise, it's just a uni-directional one-to-one mapping
     * where the instructor can pull out an instructor detail class,
     * but not vice versa
     *
     * "instructorDetail" informs Spring Boot on how to use the field to
     * find the instructor class
     *
     * If you only want to delete the instructorDetail but not the instructor,
     * do not use CascadeType.ALL. Specify every cascade type except REMOVE and
     * ALL.
     */
    @OneToOne(
            mappedBy = "instructorDetail",
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            }
    )
    private Instructor instructor;

    public InstructorDetail(String youtubeChannel, String hobby) {
        this.youtubeChannel = youtubeChannel;
        this.hobby = hobby;
    }

    /**
     * If it's a bi-directional mapping you have to override the toString method to prevent
     * both classes from calling each other
     * @return String
     */
    @Override
    public String toString() {
        return "InstructorDetail{" +
                "id=" + id +
                ", youtubeChannel='" + youtubeChannel + '\'' +
                ", hobby='" + hobby + '\'' +
                '}'; // notice: NO instructor printed here
    }
}
