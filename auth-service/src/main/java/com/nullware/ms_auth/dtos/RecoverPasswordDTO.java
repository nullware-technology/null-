package com.nullware.ms_auth.dtos;

public record RecoverPasswordDTO(Long userId, String emailTo, String subject, String text, String token) {

}

