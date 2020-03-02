package com.parcom.polls.model.student;


import com.parcom.polls.model.variant.Variant;
import com.parcom.polls.model.variant.VariantDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/students",produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(tags="Voters for poll")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    @ApiOperation(value = "Get all variants")
    public List<Student> all(@RequestParam Long idPoll){
        return studentService.all(idPoll);
    }

    @PostMapping
    @ApiOperation(value = "Add voter to poll")
    public Student create(@Valid @RequestBody StudentDto studentDto,
                               BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return studentService.create(studentDto);
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete voter from poll")
    public void delete(@PathVariable Long id)
    {
        studentService.delete(id);
    }






}
