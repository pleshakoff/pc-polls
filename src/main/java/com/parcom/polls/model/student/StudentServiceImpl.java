package com.parcom.polls.model.student;



import com.parcom.network.Network;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
class StudentServiceImpl implements StudentService {

   private final Network network;
    private static final String SERVICE_NAME_CLASSROOM = "classroom";


   @Override
   public List<Student> getGroupStudents()
   {
       Student[] students =  network.callGet(SERVICE_NAME_CLASSROOM,Student[].class,"students","all").getBody();
       return Arrays.asList(students);
   }

    @Override
    public Student getStudentById(Long id)
    {
        return network.callGet(SERVICE_NAME_CLASSROOM,Student.class,"students",id.toString()).getBody();
    }



}
