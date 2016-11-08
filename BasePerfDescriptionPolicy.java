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

@LoadablePrivacyPolicy
public class BasePerfDescriptionPolicy implements PrivacyPolicy {

    private static final ThreadLocal<List<PrivacyPolicyRule>> rules = new ThreadLocal<List<PrivacyPolicyRule>>() {
        @Override
        protected List<PrivacyPolicyRule> initialValue() {
            final List<PrivacyPolicyRule> rules = new ArrayList<>();
            rules.add(new DescriptionRule());
            return rules;
        }
    };

    @Override
    public String getId() {
        return "8584bca1-53e2-43d3-a87f-7c08ad5b22ac";
    }

    @Override
    public String getName() {
        return "Base Performance: Description";
    }

    @Override
    public String getDescription() {
        return "Base performance metric description";
    }

    @Override
    public Set<DataType> getInputTypes() {
        return Sets.newHashSet(DataType.LONG_INTEGER);
    }

    @Override
    public List<PrivacyPolicyRule> getRules() {
        return rules.get();
    }

    protected static class DescriptionRule implements PrivacyPolicyRule {
        @Override
        public String getName() {
            return "Description";
        }

        @Override
        public String getResult(Object input, List<Field> fields, List<Object> row) {
            final long num = ((Long)input).longValue();

            return num < 55L ? "low" : num < 70L ? "medium" : "high";
        }
    }
}
