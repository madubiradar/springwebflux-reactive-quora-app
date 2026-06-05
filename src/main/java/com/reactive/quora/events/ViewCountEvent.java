package com.reactive.quora.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewCountEvent {

    private String targetId;

    private String targetType;

    private LocalDateTime timestamp;

}
