package gov.nist.hit.hl7.tcamt.auth.controller;

import gov.nist.hit.hl7.tcamt.auth.model.*;
import gov.nist.hit.hl7.tcamt.auth.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthenticationController {

  @Autowired
  private Environment env;
  @Autowired
  AccountService accountService;
  @GetMapping("/api/me")
  public UserInfo me(@AuthenticationPrincipal UserInfo principal) {
    return principal;
  }

  @PostMapping("/api/setup-profile")
  public OpAck<UserInfo> profileSetup(@RequestBody ProfileSetup profile, @AuthenticationPrincipal UserInfo principal) throws Exception {
    User user = accountService.setupUserProfile(profile, principal.getId());
    return new OpAck<>(AckStatus.SUCCESS, "User profile updated successfully", "user-profile-setup", user.getPrincipal());
  }

  @PostMapping("/api/register")
  public OpAck<Void> profileSetup(@RequestBody RegistrationRequest registration) throws Exception {
    accountService.register(registration);
    return new OpAck<>(AckStatus.SUCCESS, "Your account has been created. Please login to start using the tool.", "user-registration", null);
  }
}
