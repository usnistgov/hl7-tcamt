package gov.nist.hit.hl7.tcamt.auth.config;

import gov.nist.hit.hl7.tcamt.auth.model.*;
import gov.nist.hit.hl7.tcamt.auth.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//import gov.nist.hit.hl7.auth.util.crypto.CryptoUtil;
@Component
public class TokenAuthenticationService {

//  @Autowired
//  private CryptoUtil crypto;
//
//  @Autowired
//  private Environment env;
//
//  public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
//          throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//
//    Cookie token = WebUtils.getCookie(request, "tcamt-auth");
//
//    if (token != null && token.getValue() != null && !token.getValue().isEmpty()) {
//      try {
//          Claims claims = Jwts.parserBuilder()
//                  .setSigningKey(crypto.pub(env.getProperty("key.public")))
//                  .build()
//                  .parseClaimsJws(token.getValue())
//                  .getBody();
//
//          String username = claims.getSubject();
//        ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");
//
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        if (roles != null) {
//          roles.forEach(r -> authorities.add(new SimpleGrantedAuthority(r.get("authority"))));
//        }
//
//        return new UsernamePasswordAuthenticationToken(username, token.getValue(), authorities);
//      } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
//        throw new SecurityException("Invalid JWT Token", e);
//      }
//    }
//    return null;
//  }

  @Autowired
  AccountService accountService;
//	@Autowired
//	DemoAuthenticationService authenticationService;

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
