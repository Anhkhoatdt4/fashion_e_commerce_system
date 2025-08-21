package com.pbl6.fashion_web_be.controller;

import com.pbl6.fashion_web_be.dto.request.UserPreferenceRequest;
import com.pbl6.fashion_web_be.dto.response.UserPreferenceResponse;
import com.pbl6.fashion_web_be.service.UserPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user-preferences")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceService service;

    @GetMapping("/user/{userId}")
    public UserPreferenceResponse getByUser(@PathVariable UUID userId){
        return service.getPreferenceByUser(userId);
    }

    @PutMapping("/user/{userId}")
    public UserPreferenceResponse update(@PathVariable UUID userId,
                                         @RequestBody UserPreferenceRequest request){
        return service.updatePreference(userId, request);
    }
}
