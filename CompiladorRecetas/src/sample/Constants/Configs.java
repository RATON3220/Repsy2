package sample.Constants;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by D10101 on 05/10/2018.
 */
public final class Configs {

    public  static final String UserName="";
    public static final String[] KEYWORDS = new String[] {
        "Funcion","Entero","Decimal","Graficar","IntDef"

    };

    public static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    public static final String PAREN_PATTERN = "\\(|\\)";
    public static final String BRACE_PATTERN = "\\{|\\}";
    public static final String BRACKET_PATTERN = "\\[|\\]";
    public static final String SEMICOLON_PATTERN = "\\;";
    public static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    public static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";
    public static final String EQUAL_PATTERN="=";
    public static final String ALL_PATTERN="\\w|,|\\^";

    public static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
                    + "|(?<EQUAL>" + EQUAL_PATTERN + ")"
                    + "|(?<ALL>" + ALL_PATTERN + ")"
    );

    public static final String sampleCode = String.join("\n", new String[] {
            "//Las variables solo pueden tener numeros al inicio o final\n"+
            "Funcion fun1 = x^2;\n" +
                    "Decimal a = 10;\n" +
                    "Entero B = 11;\n" +
                    "Graficar(fun1);\n" +
                    "IntDef(fun1,a,B);",

    });
    public static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            matcher.group("EQUAL") != null ? "equal":
                                                                                    matcher.group("ALL") != null ? "all":
                                                                            null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    public static String[] EXPRESIONES={
            "Funcion (\\d+)?[A-z]+(\\d+)? \\= ((\\-||\\+)?[X-x](\\^\\d+)?)+((;)|(;)\\s+)$",
            "Entero (\\d+)?[A-z]+(\\d+)? \\= [\\d]+((;)|(;)\\s+)$",
            "Decimal (\\d+)?[A-z]+(\\d+)? \\= [\\d]+((;)|(;)\\s+)$",
            "Graficar\\((\\d+)?[A-z]+(\\d+)?+\\)((;)|(;)\\s+)$",
            "IntDef\\((\\d+)?[A-z]+(\\d+)?\\,(\\d+)?[A-z]+(\\d+)?\\,(\\d+)?[A-z]+(\\d+)?\\)((;)|(;)\\s+)$",
            "^(\\/\\/)(\\/+)?|(^(\\/\\/)(\\/+)?((\\w+\\s+)+|(\\w+))(\\w+|\\w+\\s+))$"


    };

}
