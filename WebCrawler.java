import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class WebCrawler {

    /*
     * Pattern to identify valid URLs
     */
    private static final String URL_PATTERN_REGEX =
            "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    private static final Pattern URL_PATTERN =
            Pattern.compile(URL_PATTERN_REGEX);

    public static void main(String[] args) throws Exception {

        // Auto-close Scanner after use
        try (Scanner sc = new Scanner(System.in)) {

            System.out.println("Enter the starting URL");

            String inputURL = sc.next();

            /*
             * Validate URL
             */
            if (!URL_PATTERN.matcher(inputURL).matches()) {

                throw new IllegalArgumentException(
                        String.format(
                                "%s isn't a valid URL",
                                inputURL
                        )
                );
            }

            System.out.println(
                    "Enter the maximum depth you want to continue crawling"
            );

            int maxLevel = sc.nextInt();

            /*
             * Depth should be greater than 0
             */
            if (maxLevel <= 0) {

                throw new IllegalArgumentException(
                        "maxLevel should be greater than 0"
                );
            }

            /*
             * Stores current level URLs
             */
            Set<String> seedURLs = new HashSet<>();

            /*
             * Stores newly discovered child URLs
             */
            Set<String> childLinks = new HashSet<>();

            /*
             * Add initial URL
             */
            seedURLs.add(inputURL);

            int currentLevel = 1;

            /*
             * Crawl until max depth
             */
            while (currentLevel <= maxLevel) {

                for (String link : seedURLs) {

                    /*
                     * Download webpage content
                     */
                    String content =
                            getWebPageContentAsString(link);

                    /*
                     * Skip invalid pages
                     */
                    if (content == null) {
                        continue;
                    }

                    /*
                     * Extract all links
                     */
                    List<String> clinks =
                            getAllLinksInString(content);

                    for (String childLink : clinks) {

                        /*
                         * Avoid duplicate URLs
                         */
                        if (!seedURLs.contains(childLink)) {

                            childLinks.add(childLink);
                        }
                    }
                }

                /*
                 * Print current level output
                 */
                printOutput(currentLevel, childLinks);

                /*
                 * Move next-level links into seedURLs
                 */
                seedURLs.clear();

                seedURLs.addAll(childLinks);

                childLinks.clear();

                currentLevel++;
            }

            System.out.println(
                    "\n************ END OF PROGRAM ************"
            );
        }
    }

    /**
     * Print all crawled links
     */
    private static void printOutput(
            int currentLevel,
            Set<String> links
    ) {

        int index = 1;

        System.out.println(
                "\n=========== START : DEPTH = "
                        + currentLevel
                        + " ==========="
        );

        for (String link : links) {

            System.out.println(index + ". " + link);

            index++;
        }

        System.out.println(
                "=========== END : DEPTH = "
                        + currentLevel
                        + " ===========\n"
        );
    }

    /**
     * Opens the webpage and returns its HTML content as String
     */
    private static String getWebPageContentAsString(
            String urlLink
    ) throws Exception {

        try {

            // Create connection from URL
            URLConnection urlConnection =
                    URI.create(urlLink)
                            .toURL()
                            .openConnection();

            // Prevent infinite waiting
            urlConnection.setConnectTimeout(5000);

            urlConnection.setReadTimeout(5000);

            // Browser-like request header
            urlConnection.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0"
            );

            // Auto-close resources
            try (
                    InputStream is =
                            urlConnection.getInputStream();

                    InputStreamReader isr =
                            new InputStreamReader(is)
            ) {

                int numCharsRead;

                // Character buffer
                char[] charArray = new char[1024];

                // Store webpage content
                StringBuilder sb =
                        new StringBuilder();

                /*
                 * Read webpage data
                 */
                while (
                        (numCharsRead =
                                isr.read(charArray)) > 0
                ) {

                    sb.append(
                            charArray,
                            0,
                            numCharsRead
                    );
                }

                /*
                 * Return webpage HTML
                 */
                return sb.toString();
            }

        } catch (
                IOException
                | IllegalArgumentException e
        ) {

            /*
             * Happens when:
             * - Website blocked
             * - Invalid URL
             * - SSL issue
             * - Timeout
             * - 404 page
             */

            return null;
        }
    }

    /**
     * Extract all valid links from webpage HTML
     */
    private static List<String> getAllLinksInString(
            String content
    ) throws IOException {

        // HTML parser
        HTMLEditorKit.Parser parser =
                new ParserDelegator();

        // Store extracted links
        final List<String> links =
                new ArrayList<>();

        /*
         * Auto-close reader
         */
        try (
                Reader reader =
                        new StringReader(content)
        ) {

            parser.parse(
                    reader,

                    new HTMLEditorKit.ParserCallback() {

                        @Override
                        public void handleStartTag(
                                HTML.Tag t,
                                MutableAttributeSet a,
                                int pos
                        ) {

                            /*
                             * Check anchor tag
                             */
                            if (t == HTML.Tag.A) {

                                Object link =
                                        a.getAttribute(
                                                HTML.Attribute.HREF
                                        );

                                if (link != null) {

                                    String linkValue =
                                            String.valueOf(link);

                                    /*
                                     * Accept valid links
                                     */
                                    if (
                                            linkValue.startsWith("http")
                                                    || linkValue.startsWith("www")
                                    ) {

                                        links.add(linkValue);
                                    }
                                }
                            }
                        }

                    },

                    true
            );
        }

        return links;
    }
}