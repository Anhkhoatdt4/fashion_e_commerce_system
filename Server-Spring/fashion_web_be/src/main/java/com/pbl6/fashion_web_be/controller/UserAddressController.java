package com.pbl6.fashion_web_be.controller;

import com.pbl6.fashion_web_be.dto.request.UserAddressRequest;
import com.pbl6.fashion_web_be.dto.response.UserAddressResponse;
import com.pbl6.fashion_web_be.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user-addresses")
@RequiredArgsConstructor
public class UserAddressController {

    private final UserAddressService service;

    @PostMapping
    public UserAddressResponse create(@RequestBody UserAddressRequest request){
        return service.createAddress(request);
    }

    @PutMapping("/{id}")
    public UserAddressResponse update(@PathVariable UUID id, @RequestBody UserAddressRequest request){
        return service.updateAddress(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        service.deleteAddress(id);
    }

    @GetMapping("/user/{userId}")
    public List<UserAddressResponse> getByUser(@PathVariable UUID userId){
        return service.getAddressesByUser(userId);
    }
}
