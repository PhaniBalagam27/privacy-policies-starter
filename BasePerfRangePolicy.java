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
public class BasePerfRangePolicy implements PrivacyPolicy {

    /**
     * Rule storage.
     */
    private static final ThreadLocal<List<PrivacyPolicyRule>> rules = new ThreadLocal<List<PrivacyPolicyRule>>() {
        @Override
        protected List<PrivacyPolicyRule> initialValue() {
            final List<PrivacyPolicyRule> rules = new ArrayList<>();
            rules.add(new RangeRule());
            return rules;
        }
    };

    @Override
    public String getId() {
        return "4d4fbd5b-b9e8-4b95-8518-740841a30c91";
    }

    @Override
    public String getName() {
        return "Base Performance: Range";
    }

    @Override
    public String getDescription() {
        return "Produces a range around a numeric value, bounded to multiples of 10";
    }

    @Override
    public Set<DataType> getInputTypes() {
        return Sets.newHashSet(DataType.LONG_INTEGER);
    }

    @Override
    public List<PrivacyPolicyRule> getRules() {
        return rules.get();
    }

    /**
     * Rule for computing a numeric range around a number, bounded by open-beginning multiples
     * of 10.  For example, the number 56 would produce a range of "51 - 60".
     */
    protected static class RangeRule implements PrivacyPolicyRule {
        @Override
        public String getName() {
            return "Range";
        }

        @Override
        public String getResult(Object input, List<Field> fields, List<Object> row) {
            final long num = ((Long)input).longValue();

            final long low = num <= 0 ? (num / 10) * 10 - 9 : ((num - 1) / 10) * 10 + 1;
            final long high = num <= 0 ? num - num % 10 : ((num - 1) / 10) * 10 + 10;

            return String.format("%s - %s", low, high);
        }
    }
}
