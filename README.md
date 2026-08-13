# Java Web Crawler

A simple command-line web crawler written in Java.

The program starts from a user-provided URL, downloads the webpage,
extracts links from the HTML, and continues crawling discovered links
up to a user-defined depth.

## Features

- URL validation
- User-defined crawling depth
- HTML webpage downloading
- HTML link extraction
- Duplicate URL handling
- Connection timeout
- Read timeout
- Browser-like User-Agent
- Console-based output
- Level/depth-based crawling

## How It Works

The crawler starts with a seed URL.

```text
Starting URL
     |
     v
Download webpage
     |
     v
Extract links
     |
     v
Store discovered URLs
     |
     v
Move to next depth
     |
     v
Repeat until maximum depth