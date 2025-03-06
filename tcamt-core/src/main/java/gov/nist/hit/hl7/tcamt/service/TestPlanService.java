package gov.nist.hit.hl7.tcamt.service;

import gov.nist.hit.hl7.tcamt.domain.TestPlan;
import gov.nist.hit.hl7.tcamt.model.TestPlanDescriptor;
import gov.nist.hit.hl7.tcamt.repository.TestPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TestPlanService {

    private final TestPlanRepository testPlanRepository;

    @Autowired
    public TestPlanService(TestPlanRepository testPlanRepository) {
        this.testPlanRepository = testPlanRepository;
    }

    public TestPlan findById(String id) {
        return testPlanRepository.findById(id).orElse(null);
    }

    public List<TestPlan> findByAccountId(String accountId) {

        return testPlanRepository.findByAccountId(accountId);
    }

    public TestPlan save(TestPlan testPlan) {
        return testPlanRepository.save(testPlan);
    }

    public void deleteById(String id) {
        testPlanRepository.deleteById(id);
    }

    public List<TestPlan> findAll() {
        return testPlanRepository.findAll();
    }

    public List<TestPlan> findByDomain(String domain) {

        return testPlanRepository.findByDomain(domain);
    }

    public boolean existsById(String id) {
        return testPlanRepository.existsById(id);
    }

    public TestPlan createTestPlan(String name, String description, String accountId, String domain) {
        TestPlan newTestPlan = new TestPlan();
        newTestPlan.setName(name);
        newTestPlan.setDescription(description);
        newTestPlan.setAccountId(accountId);
        newTestPlan.setDomain(domain);
        return save(newTestPlan);
    }

    public List<TestPlanDescriptor> getTestPlansByType(String type) {
        List<TestPlanDescriptor> testPlans = testPlanRepository.findAll()
                .stream()
                .map(testPlan -> {
                    TestPlanDescriptor descriptor = new TestPlanDescriptor();
                    descriptor.setId(testPlan.getId());
                    descriptor.setName(testPlan.getName());
                    return descriptor;
                })
                .toList();
        return testPlans;
    }



}