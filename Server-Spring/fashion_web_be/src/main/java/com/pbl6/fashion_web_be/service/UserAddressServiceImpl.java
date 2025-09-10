package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.request.UserAddressRequest;
import com.pbl6.fashion_web_be.dto.response.UserAddressResponse;
import com.pbl6.fashion_web_be.entity.UserProfile;
import com.pbl6.fashion_web_be.entity.UserAddress;
import com.pbl6.fashion_web_be.mapper.UserAddressMapper;
import com.pbl6.fashion_web_be.repository.UserAddressRepository;
import com.pbl6.fashion_web_be.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAddressServiceImpl implements UserAddressService {

    UserAddressMapper userAddressMapper;
    UserProfileRepository userProfileRepository;
    UserAddressRepository userAddressRepository;

    @Override
    public UserAddressResponse createAddress(UserAddressRequest request) {
        UserProfile userProfile = userProfileRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User profile not found"));
        UserAddress address = userAddressMapper.toUserAddress(request);
        address.setUser(userProfile);
        return userAddressMapper.toUserAddressResponse(userAddressRepository.save(address));
    }

    @Override
    public UserAddressResponse updateAddress(UUID addressId, UserAddressRequest request) {
        UserAddress address = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        UserAddress updatedAddress = userAddressMapper.toUserAddress(request);
        updatedAddress.setAddressId(addressId);
        updatedAddress.setUser(address.getUser());
        return userAddressMapper.toUserAddressResponse(userAddressRepository.save(updatedAddress));
    }

    @Override
    public void deleteAddress(UUID addressId) {
        userAddressRepository.deleteById(addressId);
    }

    @Override
    public List<UserAddressResponse> getAddressesByUser(UUID userId) {
        return userAddressRepository.findByUserProfileId(userId)
                .stream()
                .map(userAddressMapper::toUserAddressResponse)
                .collect(Collectors.toList());
    }
}
