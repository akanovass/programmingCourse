package bitlab.academyProject.SpringProject.services;

import bitlab.academyProject.SpringProject.models.*;
import bitlab.academyProject.SpringProject.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService{



    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordHistoryRepository passwordHistoryRepository;

    private final CourseRepository courseRepository;

    private final TopicRepository topicRepository;

    private final TeacherRepository teacherRepository;

    private final TaskRepository taskRepository;

    private final AnswerRepository answerRepository;

    private final RequestRepository requestRepository;

    public AuthUser findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


    public AuthUser getUser(Long id){
        AuthUser user=userRepository.getById(id);
        return user;
    }


    public Topics getTopic(Long id){
        Topics topic=topicRepository.getById(id);
        return topic;
    }


    public Courses addCourse(Courses courses){
        return courseRepository.save(courses);
    }


    public Request addRequest(Request request){
        return requestRepository.save(request);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthUser user=userRepository.findByEmail(email);
        if(user!=null){
            return user;
        }else {
            throw new UsernameNotFoundException("USER NOT FOUND EXCEPTION");
        }
    }

    public Courses saveCourse(Courses courses){
        return courseRepository.save(courses);
    }


    public AuthUser registerUser(AuthUser user){

        AuthRole role=roleRepository.findByRole("ROLE_USER");
        if(role!=null) {
            List<AuthRole> roles = new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
            AuthUser newUser = userRepository.save(user);
            PasswordHistory history=new PasswordHistory();
            history.setSaveDate(new Date());
            history.setPassword(newUser.getPassword());
            history.setUser(newUser);
            passwordHistoryRepository.save(history);

            return newUser;
        }
        return null;
    }


    public List<Courses> getAllCourses(){
        return courseRepository.findAll();
    }


    public List<Topics> getAllTopics(){
        return topicRepository.findAll();
    }

    public List<Request> getAllRequests(){
        return requestRepository.findAll();
    }


    public Courses getCourses(Long id){

        return courseRepository.findById(id).orElse(null);
    }

    public Request getRequest(Long id){
        return requestRepository.findById(id).orElse(null);
    }

    public Tasks getTask(Long id){

        return taskRepository.findById(id).orElse(null);
    }

    public Courses getCoursesByName(String name){

        return courseRepository.findByName(name);
    }

    public List<Teacher> getTeachers(){
        return teacherRepository.findAll();
    }


    public Teacher getTeacher(Long id){
        return teacherRepository.findById(id).orElse(null);
    }

    public List<AuthUser> getAllUsers(){

        return userRepository.findAll();

    }




    public List<PasswordHistory> getAllPasswordHistory(AuthUser user){

        return passwordHistoryRepository.findAllByUser(user);

    }

    public AuthUser saveUser(AuthUser user){

        return userRepository.save(user);

    }

//    public Topics addTopics(Topics topics){
//        return topicRepository.save(topics);
//    }

    public Tasks saveTasks(Tasks tasks){
        return taskRepository.save(tasks);
    }


    public Topics saveTopics(Topics topics){
        return topicRepository.save(topics);
    }


    public Answer addAnswer(Answer answer){
        return answerRepository.save(answer);
    }
    public List<Topics> topicList(Long id){

        return topicRepository.getTopicsById(id);
    }



    public void deleteCourse(Courses courses){
        courseRepository.delete(courses);
    }


    public void deleteTask(Tasks tasks){
        taskRepository.delete(tasks);
    }

    public void deleteTopic(Topics topics){
        topicRepository.delete(topics);
    }

//    public void deleteTopic(List<Tasks> tasks){
//         topicRepository.deleteAllByTasks(tasks);
//    }
//
//    public void deleteTopic(Topics topics){
//        topicRepository.delete(topics);
//    }

    public Topics addTopic(Topics topics){
        return topicRepository.save(topics);
    }


    public Tasks addTask(Tasks tasks){
        return taskRepository.save(tasks);
    }


//    public List<Tasks> getAllTasks(Topics topics){
//        return taskRepository.findAllByTopics(topics);
//    }

//    public List<Tasks> getAllTasks(){
//        return taskRepository.findAll();
//    }

    public List<Tasks> getAllTasks(){
        return taskRepository.findAll();
    }


    public List<Answer> getAllAnswers(){
        return answerRepository.findAll();
    }
}
