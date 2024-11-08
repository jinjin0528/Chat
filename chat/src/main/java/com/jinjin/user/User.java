package com.jinjin.user;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {

    @Id
    private String id;
    private String username;
    private String email;
    private UserStatus status;
    private long lastActiveTime;
}