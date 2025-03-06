package gov.nist.hit.hl7.tcamt.auth.model;

import gov.nist.hit.auth.core.model.HITToolUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Document
public class User implements HITToolUser<UserInfo> {
	@Id
	private String id;
	@Indexed(unique = true, collation = "{locale: \"en\", strength: 2}")
	private String username;
	private String password;
	@Indexed(unique = true, collation = "{locale: \"en\", strength: 2}")
	private String email;
	private boolean activePasswordLogin;
	@DBRef
	private List<UserRole> roles;
	private List<Identity> identities;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public List<Identity> getIdentities() {
		if(identities == null) {
			identities = new ArrayList<>();
		}
		return identities;
	}

	public void setIdentities(List<Identity> identities) {
		this.identities = identities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map((r) -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserRole> getRoles() {
		if(roles == null) {
			roles = new ArrayList<>();
		}
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isActivePasswordLogin() {
		return activePasswordLogin;
	}

	public void setActivePasswordLogin(boolean activePasswordLogin) {
		this.activePasswordLogin = activePasswordLogin;
	}

	@Override
	public UserInfo getPrincipal() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(username);
		userInfo.setId(id);
		userInfo.setRoles(roles.stream().map(UserRole::getRole).collect(Collectors.toSet()));
		return userInfo;
	}

}
