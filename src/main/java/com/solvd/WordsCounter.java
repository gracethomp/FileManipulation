package com.solvd;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class WordsCounter {
    private static final String FILE_OUTPUT = "src/main/resources/text_output.txt";

    private static final Logger LOGGER = Logger.getLogger(WordsCounter.class);

    public static void countWords(String filePath) {
        try {
            String lines = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
            lines = StringUtils.lowerCase(RegExUtils.replaceAll(lines, "\\p{Punct}", ""));
            String[] strings = StringUtils.split(lines);
            Map<String, Integer> map = new HashMap<>();
            for (String s : strings)
                map.merge(s, 1, Integer::sum);
            if(new File(FILE_OUTPUT).exists())
                FileUtils.delete(new File(FILE_OUTPUT));
            for (String s : map.keySet())
                FileUtils.write(new File(FILE_OUTPUT), s + " - " + map.get(s) + '\n', StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            LOGGER.error("File error while words counting");
        }

    }
}
