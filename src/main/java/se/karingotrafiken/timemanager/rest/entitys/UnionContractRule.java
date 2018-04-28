package se.karingotrafiken.timemanager.rest.entitys;

import se.karingotrafiken.timemanager.rest.appmodel.common.RuleType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"ruleType", "unionContract_id"})})
public class UnionContractRule implements DbEntity {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private UnionContract unionContract;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RuleType ruleType;

    @Column(nullable = false)
    private double coefficient;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public UnionContract getUnionContract() {
        return unionContract;
    }

    public void setUnionContract(UnionContract unionContract) {
        this.unionContract = unionContract;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnionContractRule that = (UnionContractRule) o;
        return id == that.id && ruleType == that.ruleType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, unionContract, ruleType, coefficient);
    }
}
