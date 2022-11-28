package io.github.pitzzahh.atmGui.entity;

import java.time.LocalDate;

public record Client(
        String name,
        String address,
        LocalDate dateOfBirth,
        String accountNumber,
        String pin
) { }
