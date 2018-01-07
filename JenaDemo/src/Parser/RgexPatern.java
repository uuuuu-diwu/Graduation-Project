package Parser;

import java.util.regex.Pattern;

public class RgexPatern {
    public static String  chapterPatternStr = "第.+章";
    public static String knowledgePointStr = "【(.*)】";
    public static Pattern chapterPattern = Pattern.compile(chapterPatternStr);
    public static Pattern knowledgePointPattern = Pattern.compile(knowledgePointStr);
}
