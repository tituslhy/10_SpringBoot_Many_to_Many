package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Course;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;

import java.util.List;

public interface AppDAO {
    void save (Instructor theInstructor);

    Instructor findInstructorById(int theId);

    InstructorDetail findInstructorDetailById(int theId);

    void deleteInstructorById(int theId);

    /**
     * This method removes both the instructor detail AND the instructor
     * if cascade type is set to ALL in InstructorDetail
     * @param theId
     */
    void deleteInstructorDetailById(int theId);

    /**
     * The lazy loading method
     * @param theId
     * @return
     */
    List<Course> findCoursesByInstructorId(int theId);

    /**
     * The eager loading method using JoinFetch
     * @param theId
     * @return
     */
    Instructor findInstructorByIdJoinFetch(int theId);

    void update(Instructor tempInstructor);

    void update(Course course);

    Course findCourseById(int theId);

    void deleteCourseById(int theId);

    void save(Course theCourse);

    Course findCourseAndReviewsByCourseId(int theId);
}
