package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Course;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO{

    // define field for entity manager
    private EntityManager entityManager;

    // inject entity manager using constructor injection
    @Autowired
    public AppDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor theInstructor) {
        entityManager.persist(theInstructor);
    }

    @Override
    public Instructor findInstructorById(int theId) {
        return entityManager.find(Instructor.class, theId);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return entityManager.find(InstructorDetail.class, theId);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int theId) {

        // retrieve instructor
        Instructor tempInstructor = entityManager.find(Instructor.class, theId);

        // retrieve courses
        List<Course> courses = tempInstructor.getCourses();

        // break association of all courses for the instructor
        for (Course tempCourse : courses){
            tempCourse.setInstructor(null);
        }

        // delete the instructor
        entityManager.remove(tempInstructor);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int theId) {
        InstructorDetail tempDetails = entityManager.find(InstructorDetail.class, theId);

        // Remove the associated object reference to break bi-directional link
        System.out.println("Setting instructor detail to null");
        tempDetails.getInstructor().setInstructorDetail(null);

        entityManager.remove(tempDetails);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int theId) {

        // Create query
        TypedQuery<Course> query = entityManager.createQuery(
                "from Course where instructor.id = :data",
                Course.class
        );
        query.setParameter("data", theId);

        // Execute query
        List<Course> courses = query.getResultList();

        return courses;
    }

    /**
     * `JOIN FETCH` is similar to eager loading. The code will still retrieve Instructor AND
     * courses. You're using two join fetches here because the instructor table is also linked
     * to the instructor detail which automatically fetches it. if you only have on join fetch,
     * two SQL queries are ran to also automatically retrieve Instructor Detail.
     * @param theId
     * @return
     */
    @Override
    public Instructor findInstructorByIdJoinFetch(int theId) {

        // Create query
        String hql = """
        select i from Instructor i
        join fetch i.courses
        join fetch i.instructorDetail
        where i.id = :data
        """;
        TypedQuery<Instructor> query = entityManager.createQuery(hql, Instructor.class);
        query.setParameter("data", theId);

        // Execute query
        Instructor instructor = query.getSingleResult();
        return instructor;
    }

    @Override
    @Transactional
    public void update(Instructor tempInstructor) {
        entityManager.merge(tempInstructor);
    }

    @Override
    @Transactional
    public void update(Course course) {
        entityManager.merge(course);
    }

    @Override
    public Course findCourseById(int theId) {
        return entityManager.find(Course.class, theId);
    }

    @Override
    @Transactional
    public void deleteCourseById(int theId) {
        Course tempCourse = entityManager.find(Course.class, theId);
        entityManager.remove(tempCourse);
    }

    /**
     * Saves the course and all associated reviews due to the cascade
     * @param theCourse
     */
    @Override
    @Transactional
    public void save(Course theCourse) {
        entityManager.persist(theCourse);
    }

    /**
     * Note that because we are also finding the reviews we must use join fetch
     * @param theId
     * @return Course
     */
    @Override
    public Course findCourseAndReviewsByCourseId(int theId) {

        String sql_query = """
                select c from Course c
                join fetch c.reviews
                where c.id = :data
                """;

        TypedQuery<Course> query = entityManager.createQuery(sql_query, Course.class);
        query.setParameter("data", theId);

        Course course = query.getSingleResult();
        return course;
    }

}
