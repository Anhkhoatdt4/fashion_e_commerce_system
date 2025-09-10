package com.pbl6.fashion_web_be.mapper;

import com.pbl6.fashion_web_be.dto.request.UserAddressRequest;
import com.pbl6.fashion_web_be.dto.response.UserAddressResponse;
import com.pbl6.fashion_web_be.entity.UserAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAddressMapper {

    @Mapping(source = "user.profileId", target = "userId")
    UserAddressResponse toUserAddressResponse(UserAddress address);

    default UserAddress toUserAddress(UserAddressRequest request) {
        if (request == null) return null;
        UserAddress address = new UserAddress();
        address.setRecipientName(request.getRecipientName());
        address.setPhone(request.getPhone());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setStateProvince(request.getStateProvince());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setIsDefault(request.getIsDefault());
        if(request.getAddressType() != null) {
            address.setAddressType(UserAddress.AddressType.valueOf(request.getAddressType()));
        }
        return address;
    }
}
