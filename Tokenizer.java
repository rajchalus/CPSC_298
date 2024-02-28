import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    public static ArrayList<String> tokenize(String text) {
        ArrayList<String> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b\\w+\\b");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    public static void main(String[] args) {
        String text = "This is an example sentence.";
        ArrayList<String> tokens = tokenize(text);
        System.out.println(tokens);
    }
}
