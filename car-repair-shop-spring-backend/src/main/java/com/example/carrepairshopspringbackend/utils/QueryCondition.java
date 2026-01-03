package com.example.carrepairshopspringbackend.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryCondition {
    private UUID userId;
    private boolean expired;
    private boolean revoked;
}
