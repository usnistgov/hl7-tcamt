package gov.nist.hit.hl7.tcamt.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document
public class TestPlan {
    @Id
    private String id;
    private String name;
    private String description;
    private TestPlanType type;
    private String domain;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
    private String accountId;
    private TestPlanConfig config;
    private TestStoryContent testStoryContent;

    private Set<TestCaseOrGroup> children = new HashSet<TestCaseOrGroup>();

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Set<TestCaseOrGroup> getChildren() {
        return children;
    }

    public void setChildren(Set<TestCaseOrGroup> children) {
        this.children = children;
    }

    public TestPlanConfig getConfig() {
        return config;
    }

    public void setConfig(TestPlanConfig config) {
        this.config = config;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestPlanType getType() {
        return type;
    }

    public void setType(TestPlanType type) {
        this.type = type;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TestStoryContent getTestStoryContent() {
        return testStoryContent;
    }

    public void setTestStoryContent(TestStoryContent testStoryContent) {
        this.testStoryContent = testStoryContent;
    }
}
