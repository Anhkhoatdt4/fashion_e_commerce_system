package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.request.UserAddressRequest;
import com.pbl6.fashion_web_be.dto.response.UserAddressResponse;

import java.util.List;
import java.util.UUID;

public interface UserAddressService {
    UserAddressResponse createAddress(UserAddressRequest request);
    UserAddressResponse updateAddress(UUID addressId, UserAddressRequest request);
    void deleteAddress(UUID addressId);
    List<UserAddressResponse> getAddressesByUser(UUID userId);
}
