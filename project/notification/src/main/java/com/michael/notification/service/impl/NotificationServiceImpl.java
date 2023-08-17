package com.michael.notification.service.impl;

import com.michael.clients.notification.NotificationRequest;
import com.michael.notification.entity.Notification;
import com.michael.notification.repository.NotificationRepository;
import com.michael.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;


    @Override
    public void send(NotificationRequest notificationRequest) {
        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(notificationRequest.getToCustomerId())
                        .toCustomerEmail(notificationRequest.getToCustomerEmail())
                        .sender("Michael")
                        .message(notificationRequest.getMessage())
                        .sentAt(LocalDateTime.now())
                        .build());
    }
}
