package com.shoppingmall.ecommercebackend.domain.address.sevice;

import com.shoppingmall.ecommercebackend.domain.address.dto.request.AddressRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.address.dto.response.AddressRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.address.entity.AddressEntity;
import com.shoppingmall.ecommercebackend.domain.address.exception.AddressErrorCode;
import com.shoppingmall.ecommercebackend.domain.address.repository.AddressRepository;
import com.shoppingmall.ecommercebackend.domain.user.entity.UserEntity;
import com.shoppingmall.ecommercebackend.domain.user.exception.UserErrorCode;
import com.shoppingmall.ecommercebackend.domain.user.repository.UserRepository;
import com.shoppingmall.ecommercebackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    // 주소 등록
    @Transactional
    public AddressRegisterResponse registerAddress(AddressRegisterRequest request, Long userId) {

        // 사용자가 존재하는지 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 주소가 존재하는지 조회
        if(addressRepository.existsByZipCodeAndAddressDetail(request.getZipCode(), request.getAddressDetail())) {
            log.warn("[AddressService] 등록된 주소 입니다: zipCode = {}, addressDetail = {}", request.getZipCode(), request.getAddressDetail());
            throw new CustomException(AddressErrorCode.ADDRESS_DUPLICATE);
        }

        // 주소가 등록되어 있지 않다면 등록
        AddressEntity address = AddressEntity.builder()
                .user(user)
                .firstAddress(request.getFirstAddress())
                .secondAddress(request.getSecondAddress())
                .lastAddress(request.getLastAddress())
                .addressDetail(request.getAddressDetail())
                .zipCode(request.getZipCode())
                .build();

        // DB 저장
        AddressEntity savedAddress = addressRepository.save(address);

        // 로그 출력
        log.info("[AddressService] 주소 등록 성공: addressId= {}", savedAddress.getAddressId());

        // 응답 세팅
        return AddressRegisterResponse.builder()
                .addressId(savedAddress.getAddressId())
                .userId(savedAddress.getUser().getUserId())
                .firstAddress(savedAddress.getFirstAddress())
                .secondAddress(savedAddress.getSecondAddress())
                .lastAddress(savedAddress.getLastAddress())
                .addressDetail(savedAddress.getAddressDetail())
                .zipCode(savedAddress.getZipCode())
                .defaultAddress(savedAddress.isDefaultAddress())
                .createdAt(savedAddress.getCreatedAt())
                .build();
    }

    // 기본 배송지 설정
    @Transactional
    public void updateDefaultAddress(Long addressId, Long userId) {

        // 사용자가 존재하는지 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 주소가 존재하는지 조회
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new CustomException(AddressErrorCode.ADDRESS_NOT_FOUND));

        // 해당 주소가 본인의 주소인지 확인
        if (!address.getUser().getUserId().equals(userId)) {
            log.warn("[AddressService] 등록되지 않은 주소입니다: addressId= {}", addressId);
            throw new CustomException(AddressErrorCode.ADDRESS_NOT_FOUND);
        }

        // 기본 배송지로 되어있던 배송지를 다시 등록할때 예외 출력
        if (address.isDefaultAddress()) {
            log.warn("[AddressService] 등록된 기본 배송지입니다: addressId= {}", addressId);
            throw new CustomException(AddressErrorCode.DEFAULT_ADDRESS_REGISTER);
        }

        // 기존 기본 배송지가 있으면 false로 변경
        addressRepository.findByUserAndDefaultAddress(user, true)
                .ifPresent(existing -> existing.updateDefaultAddress(false));

        // 주소를 기본 배송지로 등록
        address.updateDefaultAddress(true);

        // 로그 출력
        log.info("[AddressService] 기본 배송지 등록 성공: addressId= {}", addressId);
    }
}
