package com.luv2code.cruddemo;

import com.luv2code.cruddemo.dao.AppDAO;
import com.luv2code.cruddemo.entity.Course;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import com.luv2code.cruddemo.entity.Review;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

    /**
     * Shorthand command line interface runner
     * @param appDAO
     * @return
     */
    @Bean
    public CommandLineRunner commandLineRunner(AppDAO appDAO){
        return runner -> {

        };
    }

    private void deleteCourseAndReviews(AppDAO appDAO) {
        int theId = 10;
        appDAO.deleteCourseById(theId);
    }

    private void retrieveCourseAndReviews(AppDAO appDAO) {
        int theId = 10;
        Course theCourse = appDAO.findCourseAndReviewsByCourseId(theId);
        System.out.println(theCourse);
        System.out.println(theCourse.getReviews());
    }

    private void createCourseAndReviews(AppDAO appDAO) {
        System.out.println("Saving the course 'Pacman - How to score one million points'");

        // create a course
        Course tempCourse = new Course("Pacman - How to score one million points");

        // add some reviews
        tempCourse.addReview(new Review("Great course...loved it!"));
        tempCourse.addReview(new Review("Cool course job well done"));
        tempCourse.addReview(new Review("What a dumb course, you are an idiot"));

        // save the course
        appDAO.save(tempCourse);

        System.out.println(tempCourse);
        System.out.println(tempCourse.getReviews());
    }

    private void deleteCourse(AppDAO appDAO) {
        int theId = 10;
        appDAO.deleteCourseById(theId);
        System.out.println("Done!");
    }

    private void updateCourse(AppDAO appDAO) {
        int theId = 10;
        System.out.println("Finding the course id: " + theId);
        Course course = appDAO.findCourseById(theId);

        System.out.println("Updating course");
        course.setTitle("Enjoy the simple things");

        appDAO.update(course);
        System.out.println("Done!");
    }

    private void updateInstructor(AppDAO appDAO) {
        int theId = 1;
        System.out.println("Finding instructor id: " + theId);
        Instructor tempInstructor = appDAO.findInstructorById(theId);

        // update instructor
        System.out.println("Updating instructor...");
        tempInstructor.setLast_name("TESTER");
        appDAO.update(tempInstructor);

        System.out.println("Instructor updated: " + tempInstructor);
        System.out.println("Done");
    }

    private void findInstructorWithCoursesJoinFetch(AppDAO appDAO) {
        int theId = 1;
        System.out.println("Finding instructor id: " + theId);

        Instructor tempInstructor = appDAO.findInstructorByIdJoinFetch(theId);
        System.out.println("The instructor: " + tempInstructor);
        System.out.println("Associated courses: " + tempInstructor.getCourses());

        System.out.println("Done");
    }

    /**
     * One way of overcoming the active Hibernate session requirement for lazy loading is to create another
     * method and call it specifically (appDAO.findCoursesByInstructorId) in the same method.
     * @param appDAO
     */
    private void findCoursesForInstructor(AppDAO appDAO) {
        int theId = 1;
        System.out.println("Finding instructor id: " + theId);

        Instructor tempInstructor = appDAO.findInstructorById(theId);
        System.out.println("The instructor: " + tempInstructor);

        // find courses for instructor - you have to
        System.out.println("Finding courses for instructor id: " + theId);
        List<Course> courses = appDAO.findCoursesByInstructorId(theId);

        // Associate the objects
        tempInstructor.setCourses(courses);

        System.out.println("Associated courses: " + tempInstructor.getCourses());
        System.out.println("Done!");
    }

    /**
     * This method only works for EAGER loading - set fetch type to EAGER in Instructor.Java's courses attribute
     * or this will throw an exception
     * @param appDAO
     */
    private void findInstructorWithCourses(AppDAO appDAO) {
        int theId = 1;
        System.out.println("Finding instructor id: " + theId);

        Instructor tempInstructor = appDAO.findInstructorById(theId);
        System.out.println("The instructor: " + tempInstructor);
        System.out.println("Associated courses: " + tempInstructor.getCourses());

        System.out.println("Done");

    }

    private void createInstructorWithCourses(AppDAO appDAO) {
        // Create the instructor
        Instructor tempInstructor = new Instructor("Chad", "Darby", "darby@luv2code.com");

        // Create the instructor detail
        InstructorDetail tempInstructorDetail = new InstructorDetail(
                "http://www.luv2code.com/youtube",
                "Luv 2 code!!!"
        );

        // Associate the objects - you need to set the instruction detail in your instructor
        tempInstructor.setInstructorDetail(tempInstructorDetail);

        // Create courses
        Course tempCourse1 = new Course("Air Guitar");
        Course tempCourse2 = new Course("Pinball Masterclass");

        // Add courses to instructor
        tempInstructor.add(tempCourse1);
        tempInstructor.add(tempCourse2);

        // Save the instructor. Note this will also save the details and courses object
        // because of CascadeType.PERSIST
        System.out.println("Saving instructor: " + tempInstructor);
        System.out.println("The courses: " + tempInstructor.getCourses());
        appDAO.save(tempInstructor);

        System.out.println("Done!");
    }

    private void deleteInstructorDetail(AppDAO appDAO) {
        int theId = 4;
        System.out.println("Deleting instructor detail id: " + theId);
        appDAO.deleteInstructorDetailById(theId);
        System.out.println("Done");
    }

    private void findInstructorDetail(AppDAO appDAO) {
        int theId = 1;
        InstructorDetail detail = appDAO.findInstructorDetailById(theId);
        System.out.println(detail);
        System.out.println(detail.getInstructor());
        System.out.println("Done");
    }

    private void deleteInstructor(AppDAO appDAO) {
        int theId = 1;
        System.out.println("Deleting instructor: " +  theId);
        appDAO.deleteInstructorById(theId);
        System.out.println("Done!");
    }

    private void findInstructor(AppDAO appDAO) {
        int theId = 1;
        System.out.println("Finding the instructor of id:  " + theId);

        Instructor tempInstructor = appDAO.findInstructorById(theId);
        System.out.println("Instructor: " + tempInstructor);
        System.out.println("Instructor details: " + tempInstructor.getInstructorDetail());
    }

    private void createInstructor(AppDAO appDAO) {

//        // Create the instructor
//        Instructor tempInstructor = new Instructor("Chad", "Darby", "darby@luv2code.com");
//
//        // Create the instructor detail
//        InstructorDetail tempInstructorDetail = new InstructorDetail(
//                "http://www.luv2code.com/youtube",
//                "Luv 2 code!!!"
//        );

        // Create the instructor
        Instructor tempInstructor = new Instructor("Madhu", "Patel", "madhuy@luv2code.com");

        // Create the instructor detail
        InstructorDetail tempInstructorDetail = new InstructorDetail(
                "http://www.luv2code.com/youtube",
                "Guitar"
        );

        // Associate the objects - you need to set the instruction detail in your instructor
        tempInstructor.setInstructorDetail(tempInstructorDetail);

        // Save the instructor. Note this will also save the details object
        // because of CascadeType.ALL
        System.out.println("Saving instructor: " + tempInstructor);

        appDAO.save(tempInstructor);

        System.out.println("Done!");
    }

}
