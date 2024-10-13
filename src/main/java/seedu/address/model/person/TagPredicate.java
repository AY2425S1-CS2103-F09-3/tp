package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the given keywords.
 */
public class TagPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().stream()
                .anyMatch(tag -> keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TagPredicate)) {
            return false;
        }
        TagPredicate otherPredicate = (TagPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }
}

