package gov.nist.hit.hl7.tcamt.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

@Document
public class TestCaseGroup extends TestCaseOrGroup {

    private String id;
    protected String name;
    private Set<TestCaseOrGroup> children = new HashSet<TestCaseOrGroup>();
    private String description;
    public TestCaseGroup() {
        super();
        this.type = Type.TESTCASEGROUP;
    }


}
