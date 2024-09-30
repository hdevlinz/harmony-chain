package com.tth.profile.service.impl;

import com.tth.commonlibrary.dto.request.profile.UserProfileRequestCreate;
import com.tth.commonlibrary.dto.request.profile.carrier.CarrierRequestCreate;
import com.tth.commonlibrary.dto.request.profile.customer.CustomerRequestCreate;
import com.tth.commonlibrary.dto.request.profile.supplier.SupplierRequestCreate;
import com.tth.commonlibrary.dto.response.profile.UserProfileResponse;
import com.tth.commonlibrary.dto.response.profile.carrier.CarrierResponse;
import com.tth.commonlibrary.dto.response.profile.customer.CustomerResponse;
import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;
import com.tth.commonlibrary.enums.ErrorCode;
import com.tth.commonlibrary.exception.AppException;
import com.tth.profile.entity.Carrier;
import com.tth.profile.entity.Customer;
import com.tth.profile.entity.Supplier;
import com.tth.profile.mapper.CarrierMapper;
import com.tth.profile.mapper.CustomerMapper;
import com.tth.profile.mapper.SupplierMapper;
import com.tth.profile.repository.CarrierRepository;
import com.tth.profile.repository.CustomerRepository;
import com.tth.profile.repository.SupplierRepository;
import com.tth.profile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final CarrierRepository carrierRepository;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;
    private final CarrierMapper carrierMapper;
    private final CustomerMapper customerMapper;
    private final SupplierMapper supplierMapper;

    @Override
    public CarrierResponse getCarrierProfile(String userId) {
        return this.carrierRepository.findByUserId(userId)
                .map(this.carrierMapper::toCarrierResponse)
                .orElse(null);
    }

    @Override
    public CustomerResponse getCustomerProfile(String userId) {
        Customer customer = this.customerRepository.findByUserId(userId).orElse(null);

        return this.customerRepository.findByUserId(userId)
                .map(this.customerMapper::toCustomerResponse)
                .orElse(null);
    }

    @Override
    public SupplierResponse getSupplierProfile(String userId) {
        return this.supplierRepository.findByUserId(userId)
                .map(this.supplierMapper::toSupplierResponse)
                .orElse(null);
    }

    @Override
    public UserProfileResponse createUserProfile(UserProfileRequestCreate request) {
        switch (request) {
            case CarrierRequestCreate carrier -> {
                Carrier newCarrier = this.carrierMapper.toCarrier(carrier);
                this.carrierRepository.save(newCarrier);
                return this.carrierMapper.toCarrierResponse(newCarrier);
            }
            case CustomerRequestCreate customer -> {
                Customer newCustomer = this.customerMapper.toCustomer(customer);
                this.customerRepository.save(newCustomer);
                return this.customerMapper.toCustomerResponse(newCustomer);
            }
            case SupplierRequestCreate supplier -> {
                Supplier newSupplier = this.supplierMapper.toSupplier(supplier);
                this.supplierRepository.save(newSupplier);
                return this.supplierMapper.toSupplierResponse(newSupplier);
            }
            default -> throw new AppException(ErrorCode.INVALID_REQUEST);
        }
    }

    @Override
    public UserProfileResponse updateUserProfile() {
        return null;
    }
}
