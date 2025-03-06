package gov.nist.hit.hl7.tcamt.auth.service;

import gov.nist.hit.auth.core.service.HITUserAccountService;
import gov.nist.hit.hl7.tcamt.auth.model.*;
import gov.nist.hit.hl7.tcamt.auth.repository.UserRepository;
import gov.nist.hit.hl7.tcamt.auth.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService implements HITUserAccountService<UserInfo, User> {

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final String NIST_OKTA_ISSUER;
	private final String DEFAULT_ROLE;
	private final PasswordEncoder passwordEncoder;

	public AccountService(
			UserRepository userRepository,
			UserRoleRepository userRoleRepository,
			PasswordEncoder passwordEncoder,
			@Value("${spring.security.oauth2.client.provider.nist-okta.issuer-uri}") String NIST_OKTA_ISSUER,
			@Value("${hit.auth.role.default}") String DEFAULT_ROLE) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.passwordEncoder = passwordEncoder;
		this.NIST_OKTA_ISSUER = NIST_OKTA_ISSUER;
		this.DEFAULT_ROLE = DEFAULT_ROLE;
	}

	@Override
	public User findAccount(OidcUser oidcUser) {
		String issuer = oidcUser.getIssuer().toString();
		String uid = oidcUser.getSubject();
		User user = userRepository.findByIdentity(issuer, uid);
		if (user == null) {
			// Allow account matching with email for nist-okta
			if(issuer.equals(NIST_OKTA_ISSUER) && oidcUser.getEmailVerified()) {
				user = userRepository.findByEmailIgnoreCase(oidcUser.getEmail());
				if(user != null) {
					addIdentity(user, oidcUser);
					return userRepository.save(user);
				}
			}
		}
		return user;
	}

	public void addIdentity(User user, OidcUser oidcUser) {
		String issuer = oidcUser.getIssuer().toString();
		String uid = oidcUser.getSubject();
		Identity identity = new Identity();
		identity.setIssuer(issuer);
		identity.setUid(uid);
		user.getIdentities().add(identity);
	}

	@Override
	public User createAccountForUser(OidcUser oidcUser) throws AuthenticationException {
		User user = new User();
		user.setEmail(oidcUser.getEmail());
		UserRole role = this.userRoleRepository.findByRole(DEFAULT_ROLE);
		if(role == null) {
			throw new AuthenticationServiceException("Default role not found");
		}
		user.getRoles().add(role);
		addIdentity(user, oidcUser);
		user.setActivePasswordLogin(false);
		user.setPassword(null);
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameIgnoreCase(username);
		if(user != null && user.isActivePasswordLogin()) {
			return user;
		} else {
			throw new UsernameNotFoundException(username);
		}
	}

	public User setupUserProfile(ProfileSetup profileSetup, String userId) throws Exception {
		User user = userRepository.findById(userId).orElse(null);
		if(user == null) {
			throw new Exception("User not found");
		}
		if(user.getUsername() != null && !user.getUsername().isEmpty()) {
			throw new Exception("User profile has already been setup.");
		}
		String username = profileSetup.getUsername();
		if(username.length() < 7 || username.length() > 15) {
			throw new Exception("Username should be between 7 and 15 characters long");
		}
		if(!username.matches("[a-zA-Z-_0-9]+")) {
			throw new Exception("Username contains invalid characters, only a to z, 0 to 9, - (dashes) and _ (underscores) allowed");
		}
		User existing = userRepository.findByUsernameIgnoreCase(username);
		if(existing != null) {
			throw new Exception("Username already taken.");
		}
		user.setUsername(username);
		return userRepository.save(user);
	}

	public List<String> validateUsername(String username) {
		List<String> errors = new ArrayList<>();
		if(username == null || username.isEmpty()) {
			errors.add("Username is required.");
		} else {
			if(username.length() < 7 || username.length() > 15) {
				errors.add("Username should be between 7 and 15 characters long");
			}
			if(!username.matches("[a-zA-Z-_0-9]+")) {
				errors.add("Username contains invalid characters, only a to z, 0 to 9, - (dashes) and _ (underscores) allowed");
			}
			User existing = userRepository.findByUsernameIgnoreCase(username);
			if(existing != null) {
				errors.add("Username already taken.");
			}
		}

		return errors;
	}

	public List<String> validateEmail(String email) {
		List<String> errors = new ArrayList<>();
		if(email == null || email.isEmpty()) {
			errors.add("Email is required.");
		} else {
			if(email.length() < 5) {
				errors.add("Email address is invalid.");
			}
			User existing = userRepository.findByEmailIgnoreCase(email);
			if(existing != null) {
				errors.add("Email address is already in use.");
			}
		}
		return errors;
	}

	public List<String> validatePassword(String password) {
		List<String> errors = new ArrayList<>();
		if(password == null || password.isEmpty()) {
			errors.add("Password is required.");
		} else {
			if(password.length() < 8 || password.length() > 30) {
				errors.add("Password should be between 8 and 30 characters long");
			}
			if(!password.matches("[a-zA-Z-_0-9]+")) {
				errors.add("Password contains invalid characters, only a to z, 0 to 9, - (dashes) and _ (underscores) allowed");
			}
		}
		return errors;
	}

	public User register(RegistrationRequest register) throws Exception {
		List<String> errors = validateUsername(register.getUsername());
		errors.addAll(validatePassword(register.getPassword()));
		errors.addAll(validateEmail(register.getEmail()));
		if(!errors.isEmpty()) {
			throw new Exception(String.join(", ", errors.toString()));
		}
		User user = new User();
		user.setUsername(register.getUsername().trim().toLowerCase());
		user.setPassword(passwordEncoder.encode(register.getPassword()));
		user.setEmail(register.getEmail().trim().toLowerCase());
		user.setActivePasswordLogin(true);
		UserRole role = this.userRoleRepository.findByRole(DEFAULT_ROLE);
		if(role == null) {
			throw new Exception("Unable to create account at this moment, please try again later.");
		}
		user.getRoles().add(role);
		return userRepository.save(user);
	}
}
