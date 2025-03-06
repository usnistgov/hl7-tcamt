package gov.nist.hit.hl7.tcamt.controller;
import gov.nist.hit.hl7.tcamt.model.TestPlanDescriptor;
import org.springframework.security.core.Authentication;

import gov.nist.hit.hl7.tcamt.domain.TestPlan;
import gov.nist.hit.hl7.tcamt.model.TestPlanCreate;
import gov.nist.hit.hl7.tcamt.service.TestPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import gov.nist.hit.hl7.tcamt.auth.model.UserInfo;

import java.util.List;

@RestController
public class TestPlanController {

    @Autowired
    TestPlanService testPlanService;

    @PostMapping("/api/testplans/create")
    @ResponseBody
    public TestPlan create(@RequestBody TestPlanCreate testPlanCreate,  UserInfo userInfo) {
        TestPlan newTestPlan = new TestPlan();
        newTestPlan.setName(testPlanCreate.getName());
        newTestPlan.setDescription(testPlanCreate.getDescription());
        newTestPlan.setAccountId(userInfo.getId());
        TestPlan savedTestPlan = testPlanService.save(newTestPlan);
        return savedTestPlan;
    }

    @GetMapping("/api/testplans/list")
    @ResponseBody
    public List<TestPlanDescriptor> getList(@RequestParam String type) {
        List<TestPlanDescriptor> testPlans = testPlanService.getTestPlansByType(type);
        return testPlans;
    }

}
