package com.researchConnect.recommendation.service;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TikaExtractionService {
    private final Tika tika;

    public TikaExtractionService(Tika tika) {
        this.tika = tika;
    }
    public String extractText(byte[] pdfBytes) throws IOException, TikaException {
        return tika.parseToString(new ByteArrayInputStream(pdfBytes));
    }

    public String extractAbstract(String fullText) {
        if (fullText == null || fullText.isBlank()) {
            return "";
        }

        String normalizedText = fullText.replaceAll("\\r\\n|\\r", "\n");

        Pattern pattern = Pattern.compile(
                "(?i)abstract[\\s:—]*\\n?\\s*(.*?)(?=\\n\\s*(?:\\d\\.?\\s+|i+\\.?\\s+|introduction|keywords|key\\s*words))",
                Pattern.DOTALL
        );

        Matcher matcher = pattern.matcher(normalizedText);
        String result;

        if (matcher.find()) {
            result = matcher.group(1).trim();
            if (result.length() > 3500) {
                result = result.substring(0, 2500).trim();
            }
        } else {
            result = normalizedText.substring(0, Math.min(1500, normalizedText.length())).trim();
        }

        // If there's a clear paragraph break, keep only the first paragraph —
        // this drops trailing footnotes/disclaimers without needing to know their exact wording
        String[] paragraphs = result.split("\\n\\s*\\n", 2);
        if (paragraphs.length > 1 && paragraphs[0].length() > 100) { // guard against a false-split on a short first line
            result = paragraphs[0];
        }

        return cleanExtractedText(result);
    }

    private String cleanExtractedText(String text) {
        return text
                .replaceAll("-\\n", "")       // rejoin hyphenated words split across lines
                .replaceAll("\\n+", " ")      // collapse remaining line breaks into spaces
                .replaceAll("\\s{2,}", " ")   // collapse any resulting double-spaces
                .trim();
    }
}