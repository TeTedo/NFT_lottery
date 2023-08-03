package com.undefined.undefined.domain.raffle.exception;

public class RaffleNotFoundException extends IllegalArgumentException {
    public RaffleNotFoundException() {
        super("Raffle Not Found");
    }
}
