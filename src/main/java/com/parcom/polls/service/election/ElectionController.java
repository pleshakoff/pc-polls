package com.parcom.polls.service.election;


import com.parcom.polls.model.voter.Voter;
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
@RequestMapping(value = "/election",produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(tags="Vote process and result")
@RequiredArgsConstructor
public class ElectionController {

    private final ElectionService electionService;


    @PostMapping("/vote")
    @ApiOperation(value = "Vote")
    public Voter create(@Valid @RequestBody VoteDto voteDto,
                        BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return electionService.vote(voteDto);
    }

    @GetMapping("/result")
    @ApiOperation(value = "Results")
    public List<VoteResult> result(Long idPool) throws BindException {
        return electionService.result(idPool);
    }






}
