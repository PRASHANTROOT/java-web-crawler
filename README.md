# Java Web Crawler

A simple command-line web crawler written in Java that starts from a user-provided URL, discovers links from webpages, and continues crawling them up to a user-defined depth.

## Overview

This project demonstrates the basic concepts behind web crawling using Java.

The crawler:

1. Takes a starting URL from the user.
2. Validates the URL.
3. Downloads the webpage.
4. Extracts links from HTML anchor (`<a>`) elements.
5. Removes duplicate links using sets.
6. Moves discovered links to the next crawling depth.
7. Continues until the selected maximum depth is reached.

## Features

- URL validation
- Depth-based crawling
- HTML webpage downloading
- HTML link extraction
- Duplicate URL handling
- Connection timeout
- Read timeout
- Browser-like User-Agent
- Console-based output

## How It Works

```text
                    Starting URL
                         |
                         v
                  Download webpage
                         |
                         v
                    Parse HTML
                         |
                         v
                  Extract links
                         |
                         v
                  Remove duplicates
                         |
                         v
                   Next depth
                         |
                         v
                Repeat until limit
```

## Requirements

- Java Development Kit (JDK) 8 or later
- Terminal / Command Prompt / PowerShell

Check your Java installation:

```bash
java -version
```

Check the Java compiler:

```bash
javac -version
```

## Installation

Clone the repository:

```bash
git clone https://github.com/YOUR-USERNAME/java-web-crawler.git
```

Move into the project directory:

```bash
cd java-web-crawler
```

## Compile

Compile the Java source:

```bash
javac WebCrawler.java
```

## Run

Start the crawler:

```bash
java WebCrawler
```

The program will ask for a starting URL:

```text
Enter the starting URL
```

Example:

```text
https://example.com
```

It will then ask for the maximum crawling depth:

```text
Enter the maximum depth you want to continue crawling
```

Example:

```text
2
```

## Example

```text
Enter the starting URL
https://example.com

Enter the maximum depth you want to continue crawling
2
```

The crawler displays discovered URLs grouped by crawling depth.

Example output:

```text
=========== START : DEPTH = 1 ===========

1. https://example.com/page1
2. https://example.com/page2

=========== END : DEPTH = 1 ===========
```

The exact output depends on the website being crawled.

## Crawling Depth

The depth determines how far the crawler follows discovered links.

### Depth 1

```text
Starting URL
     |
     +---- Link A
     +---- Link B
     +---- Link C
```

### Depth 2

```text
Starting URL
     |
     +---- Link A
     |       |
     |       +---- Link A1
     |       +---- Link A2
     |
     +---- Link B
             |
             +---- Link B1
             +---- Link B2
```

For testing, start with a depth of `1` or `2`.

Higher depths can result in many more pages being requested.

## Project Structure

```text
java-web-crawler/
│
├── WebCrawler.java
├── README.md
└── .gitignore
```

Compiled `.class` files are intentionally excluded from the repository.

## Technologies

- Java
- Java Networking APIs
- HTML Parser
- Java Collections
- Regular Expressions

## Java APIs Used

The project uses Java's built-in APIs rather than external libraries.

Important classes include:

```text
java.net.URI
java.net.URLConnection
java.util.HashSet
java.util.ArrayList
java.util.Scanner
javax.swing.text.html.HTMLEditorKit
javax.swing.text.html.parser.ParserDelegator
```

## Error Handling

The crawler handles webpage connection problems such as:

- Invalid URLs
- Connection failures
- Connection timeouts
- Read timeouts
- SSL-related issues
- Some HTTP/page access failures

Unavailable pages are skipped instead of stopping the entire crawling process.

## Limitations

This is a basic educational web crawler and is not intended to be a production-scale search engine crawler.

Current limitations include:

- Command-line interface only
- No database storage
- No graphical dashboard
- Basic URL handling
- Limited relative URL handling
- No JavaScript rendering
- No distributed crawling
- No advanced crawl scheduling
- Some websites may block automated requests

## Responsible Crawling

Only crawl websites where you have permission or where automated access is allowed.

Before crawling a website, consider:

- `robots.txt`
- Website terms of service
- Crawl frequency and server load
- Rate limits
- Applicable laws and policies

Avoid sending excessive requests to websites.

## Future Improvements

Possible future improvements include:

- Better URL normalization
- Relative URL resolution
- `robots.txt` support
- Crawl delays and rate limiting
- Domain restrictions
- Maximum URL limits
- Multithreaded crawling
- Crawl statistics
- CSV/JSON export
- Database storage
- Web-based monitoring dashboard
- Improved logging
- Better error reporting

## Purpose

This project was created to explore practical concepts in:

- Java programming
- Networking
- HTTP connections
- HTML parsing
- URL extraction
- Collections
- Exception handling
- Depth-based traversal



