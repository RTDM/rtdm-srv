package hooks;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class HooksResourceTest {

    private static Pattern CARD_REF_PATTERN = Pattern.compile(".*#(\\d+).*");

    @Test
    public void should_find_properly_card_ref_in_commit_msg() throws Exception {

        List<String> msgs = Arrays.asList(
                "#1 : Fix issue 1 ",
                "#2 : Fix issue 2 ",
                "#3 : Fix issue 3 "
        );

        for (String msg : msgs) {
            Matcher matcher = CARD_REF_PATTERN.matcher(msg);
            assertThat(matcher.matches()).isTrue();
            assertThat(matcher.groupCount()).isEqualTo(1);
            assertThat(matcher.group(1).length()).isEqualTo(1);
        }
    }
}
