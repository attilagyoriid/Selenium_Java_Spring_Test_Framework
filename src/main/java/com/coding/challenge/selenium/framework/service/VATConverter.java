package com.coding.challenge.selenium.framework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * VATConverter is a service that provides methods to convert between different
 * VAT calculations. Values could be calculated dynamically - but it is not implemented
 */
@Slf4j
@Lazy
@Service
public class VATConverter {
    public VATConverter() {
        throw new UnsupportedOperationException("VATConverter is not implemented");
    }
}
