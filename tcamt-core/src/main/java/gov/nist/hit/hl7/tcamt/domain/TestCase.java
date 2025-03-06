package gov.nist.hit.hl7.tcamt.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Document
public class TestCase extends TestCaseOrGroup {
    List<TestStep> testSteps = new ArrayList<>();
    public TestCase(){
        super();
        this.type = Type.TESTCASE;
    }

    public void setTestSteps(List<TestStep> testSteps) {
        this.testSteps = testSteps;
    }

    public List<TestStep> getTestSteps() {
        return testSteps;
    }
}
