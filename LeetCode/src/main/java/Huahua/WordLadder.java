package Huahua;

import java.util.*;

public class WordLadder {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        // Game Plan:
        // Use BFS to expand words to form a graph, use DFS to return the path

        Set<String> dict = new HashSet<>();
        dict.addAll(wordList); // Create a graph for all the words
        if (!dict.contains(endWord)) {
            return new ArrayList<List<String>>();
        }
        // Remove the start word and end word so it does not confuses
        dict.remove(beginWord);
        dict.remove(endWord);
        // Create data structures for the graph
        Map<String, Integer> level = new HashMap<>();
        level.put(beginWord,1);

        Map<String, List<String>> children = new HashMap<>();
        // Queue for BFS
        Queue<String> q = new LinkedList<>();
        // Prepare for BFS start
        int step = 0;
        int len = beginWord.length(); // Since for every ladder step, the word length does not change
        boolean found = false; // if the endWord is found

        // Start BFS
        q.offer(beginWord);
        while(!q.isEmpty() && !found) {
            step++;
            int qsize = q.size();
            for (int size = 0; size < qsize; size++) { // look at a single layer, start expanding
                String curr = q.poll();
                for (int i = 0; i < len; i ++) { // At each character position
                    char[] charArr = curr.toCharArray();
                    char ch = charArr[i];
                    for (char c = 'a'; c <='z'; c++) {
                        if (c == ch) continue;
                        charArr[i] = c;
                        String child = new String(charArr);
                        // Check if we reached end
                        if (child.equals(endWord)) {
                            if (children.get(curr) == null) {
                                children.put(curr, new ArrayList<>());
                                children.get(curr).add(child);
                            }
                            else {
                                children.get(curr).add(child);
                            }
                            found = true;
                            System.out.println("Found end word: " + child + " at level " + (step + 1 ));
                        } else { // check if it is the same word generated at this level !!!
                            if (level.containsKey(child) && step < level.get(child)) { // cuz current step is nex
                                // If the word had already been calculated
                                // Adding this here to avoid removing the word from dict too early
                                System.out.println("Same word : " + child);
                                if (children.get(curr) == null) {
                                    children.put(curr, new ArrayList<>());
                                    children.get(curr).add(child);
                                }
                                else {
                                    children.get(curr).add(child);
                                }
                            }
                        }
                        // if we have not seen this word
                        if (!dict.contains(child)) continue;
                        // if child is a valid transform
                        System.out.println("New valid transform: " + child + " at level " + (step+1) );
                        dict.remove(child); // As we can only transform to this word once
                        q.offer(child); //participate in next iteration
                        level.put(child, step + 1);
                        if (children.get(curr) == null) {
                            children.put(curr, new ArrayList<>());
                            children.get(curr).add(child);
                        }
                        else {
                            children.get(curr).add(child);
                        }
                    }
                }
            }
        }
        List<List<String>> result = new ArrayList<>();

        if(found) {
            List<String> path = new ArrayList<>();
            path.add(beginWord);
            dfs(beginWord, endWord, children, path, result);
        }

        return result;
    }

    public static void dfs(String curr, String end,Map<String, List<String>> children ,List<String> path, List<List<String>> paths) {
        if (curr.equals(end)) { // if we can reach end
            paths.add(new ArrayList<String>(path));
            return;
        }
        for (String child : children.get(curr)) {
            path.add(child);
            dfs(child, end, children, path, paths);
            path.remove(path.size()-1);
        }
    }

    public static void main(String[] args) {
        String begin = "a";
        String end = "c";
        String[] wordList = new String[]{"a","b","c"};
        List<String> wordArray = Arrays.asList(wordList);
        WordLadder solution = new WordLadder();
        solution.findLadders(begin, end, wordArray);

    }
}
