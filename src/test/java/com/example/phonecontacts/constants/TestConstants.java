package com.example.phonecontacts.constants;

import com.example.phonecontacts.dto.ContactDto;
import com.example.phonecontacts.dto.LoginDto;
import com.example.phonecontacts.dto.SignUpDto;
import com.example.phonecontacts.entities.*;

import java.util.Set;

public final class TestConstants {
    public static User getTestUser() {

        return User.builder()
                .id(1L)
                .name("Test")
                .userName("Test")
                .email("test@email.com")
                .password("test")
                .build();
    }

    public static Contact getTestContact() {

        return Contact.builder()
                .name("Test")
                .phones(Set.of(new ContactPhoneNumber("+380939333333"), (new ContactPhoneNumber("+380939333334"))))
                .emails(Set.of(new ContactEmail("xxx@xxx.com"), new ContactEmail("yyy@yyy.com")))
                .id(1L)
                .user(getTestUser())
                .build();
    }

    public static Image  getTestImage(){

        return Image.builder()
                .id(1L)
                .name("test.test")
                .imageBytes(new byte[0])
                .build();
    }

    public static ContactDto getContactDto() {
        return ContactDto.builder()
                .name("Test")
                .phones(Set.of("+380939333333", "+380939333334"))
                .emails(Set.of("xxx@xxx.com", "yyy@yyy.com"))
                .build();
    }

    public static LoginDto getLoginDto() {
        return new LoginDto("test", "test");
    }

    public static SignUpDto getSignupDto() {
        return new SignUpDto("Test", "test", "test@test.com", "test");
    }
}
