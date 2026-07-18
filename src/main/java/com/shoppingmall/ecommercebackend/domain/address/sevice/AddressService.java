package com.shoppingmall.ecommercebackend.domain.address.sevice;

import com.shoppingmall.ecommercebackend.domain.address.dto.request.AddressRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.address.dto.request.AddressUpdateRequest;
import com.shoppingmall.ecommercebackend.domain.address.dto.response.AddressListResponse;
import com.shoppingmall.ecommercebackend.domain.address.dto.response.AddressRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.address.dto.response.AddressUpdateResponse;
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

import java.util.ArrayList;
import java.util.List;

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

    // 주소 목록 조회
    public List<AddressListResponse> getAddressList(Long userId) {

        // 사용자가 존재하는지 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 응답 세팅
        List<AddressListResponse> list = new ArrayList<>();
        for (AddressEntity address : addressRepository.findAllByUser(user)) {
            list.add(AddressListResponse.builder()
                    .addressId(address.getAddressId())
                    .userId(address.getUser().getUserId())
                    .firstAddress(address.getFirstAddress())
                    .secondAddress(address.getSecondAddress())
                    .lastAddress(address.getLastAddress())
                    .addressDetail(address.getAddressDetail())
                    .zipCode(address.getZipCode())
                    .defaultAddress(address.isDefaultAddress())
                    .build());
        }

        // 로그 출력
        log.info("[AddressService] 주소 목록 조회 성공: userId= {}", userId);

        return list;
    }

    // 주소 삭제
    @Transactional
    public void deleteAddress(Long addressId, Long userId) {

        // 사용자가 존재하는지 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 주소가 존재하는지 조회
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new CustomException(AddressErrorCode.ADDRESS_NOT_FOUND));

        // 사용자 본인의 주소인지 확인
        if (!address.getUser().getUserId().equals(userId)) {
            log.warn("[AddressService] 사용자 본인의 주소와 일치하지 않습니다. userId= {}", userId);
            throw new CustomException(AddressErrorCode.ADDRESS_NOT_FOUND);
        }

        // 주소 삭제
        addressRepository.delete(address);

        // 로그 츨력
        log.info("[AddressService] 주소 삭제 성공: addressId= {}", addressId);
    }

    // 주소 수정
    @Transactional
    public AddressUpdateResponse updateAddress(Long addressId, Long userId, AddressUpdateRequest request) {

        // 사용자가 존재하는지 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 주소가 존재하는지 조회
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new CustomException(AddressErrorCode.ADDRESS_NOT_FOUND));

        // 로그인한 사용자가 등록한 주소가 맞는지 조회
        if (!address.getUser().getUserId().equals(userId)) {
            log.warn("[AddressService] 등록된 주소가 아닙니다. userId= {}", userId);
            throw new CustomException(AddressErrorCode.ADDRESS_NOT_FOUND);
        }

        // 주소 수정
        address.updateAddress(
                request.getFirstAddress(),
                request.getSecondAddress(),
                request.getLastAddress(),
                request.getAddressDetail(),
                request.getZipCode()
        );

        // 로그 출력
        log.info("[AddressService] 주소 수정 성공: addressId= {}", addressId);

        return AddressUpdateResponse.builder()
                .addressId(address.getAddressId())
                .userId(address.getUser().getUserId())
                .firstAddress(address.getFirstAddress())
                .secondAddress(address.getSecondAddress())
                .lastAddress(address.getLastAddress())
                .addressDetail(address.getAddressDetail())
                .zipCode(address.getZipCode())
                .defaultAddress(address.isDefaultAddress())
                .updatedAt(address.getModifiedAt())
                .build();
    }
}
