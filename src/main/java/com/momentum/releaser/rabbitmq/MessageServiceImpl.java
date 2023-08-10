package com.momentum.releaser.rabbitmq;

import com.momentum.releaser.rabbitmq.MessageDto.ReleaserMessageDto;

import com.momentum.releaser.redis.RedisUtil;
import com.momentum.releaser.redis.notification.NotificationRedisRepository;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange userDirectExchange;
    private final DirectExchange projectDirectExchange;

    private final RedisUtil redisUtil;
    private final NotificationRedisRepository notificationRedisRepository;

    /**
     * Queue로 메시지 발행
     *
     * @param userEmail          사용자 이메일
     * @param releaserMessageDto 발행할 메시지의 DTO
     * @author seonwoo
     * @date 2023-08-07 (월)
     */
    @Override
    public void sendMessagePerUser(String userEmail, ReleaserMessageDto releaserMessageDto) {
        // 메시지 전송
        String userRoutingKey = "releaser.user." + userEmail;
        rabbitTemplate.convertAndSend(userDirectExchange.getName(), userRoutingKey, releaserMessageDto);

        // 테스트용 메시지 수신
        receiveMessagePerUser(releaserMessageDto);
    }

    @Override
    public void receiveMessagePerUser(ReleaserMessageDto releaserMessageDto) {
        log.info("Received message: {}", releaserMessageDto.getMessage());
    }

    /**
     * Queue에서 메시지를 구독
     *
     * @param projectId          프로젝트 식별 번호
     * @param releaserMessageDto 발행할 메시지의 DTO
     * @author seonwoo
     * @date 2023-08-07 (월)
     */
    @Override
    public void sendMessagePerProject(Long projectId, ReleaserMessageDto releaserMessageDto) {
        String projectRoutingKey = "releaser.project." + projectId;
        rabbitTemplate.convertAndSend(projectDirectExchange.getName(), projectRoutingKey, releaserMessageDto);

        // 테스트용 메시지 수신
        receiveMessagePerProject(releaserMessageDto);
    }

    @Override
    public void receiveMessagePerProject(ReleaserMessageDto releaserMessageDto) {
        log.info("Received message: {}", releaserMessageDto.getMessage());
    }
}
