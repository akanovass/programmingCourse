package bitlab.academyProject.SpringProject.controllers;

import bitlab.academyProject.SpringProject.models.*;
import bitlab.academyProject.SpringProject.services.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainControllers {

    private final AuthUserService authUserService;
    private final PasswordEncoder passwordEncoder;




    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("currentUser", getCurrentUser());

        List<Courses> courses = authUserService.getAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("currentUser", getCurrentUser());

        List<Topics> allTopics = authUserService.getAllTopics();

        model.addAttribute("topics", allTopics);
        return "index";
    }

    @GetMapping(value = "/signin")
    public String signIn(Model model) {
        model.addAttribute("currentUser", getCurrentUser());

        return "signin";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model) {
        model.addAttribute("currentUser", getCurrentUser());

        List<AuthUser> u = authUserService.getAllUsers();
        for (int i = 0; i < u.size(); i++) {
            if (u.get(i).getEmail().equals(getCurrentUser().getEmail())) {
                u.remove(u.get(i));
            }
        }

        List<AuthRole> users = getCurrentUser().getRoles();
        List<Teacher> teachers = authUserService.getTeachers();
        String currUser = "";

        List<Courses> courses = authUserService.getAllCourses();
        List<Topics> allTopics = authUserService.getAllTopics();

        for (int i = 0; i < users.size(); i++) {
            currUser += users.get(i).getRole();
        }
        if (currUser.equals("ROLE_ADMIN")) {
            model.addAttribute("currentUser", getCurrentUser());
            model.addAttribute("courses", courses);
            model.addAttribute("topics", allTopics);
            return "profile";
        } else {
            model.addAttribute("courses", courses);
            model.addAttribute("topics", allTopics);
            model.addAttribute("currentUser", getCurrentUser());
            model.addAttribute("teachers", teachers);
            return "profile";
        }


    }


    @GetMapping(value = "/allrequests")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String allRequests(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
       List<Request> allRequests=authUserService.getAllRequests();
       model.addAttribute("requests",allRequests);
        return "allrequest";
    }

    @GetMapping(value = "/update")
    @PreAuthorize("isAuthenticated()")
    public String updateProgile(Model model) {
        model.addAttribute("currentUser", getCurrentUser());

        return "myProfile";
    }


    @GetMapping(value = "/accesserror")
    public String accessErrror(Model model) {
        model.addAttribute("currentUser", getCurrentUser());

        return "accessdeniedpage";
    }


    @GetMapping(value = "/signup")
    public String signUp(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "signup";
    }


    @GetMapping(value = "/allstudents")
    public String allStudents(Model model) {
        List<AuthUser> users = authUserService.getAllUsers();
        model.addAttribute("currentUser", getCurrentUser());

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(getCurrentUser().getEmail())) {
                users.remove(users.get(i));
            }
        }
        model.addAttribute("users", users);

        return "allstudents";
    }


    @GetMapping(value = "/adminpanel")
    public String adminPanel(Model model) {

        List<Courses> courses = authUserService.getAllCourses();
        model.addAttribute("currentUser", getCurrentUser());

        model.addAttribute("courses", courses);
        List<String> days = new ArrayList<>();
        days.add("Mon");
        days.add("Tue");
        days.add("Wed");
        days.add("Thur");
        days.add("Fri");
        days.add("Sat");
        days.add("Sun");
        model.addAttribute("days", days);
        model.addAttribute("currentUser", getCurrentUser());
        List<Teacher> teachers = authUserService.getTeachers();
        List<Topics> allTopics = authUserService.getAllTopics();
        model.addAttribute("teachers", teachers);
        model.addAttribute("topics", allTopics);
        return "adminpanel";

    }


    @PostMapping(value = "/addcourse")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String addNews(Model model, @RequestParam(name = "course_name") String course_name,
                          @RequestParam(name = "course_profession") String course_profession,
                          @RequestParam(name = "course_description") String course_description,
                          @RequestParam(name = "course_photo") String course_photo,
                          @RequestParam(name = "num_students") String num_students,
                          @RequestParam(name = "level") String level,
                          @RequestParam(name = "course_price") int price,
                          @RequestParam(name = "start_date") String start_date,
                          @RequestParam(name = "finish_date") String finish_date,
                          @RequestParam(name = "teacher") String teacher,
                          @RequestParam(name = "days") String days,
                          @RequestParam(name = "first_time") String first_time,
                          @RequestParam(name = "second_time") String second_time) {
        model.addAttribute("currentUser", getCurrentUser());

        Courses course = new Courses();
        course.setName(course_name);
        course.setDescription(course_description);
        course.setContent(course_profession);
        course.setPhoto(course_photo);
        course.setMesta(num_students);
        course.setLavel(level);
        course.setPrice(price);
        course.setStartDate(start_date);
        course.setFinishDate(finish_date);
        course.setTeacher(teacher);
        course.setDays(days);
        course.setStartTime(first_time);
        course.setFinishTime(second_time);

        Courses c = authUserService.addCourse(course);
        if (c != null) {
            return "redirect:/intocourse/" + c.getId();
        }
        return "redirect:/";
    }


    @GetMapping(value = "/allcourse")
    public String allCourses(Model model) {
        List<Courses> courses = authUserService.getAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("currentUser", getCurrentUser());

        List<Topics> allTopics = authUserService.getAllTopics();

        model.addAttribute("topics", allTopics);
        return "allcourse";

    }


    @GetMapping(value = "/my_course/{id}")
    public String myCourse(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("currentUser", getCurrentUser());
        Courses courses = authUserService.getCourses(id);
        List<Topics> topics = courses.getTopics();
        model.addAttribute("topic", topics);
        model.addAttribute("course", courses);
        return "toCourse";

    }

    @GetMapping(value = "/intocourse/{id}")
    public String readNews(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("currentUser", getCurrentUser());
        Courses courses = authUserService.getCourses(id);
        List<Topics> topics = courses.getTopics();
        model.addAttribute("topic", topics);
        model.addAttribute("course", courses);
        return "inToCourse";

    }

    @GetMapping(value = "/admin_details/{id}")
    public String adminDetails(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("currentUser", getCurrentUser());
        Courses courses = authUserService.getCourses(id);
        List<Topics> topics = courses.getTopics();
        model.addAttribute("topic", topics);
        model.addAttribute("course", courses);
        return "adminDetails";

    }



    @GetMapping(value = "/updatenews/{id}")
    public String updateNews(Model model, @PathVariable(name = "id") Long id) {
        List<Teacher> teachers = authUserService.getTeachers();
        model.addAttribute("teachers", teachers);
        model.addAttribute("currentUser", getCurrentUser());

        List<String> day = new ArrayList<>();
        day.add("Mon");
        day.add("Tue");
        day.add("Wed");
        day.add("Thur");
        day.add("Fri");
        day.add("Sat");
        day.add("Sun");
        model.addAttribute("days", day);
        Courses courses = authUserService.getCourses(id);
        model.addAttribute("course", courses);
        return "up";
    }


    @GetMapping(value = "/tasks/{id}")
    public String goToTasks(Model model, @PathVariable(name = "id") Long id) {
        Courses course = authUserService.getCourses(id);
        model.addAttribute("currentUser", getCurrentUser());

        if (course != null) {
            List<Topics> topics = course.getTopics();
            model.addAttribute("topics", topics);
            model.addAttribute("c", course);
            return "topics";
        }

        return "adminpanel";
    }

    @PostMapping(value = "save_topic")
    public String saveTopic(@RequestParam(name = "topic_name") String topic_name,
                            @RequestParam(name = "topic_description") String topic_description,
                            @RequestParam(name = "topic_id") Long topic_id,
                            @RequestParam(name = "course_id") Long course_id) {

        Topics topics = authUserService.getTopic(topic_id);
        Courses courses = authUserService.getCourses(course_id);
        if (topics != null) {
            topics.setTopic(topic_name);
            topics.setTopicDescription(topic_description);
            authUserService.saveTopics(topics);
            return "redirect:/tasks/" + courses.getId();
        }
        return "accessdeniedpage";

    }


    @PostMapping(value = "/task_details")
    public String taskDetails(Model model, @RequestParam(name = "topic_id") Long topic_id,
                              @RequestParam(name = "course_id") Long course_id) {
        Courses courses = authUserService.getCourses(course_id);
        Topics topic = authUserService.getTopic(topic_id);
        model.addAttribute("topic", topic);
        model.addAttribute("currentUser", getCurrentUser());

        List<Tasks> tasks = topic.getTasks();
        model.addAttribute("task", tasks);
        model.addAttribute("courses", courses);
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(tasks.get(i).getTask());
        }
        return "topic_details";
    }

    @PostMapping(value = "update_task")
    public String updateTask(@RequestParam(name = "task_id") Long task_id,
                             @RequestParam(name = "course_id") Long course_id,
                             @RequestParam(name = "task") String task_name){

        Tasks task=authUserService.getTask(task_id);
        if(task!=null){
            task.setTask(task_name);
            authUserService.saveTasks(task);
        }
        return "redirect:/tasks/"+course_id;
    }


    @PostMapping(value = "delete_task")
    public String deleteTask(@RequestParam(name = "task_id") Long task_id,
                             @RequestParam(name = "topic_id") Long topic_id,
                             @RequestParam(name = "course_id") Long course_id){

        Tasks task=authUserService.getTask(task_id);
        Topics topic=authUserService.getTopic(topic_id);
        if(topic!=null){
            List<Tasks> tasks=topic.getTasks();
            for(int i=0; i<tasks.size(); i++){
                if(tasks.get(i).equals(task)){
                    tasks.remove(tasks.get(i));
                    authUserService.saveTasks(task);
                }
            }
        }

        return "redirect:/tasks/"+course_id;
    }




    @PostMapping(value = "student_course_task")
    public String studentTask(Model model, @RequestParam(name = "topic_id") Long topic_id,
                              @RequestParam(name = "course_id") Long course_id) {
        Courses courses=authUserService.getCourses(course_id);
        Topics topic = authUserService.getTopic(topic_id);
        model.addAttribute("currentUser", getCurrentUser());
        model.addAttribute("course",courses);
        model.addAttribute("topic", topic);
        List<Tasks> tasks = topic.getTasks();
        model.addAttribute("task", tasks);


        return "student_course_topics";

    }


    @PostMapping(value = "/delete_topic")
    public String deleteTopic(Model model, @RequestParam(name = "topic_id") Long topicId,
                              @RequestParam(name = "course_id") Long course_id) {
        Topics topic = authUserService.getTopic(topicId);
        Courses courses = authUserService.getCourses(course_id);
        List<Topics> topics = courses.getTopics();
        for (int i = 0; i < topics.size(); i++) {
            if (topics.get(i).equals(topic)) {
                topics.remove(topics.get(i));
            }
        }
        model.addAttribute("currentUser", getCurrentUser());
        List<Tasks> tasks = topic.getTasks();
        if (tasks != null) {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("Taskkk:" + tasks.get(i));
                authUserService.deleteTopic(topic);
                authUserService.deleteTask(tasks.get(i));

            }
        }

//            authUserService.deleteTopic(topic);
        return "redirect:/tasks/"+course_id;



//         return "redirect:/profile";
    }



    @PostMapping(value = "/add_topic")
    public String addTopic(Model model,@RequestParam(name = "topic_name") String topic_name,
                           @RequestParam(name = "topic_description") String description,
                           @RequestParam(name = "c_id") Long course_id){

            Courses courses=authUserService.getCourses(course_id);
        model.addAttribute("currentUser",getCurrentUser());

        if(courses!=null) {
                Topics newTopic = new Topics();
                newTopic.setTopic(topic_name);
                newTopic.setTopicDescription(description);
                Topics topic = authUserService.addTopic(newTopic);
                List<Topics> list = courses.getTopics();
                if (list == null) {
                    list = new ArrayList<>();
                }

                list.add(topic);
                courses.setTopics(list);
                authUserService.saveCourse(courses);
                 return "redirect:/tasks/"+courses.getId();

            }
        return "redirect:/profile";


    }
    @PostMapping(value = "/ozgertu")
    public String updateNews(Model model,@RequestParam(name = "course_name") String course_name,
                             @RequestParam(name = "course_profession") String course_profession,
                             @RequestParam(name = "course_description") String course_description,
                             @RequestParam(name = "course_photo") String course_photo,
                             @RequestParam(name = "num_students") String num_students,
                             @RequestParam(name = "level") String level,
                             @RequestParam(name = "course_price") int price,
                             @RequestParam(name = "start_date") String start_date,
                             @RequestParam(name = "finish_date") String finish_date,
                             @RequestParam(name = "teacher") String teacher,
                             @RequestParam(name = "days") String days,
                             @RequestParam(name = "first_time") String first_time,
                             @RequestParam(name = "second_time") String second_time,
                             @RequestParam(name = "course_id") Long course_id){
        model.addAttribute("currentUser",getCurrentUser());

        Courses courses=authUserService.getCourses(course_id);
        if(courses!=null){
            courses.setName(course_name);
            courses.setContent(course_profession);
            courses.setPhoto(course_photo);
            courses.setDescription(course_description);
            courses.setMesta(num_students);
            courses.setLavel(level);
            courses.setPrice(price);
            courses.setStartDate(start_date);
            courses.setFinishDate(finish_date);
            courses.setTeacher(teacher);
            courses.setDays(days);
            courses.setStartTime(first_time);
            courses.setFinishTime(second_time);
            authUserService.saveCourse(courses);
            return "redirect:/adminpanel";

        }

        return "redirect:/profile";

    }



    @PostMapping(value = "/totaskdetails")
    public String toTaskDetails(Model model,@RequestParam(name = "task_id") Long id ,
                                @RequestParam(name = "course_id") Long course_id,
                                @RequestParam(name = "topic_id") Long topic_id){
        model.addAttribute("currentUser",getCurrentUser());
       Courses courses=authUserService.getCourses(course_id);
       Topics topics=authUserService.getTopic(topic_id);
        Tasks tasks=authUserService.getTask(id);
            model.addAttribute("task",tasks);
            model.addAttribute("course",courses);
            model.addAttribute("topic",topics);
            return "totaskdetails";
    }



    @PostMapping(value = "/tohomework")
    public String toHomeWork(Model model,@RequestParam(name = "task_id") Long id ,
                             @RequestParam(name = "course_id") Long course_id,
                             @RequestParam(name = "topic_id") Long topic_id){
        Topics topics=authUserService.getTopic(topic_id);
        model.addAttribute("topic",topics);
        model.addAttribute("currentUser",getCurrentUser());
        Courses courses=authUserService.getCourses(course_id);
        model.addAttribute("course",courses);
        List<Answer> answers1=new ArrayList<>();
        Tasks tasks=authUserService.getTask(id);
        List<Answer> answers2=new ArrayList<>();

        List<Answer> answers=authUserService.getAllAnswers();
        for(int i=0; i< answers.size(); i++){
            if(answers.get(i).getAuthor().getId().equals(getCurrentUser().getId()) && answers.get(i).getTasks().equals(tasks)){
              answers1.add(answers.get(i));
                model.addAttribute("answerofuser",answers1);
                model.addAttribute("bos",answers2);

            }
        }
        model.addAttribute("task",tasks);
        return "tohomework";
    }
    @PostMapping(value = "/addTask")
    public String addTask(Model model,@RequestParam(name = "topicId") Long topic_id,
                          @RequestParam(name = "task") String task,
                          @RequestParam(name = "course_id") Long course_id){
        Topics topic = authUserService.getTopic(topic_id);
        model.addAttribute("currentUser",getCurrentUser());

        if(topic!=null){
            Tasks task1=new Tasks();
            task1.setTask(task);
            List<Tasks> tasksList=topic.getTasks();
            if(tasksList==null){
                tasksList=new ArrayList<>();
            }
            tasksList.add(task1);
            topic.setTasks(tasksList);

            authUserService.saveTasks(task1);
            return "redirect:/tasks/"+course_id;
        }

        return "redirect:/profile";

    }

        @PostMapping(value = "/delete_course")
        public String deleteNews(Model model,@RequestParam(name = "n_id") Long id){
        Courses cour=authUserService.getCourses(id);
            model.addAttribute("currentUser",getCurrentUser());

            if(cour!=null){
        List<AuthUser> users=authUserService.getAllUsers();
        for (int i=0; i< users.size(); i++){
            List<Courses> courseList=users.get(i).getCourses();
            for (int j=0; j<courseList.size(); j++) {
                if (courseList.get(j).equals(cour)){
                    courseList.remove(cour);
                    users.get(i).setCourses(courseList);
                    authUserService.saveUser(users.get(i));
                    authUserService.deleteCourse(cour);
                    authUserService.saveUser(users.get(i));
                }else {
                    authUserService.deleteCourse(cour);
                    authUserService.saveCourse(cour);

                }
                System.out.println("Hiii");
                    return "redirect:/adminpanel";

            }
            }
        }

        return "redirect:/profile";
    }

    @GetMapping(value = "/details")
    public String details(Model model,@RequestParam(name = "id") Long id){
        AuthUser user=authUserService.getUser(id);
        model.addAttribute("currentUser",getCurrentUser());
        model.addAttribute("u",user);

        List<Courses> possibleCourse=authUserService.getAllCourses();
        model.addAttribute("courses",possibleCourse);

        return "details";

    }

    @GetMapping(value = "/torequest")
    public String requestDetails(Model model,@RequestParam(name = "id") Long id){
        model.addAttribute("currentUser",getCurrentUser());

        Request request=authUserService.getRequest(id);
        if(request!=null){
            model.addAttribute("request",request);
            return "request_details";
        }

        return "accessdeniedpage";
    }


    @GetMapping(value = "/mycourses")
    public String myCourses(Model model){
        model.addAttribute("currentUser",getCurrentUser());
        AuthUser user=authUserService.getUser(getCurrentUser().getId());
        model.addAttribute("user",user);

        List<Courses> possibleCourse=authUserService.getAllCourses();
        model.addAttribute("curstar",possibleCourse);
        return "mycourses";

    }

    @PostMapping(value = "/tosignup")
    public String toSignUP(Model model,@RequestParam(name = "user_email")String email,
                           @RequestParam(name = "user_password") String password,
                           @RequestParam(name = "conf_user_password") String confirmPassword,
                           @RequestParam(name = "full_name") String fullName){
        model.addAttribute("currentUser",getCurrentUser());

        if(password.equals(confirmPassword)){
            AuthUser checkUser=authUserService.findUserByEmail(email);
            if(checkUser==null) {
                AuthUser user = new AuthUser();
                user.setEmail(email);
                user.setFullName(fullName);
                user.setPassword(passwordEncoder.encode(password));
                authUserService.registerUser(user);
                AuthUser newUser = authUserService.registerUser(user);
                if (newUser != null) {
                    return "redirect:/signup?success";
                }
            }
        }
        return "redirect:/signup?error";
    }




    @PostMapping(value = "/updateprofile")
    @PreAuthorize("isAuthenticated()")
    public String updateProfile(Model model,@RequestParam(name = "full_name") String fullName,
                                @RequestParam(name = "email") String email){
        model.addAttribute("currentUser",getCurrentUser());


        AuthUser currentUser=getCurrentUser();
        currentUser.setFullName(fullName);
        currentUser.setEmail(email);
        authUserService.saveUser(currentUser);
        System.out.println(fullName);
        return "redirect:/profile";

    }

    @PostMapping(value = "/updatephoto")
    @PreAuthorize("isAuthenticated()")
    public String updatePhoto(Model model,@RequestParam(name = "photo") String photo){
        model.addAttribute("currentUser",getCurrentUser());
        AuthUser user=getCurrentUser();
        if(user!=null){
            user.setPhotoProfile(photo);
            authUserService.saveUser(user);
            return "redirect:/profile";

        }
        return "redirect:/accessdeniedpage";
    }




    @PostMapping(value = "/updateprofileofadmin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String updateProfileOfAdmin(Model model,@RequestParam(name = "full_name") String fullName,
                                @RequestParam(name = "email") String email,
                                       @RequestParam(name = "id") Long id){

        model.addAttribute("currentUser",getCurrentUser());


        AuthUser user=authUserService.getUser(id);
        user.setFullName(fullName);
        user.setEmail(email);
        authUserService.saveUser(user);
        System.out.println(fullName);
        return "redirect:/allstudents";

    }
    @PostMapping(value = "/updatepassword")
    @PreAuthorize("isAuthenticated()")
    public String updatePasssword(Model model,@RequestParam(name = "old_password") String oldPassword,
                                  @RequestParam(name = "new_password") String newPassword,
                                  @RequestParam(name = "retype_password") String retypePassword){

        if(newPassword.equals(retypePassword)){
            model.addAttribute("currentUser",getCurrentUser());

            AuthUser currentUser=getCurrentUser();
            if(passwordEncoder.matches(oldPassword,currentUser.getPassword())){



                if(currentUser!=null) {
                    currentUser.setPassword(passwordEncoder.encode(newPassword));
                    authUserService.saveUser(currentUser);
                }
            }
        }
        return "redirect:/profile";
    }




    @PostMapping(value = "/assigncourse")
    public String assignCourse(Model model,@RequestParam(name = "course_id") Long courseId,
                               @RequestParam(name = "student_id") Long studentId){
        model.addAttribute("currentUser",getCurrentUser());

        AuthUser user=authUserService.getUser(studentId);
        if(user!=null){
            Courses course=authUserService.getCourses(courseId);
            if(course!=null){
                List<Courses> coursesList=user.getCourses();
                if(coursesList==null){
                    coursesList=new ArrayList<>();
                }

                coursesList.add(course);
                user.setCourses(coursesList);
                authUserService.saveUser(user);
                return "redirect:/details?id="+user.getId();
            }
        }
        return "redirect:/";

    }


    @PostMapping(value = "/unassigncourse")
    public String unassignCourse(Model model,@RequestParam(name = "course_id") Long courseId,
                               @RequestParam(name = "student_id") Long studentId){
        model.addAttribute("currentUser",getCurrentUser());

        AuthUser user=authUserService.getUser(studentId);
        if(user!=null){
            Courses course=authUserService.getCourses(courseId);
            if(course!=null){
                List<Courses> coursesList=user.getCourses();
                if(coursesList==null){
                    coursesList=new ArrayList<>();
                }

                coursesList.remove(course);
                user.setCourses(coursesList);
                authUserService.saveUser(user);
                return "redirect:/details?id="+user.getId();
            }
        }
        return "redirect:/";

    }

    protected AuthUser getCurrentUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            AuthUser authUser=(AuthUser) authentication.getPrincipal();
            return authUser;
        }
        return null;
    }



    @PostMapping(value = "/addAnswer")
    public String addAnswer(Model model,@RequestParam(name = "task_id") Long id_task,
                            @RequestParam(name = "answer") String answer,
                            @RequestParam(name = "course_id") Long course_id,
                            @RequestParam(name = "topic_id") Long topic_id){
        model.addAttribute("currentUser",getCurrentUser());
        Topics topics=authUserService.getTopic(topic_id);
        model.addAttribute("topic",topics);
        Tasks tasks=authUserService.getTask(id_task);
        if(tasks!=null){
            Answer answer1=new Answer();
            answer1.setTasks(tasks);
            answer1.setPostDate(new Date());
            answer1.setAuthor(getCurrentUser());
            answer1.setAnswer(answer);
            Answer newAnswer=authUserService.addAnswer(answer1);
            if(newAnswer!=null) {
                return "redirect:/mycourses";
            }
        }

        return "redirect:/accesserror";

    }


    @PostMapping(value = "/sendrequest")
    public String sendRequest(Model model,@RequestParam(name = "full_name") String fullName,
                              @RequestParam(name = "email") String email,
                              @RequestParam(name = "phone") String number,
                              @RequestParam(name = "course_id") Long course_id){

        Courses course=authUserService.getCourses(course_id);
        model.addAttribute("currentUser",getCurrentUser());

        if(course!=null){
            Request request=new Request();
            request.setName(fullName);
            request.setEmail(email);
            request.setNumber(number);
            request.setCourses(course);
            request.setCompleted(false);
            Request request1=authUserService.addRequest(request);
            List<Courses> courses = authUserService.getAllCourses();
            model.addAttribute("courses", courses);
            model.addAttribute("currentUser", getCurrentUser());

            List<Topics> allTopics = authUserService.getAllTopics();

            model.addAttribute("topics", allTopics);
            if(request1!=null){
                return "index";


            }
        }


        return "accessdeniedpage";
    }


    @PostMapping(value = "save_request")
    public String isCompleted(Model model,@RequestParam(name = "completed") boolean completed,
                              @RequestParam(name = "request_id") Long id){
        Request request=authUserService.getRequest(id);
        if(request!=null){
            request.setCompleted(completed);
            Request request1=authUserService.addRequest(request);
            model.addAttribute("currentUser", getCurrentUser());
            List<Request> allRequests=authUserService.getAllRequests();
            model.addAttribute("requests",allRequests);
            return "allrequest";

        }


        return "accessdeniedpage";
    }


}
