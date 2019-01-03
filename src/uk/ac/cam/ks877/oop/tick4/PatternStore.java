package uk.ac.cam.ks877.oop.tick4;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class PatternStore {

    private List<Pattern> patterns = new LinkedList<>();
    private Map<String,List<Pattern>> mapAuths = new HashMap<>();
    private Map<String, Pattern> mapName = new HashMap<>();

    public PatternStore(String source) throws IOException {
        //decides whether its a website or a file
        if (source.startsWith("http://") || source.startsWith("https://")) {
            loadFromURL(source);
        }
        else {
            loadFromDisk(source);
        }
    }

    public PatternStore(Reader source) throws IOException {
        load(source);
    }

    private void load(Reader r) throws IOException {
        try {
            BufferedReader b = new BufferedReader(r);
            String line;
            //loops through each line of the file/website
            while ( (line = b.readLine()) != null) {
                //in a try catch block to ensure that the line isn't malformed
                try {
                    Pattern p = new Pattern(line);
                    String author = p.getAuthor();
                    String name = p.getName();
                    patterns.add(p);
                    //checking if author is already in list of patterns
                    if(mapAuths.containsKey(author)) {
                        mapAuths.get(author).add(p);
                    }
                    else {
                        List<Pattern> pattern_list = new LinkedList<>();
                        pattern_list.add(p);
                        //adds new key, value to mapAuths
                        mapAuths.put(author, pattern_list);
                    }
                    //adds name of pattern to mapName
                    mapName.put(name, p);

                }
                catch(PatternFormatException e) {
                    System.out.println("Malformed line: " + line);
                }
            }
        }
        catch (Exception e) {
            throw new IOException(e);
        }

    }


    private void loadFromURL(String url) throws IOException {
        // Creates a Reader for the URL and then call load on it
        URL destination = new URL(url);
        URLConnection conn = destination.openConnection();
        Reader r = new InputStreamReader(conn.getInputStream());
        load(r);
    }

    private void loadFromDisk(String filename) throws IOException {
        // Creates a Reader for the file and then call load on it
        Reader r = new FileReader(filename);
        load(r);
    }


    public List<Pattern> getPatternsNameSorted() {
        // Gets a list of all patterns sorted by name
        List<Pattern> copy = new LinkedList<Pattern>(patterns);
        Collections.sort(copy, byName);
        return copy;
    }

    Comparator<Pattern> byAuthor = (Pattern p, Pattern q) -> {
        return p.getAuthor().compareToIgnoreCase(q.getAuthor());
    };

    Comparator<Pattern> byName = (Pattern p, Pattern q) -> {
        return p.getName().compareToIgnoreCase(q.getName());
    };

    Comparator<Pattern> byAuthorThenName = byAuthor.thenComparing(byName);

    public List<Pattern> getPatternsAuthorSorted() {
        // Gets a list of all patterns sorted by author then name
        List<Pattern> copy = new LinkedList<Pattern>(patterns);
        // inplace method to allow comparison to be done by author name
        Collections.sort(copy, byAuthorThenName);
        return copy;
    }

    public List<Pattern> getPatternsByAuthor(String author) throws PatternNotFound {
        // returns a list of patterns from a particular author sorted by name
        // throws exception if author not in getAuths
        if(mapAuths.containsKey(author)) {
            List<Pattern> authPat = new LinkedList<Pattern>(mapAuths.get(author));
            Collections.sort(authPat, byName);
            return authPat;
        }
        else {
            throw new PatternNotFound("No patterns by " + author);
        }

    }

    public Pattern getPatternByName(String name) throws PatternNotFound {
        // Get a particular pattern by name
        if(mapName.containsKey(name)) {
            return mapName.get(name);
        }
        else {
            throw new PatternNotFound("No pattern called " + name);
        }
    }

    public List<String> getPatternAuthors() {
        //Gets a sorted list of all pattern authors in the store
        // get a set of authors and then convert to list
        Set<String> authors = mapAuths.keySet();
        //int n = authors.size();
        List <String> authorList = new LinkedList<String>();
        //iterate through set and add to list
        for(String author : authors) {
            authorList.add(author);
        }

        Collections.sort(authorList, new Comparator<String>() {
            public int compare(String author1, String author2) {
                return author1.compareToIgnoreCase(author2);
            }
        });

        return authorList;
    }

    public List<String> getPatternNames() {
        // Gets a list of all pattern names in the store,
        // sorted by name
        // iterate through patterns and add name to new list of names
        List<String> nameList = new LinkedList<String>();
        for(Pattern pattern : patterns) {
            nameList.add(pattern.getName());
        }
        Collections.sort(nameList, new Comparator<String>() {
            public int compare(String name1, String name2) {
                return name1.compareToIgnoreCase(name2);
            }
        });
        return nameList;
    }

    public static void print_list(List<Pattern> patternList) {

        for(Pattern p: patternList) {
            p.print();
        }
    }
}