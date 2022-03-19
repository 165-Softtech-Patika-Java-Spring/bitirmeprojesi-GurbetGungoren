package com.softtech.finalproject.service;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "tax-rates")
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class TaxRatesProperties {
    @NotNull
    private double food;
}
