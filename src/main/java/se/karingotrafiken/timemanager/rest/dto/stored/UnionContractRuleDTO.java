package se.karingotrafiken.timemanager.rest.dto.stored;

import org.hibernate.validator.constraints.NotEmpty;
import se.karingotrafiken.timemanager.rest.appmodel.common.RuleType;
import se.karingotrafiken.timemanager.rest.dto.DtoObject;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UnionContractRuleDTO implements DtoObject {

    private long id;

    private long contractId;

    @NotNull(message = "ruleType can not be null")
    @NotEmpty(message = "ruleType can not be empty")
    private RuleType ruleType;

    @Min(value = 1, message = "coefficient must be > 0")
    private double coefficient;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
}
