package gov.nist.hit.hl7.tcamt.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TestStep {
    @Id
    private String id;
    private String name;
    private String description;
    private Type type;
    private TestStepDirection testStepDirection;

    public TestStep() {
        super();
        this.type = Type.TESTSTEP;
    }

}
