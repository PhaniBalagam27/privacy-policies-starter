package com.anonos.bigprivacy.demoprivacypolicy;

import com.anonos.bigprivacy.privacypolicyapi.DataType;
import com.anonos.bigprivacy.privacypolicyapi.Field;
import com.anonos.bigprivacy.privacypolicyapi.LoadablePrivacyPolicy;
import com.anonos.bigprivacy.privacypolicyapi.PrivacyPolicy;
import com.anonos.bigprivacy.privacypolicyapi.PrivacyPolicyRule;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Base Performance Metric Range A-DDID policy.
 */
@LoadablePrivacyPolicy
public class CTScanResultPolicy implements PrivacyPolicy {

    /**
     * Rule storage.
     */
    private static final ThreadLocal<List<PrivacyPolicyRule>> rules = new ThreadLocal<List<PrivacyPolicyRule>>() {
        @Override
        protected List<PrivacyPolicyRule> initialValue() {
            final List<PrivacyPolicyRule> rules = new ArrayList<>();
            rules.add(new ScanResultRule());
            return rules;
        }
    };

    @Override
    public String getId() {
        return "a9ea9ac0-10ee-4be7-a1e8-30a41191b1ae";
    }

    @Override
    public String getName() {
        return "Medical: CT Scan Result";
    }

    @Override
    public String getDescription() {
        return "Determines a normal or abnormal scan from its original numerical value (0-10)";
    }

    @Override
    public Set<DataType> getInputTypes() {
        return Sets.newHashSet(DataType.LONG_INTEGER);
    }

    @Override
    public List<PrivacyPolicyRule> getRules() {
        return rules.get();
    }

    protected static class ScanResultRule implements PrivacyPolicyRule {
        @Override
        public String getName() {
            return "ScanResult";
        }

        @Override
        public String getResult(Object input, List<Field> fields, List<Object> row) {
            final long num = ((Long)input).longValue();

            return num <= 5 ? "normal" : "abnormal";
        }
    }
}
