package com.parcom.polls.service.sync;


import com.parcom.polls.model.voter.Voter;
import com.parcom.polls.model.voter.VoterDto;
import com.parcom.polls.model.voter.VoterService;
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
@RequestMapping(value = "/sync",produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(tags="Students synchronization")
@RequiredArgsConstructor
public class SyncController {

    private final SyncService syncService;


    @PostMapping("/synchronize")
    @ApiOperation(value = "Synchronize group students")
    public void synchronize()  {
        syncService.synchronize();
    }








}
