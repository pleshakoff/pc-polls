package com.parcom.polls.model.poll;

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
@RequestMapping(value = "/polls",produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(tags="Polls")
@RequiredArgsConstructor
public class PollController {

    private final PollService pollService;

    @GetMapping
    @ApiOperation(value = "Get all polls")
    public List<Poll> all(@RequestParam PollState pollState){
        return pollService.all(pollState);
    }


    @GetMapping("/my")
    @ApiOperation(value = "Get all my polls")
    public List<Poll> allMy(){
        return pollService.allMy();
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Get poll by ID")
    public Poll get(@PathVariable Long id)  {
        return pollService.get(id);
    }

    @PostMapping
    @ApiOperation(value = "Create poll")
    public Poll create(@Valid @RequestBody PollDto pollDto,
                               BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return pollService.create(pollDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update poll")
    public Poll update(@PathVariable Long id,@Valid @RequestBody PollDto pollDto,
                          BindingResult bindingResult) throws BindException
    {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return pollService.update(id,pollDto);
    }

    @PutMapping("/state/{id}")
    @ApiOperation(value = "Change polls state")
    public Poll changeState(@PathVariable Long id,PollState pollState) throws BindException
    {
        return pollService.changeState(id,pollState);
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete poll")
    public void delete(@PathVariable Long id)
    {
        pollService.delete(id);
    }




}
