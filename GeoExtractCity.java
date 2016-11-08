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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@LoadablePrivacyPolicy
public class GeoExtractCity implements PrivacyPolicy {

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
        return "590ccfd5-a9ca-4be7-93e4-eb8e098280b5";
    }

    @Override
    public String getName() {
        return "Geo: Extract City";
    }

    @Override
    public String getDescription() {
        return "Extract city from address";
    }

    @Override
    public Set<DataType> getInputTypes() {
        return Sets.newHashSet(DataType.TEXT);
    }

    @Override
    public List<PrivacyPolicyRule> getRules() {
        return rules.get();
    }

    protected static class DescriptionRule implements PrivacyPolicyRule {

        private static final Pattern RE_FRA = Pattern.compile("^.*?,\\s+\\d+,\\s+([\\w\\s]+)\\s*$");
        private static final Pattern RE_USA = Pattern.compile("^.*?,\\s+([\\w\\s]+),\\s+[A-Z]{2},\\s+\\d+$");

        @Override
        public String getName() {
            return "City";
        }

        @Override
        public String getResult(Object input, List<Field> fields, List<Object> row) {
            final String address = (String)input;

            final Matcher[] matchers = new Matcher[] {
                RE_FRA.matcher(address),
                RE_USA.matcher(address)
            };

            String city = null;
            for (final Matcher matcher : matchers) {
                if (matcher.matches()) {
                    city = matcher.group(1);
                    break;
                }
            }

            return city;
        }
    }
}
