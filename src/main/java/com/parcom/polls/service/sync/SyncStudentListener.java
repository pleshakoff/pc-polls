package com.parcom.polls.service.sync;

import com.parcom.asyncdto.SyncStudentDto;
import com.parcom.security_client.AsyncUserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
class SyncStudentListener {

    private final SyncService syncService;

    @KafkaListener(topics = "${parcom.kafka.topic.students}", groupId = "${parcom.kafka.group.polls}")
    public void listen(@Payload SyncStudentDto syncStudentDto, @Header("X-Auth-Token") String token) {
        log.info("Get sync students message");
         AsyncUserUtils.authByToken(token);
        syncService.synchronize();
    }


}
