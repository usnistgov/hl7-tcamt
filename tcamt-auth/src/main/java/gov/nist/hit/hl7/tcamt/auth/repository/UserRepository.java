package gov.nist.hit.hl7.tcamt.auth.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import gov.nist.hit.hl7.tcamt.auth.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	User findByUsernameIgnoreCase(String username);
	User findByEmailIgnoreCase(String email);
	@Query("{ 'identities': { $elemMatch: { 'issuer': ?0, 'uid': ?1 } } }")
	User findByIdentity(String issuer, String uid);
}
