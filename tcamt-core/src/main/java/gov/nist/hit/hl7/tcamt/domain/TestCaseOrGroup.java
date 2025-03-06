package gov.nist.hit.hl7.tcamt.domain;

import org.springframework.data.annotation.Id;


public abstract class TestCaseOrGroup {
    @Id
    protected String id;
    protected Long longId;
    protected String name;
    protected String description;
    protected Integer version;
    protected Type type;
    public TestCaseOrGroup() {
        super();
    }
}
