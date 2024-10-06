package com.tth.shipping.service.impl;

import com.tth.shipping.service.SampleDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SampleDataServiceImpl implements SampleDataService {

    @Override
    public boolean createSampleData() {
        log.info("Creating sample data...");

        return true;
    }

}
