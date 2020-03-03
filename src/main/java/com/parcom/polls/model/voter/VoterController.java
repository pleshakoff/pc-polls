package com.parcom.polls.model.voter;


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
@RequestMapping(value = "/voters",produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(tags="Voters for poll")
@RequiredArgsConstructor
public class VoterController {

    private final VoterService voterService;

    @GetMapping
    @ApiOperation(value = "Get all variants")
    public List<Voter> all(@RequestParam Long idPoll){
        return voterService.all(idPoll);
    }

    @PostMapping
    @ApiOperation(value = "Add voter to poll")
    public Voter create(@Valid @RequestBody VoterDto voterDto,
                        BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return voterService.create(voterDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete voter from poll")
    public void delete(@PathVariable Long id)
    {
        voterService.delete(id);
    }






}
