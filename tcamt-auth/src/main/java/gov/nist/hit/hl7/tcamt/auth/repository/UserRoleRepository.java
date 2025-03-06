package gov.nist.hit.hl7.tcamt.auth.repository;

import gov.nist.hit.hl7.tcamt.auth.model.UserRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends MongoRepository<UserRole, String> {
	UserRole findByRole(String role);
}
