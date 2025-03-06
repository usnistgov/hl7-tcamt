package gov.nist.hit.hl7.tcamt.repository;

import gov.nist.hit.hl7.tcamt.domain.TestPlan;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TestPlanRepository extends MongoRepository<TestPlan, String> {
    public Optional<TestPlan> findById(String id);
    List<TestPlan> findByAccountId(String accountId);
    TestPlan save(TestPlan testPlan);
    void deleteById(String id);
    List<TestPlan> findAll();
    List<TestPlan> findByDomain(String domain);
    boolean existsById(String id);
    List<TestPlan> findByType(String type);
}