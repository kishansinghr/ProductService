package com.kishan.productservice.thirdparty;

import java.util.*;

class Solution {
    public static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    private ArrayList<Integer> answers;
    /* each pair corresponds to : ( location, serverID ) */
    private Map<Integer, String> locationToServerMapping;
    /* each pair corresponds to : ( serverID, <usernames> ) */
    private Map<String, List<Pair<String, Integer>>> serverToKeyMappings;

    private int userHash(String username, int hashKey) {
        final int p = hashKey;
        final int n = 360;
        long hashCode = 0;
        long p_pow = 1;
        for (int i = 0; i < username.length(); i++) {
            char character = username.charAt(i);
            hashCode = (hashCode + (character - 'A' + 1) * p_pow) % n;
            p_pow = (p_pow * p) % n;
        }
        return (int) (hashCode);
    }

    private int assignRequest(String keyname, int hashKey) {
        if (locationToServerMapping.size() == 0) {
            return -1;
        }
        int keyLocation = userHash(keyname, hashKey);
        Map.Entry<Integer, String> it = locationToServerMapping.entrySet().stream()
                .filter(e -> e.getKey() >= keyLocation)
                .findFirst()
                .orElse(locationToServerMapping.entrySet().stream().findFirst().get());
        /* when no server to right on the ring, assign the first server from start of the ring */
        String serverID = it.getValue();
        serverToKeyMappings.computeIfAbsent(serverID, k -> new ArrayList<>()).add(new Pair<>(keyname, hashKey));
        return it.getKey();
    }

    private void findRequestsToServe(int serverLocation, int hashKey) {
        if (serverToKeyMappings.size() == 0) {
            /* No requests to serve */
            return;
        }
        /* we need to find the requests which can now possibly be redirected to the added server */
        /* doing +1 because otherwise we'll get the location of the newly added server */
        Map.Entry<Integer, String> it = locationToServerMapping.entrySet().stream()
                .filter(e -> e.getKey() > serverLocation)
                .findFirst()
                .orElse(locationToServerMapping.entrySet().stream().findFirst().get());
        String serverID = it.getValue();
        /* these are the usernames which can possibly be redirected */
        List<Solution.Pair<String, Integer>> keynames = serverToKeyMappings.getOrDefault(serverID, new ArrayList<>());
        serverToKeyMappings.remove(serverID);
        for (Solution.Pair<String, Integer> entry : keynames) {
            /* may or may not get reassigned */
            assignRequest(entry.getKey(), entry.getValue());
        }
    }

    private int addServer(String serverID, int hashKey) {
        int firstLocation = userHash(serverID, hashKey);
        locationToServerMapping.put(firstLocation, serverID);
        findRequestsToServe(firstLocation, hashKey);
        return serverToKeyMappings.getOrDefault(serverID, new ArrayList<>()).size();
    }

    private int removeServer(String serverID, int hashKey) {
        for (Iterator<Map.Entry<Integer, String>> it = locationToServerMapping.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Integer, String> entry = it.next();
            if (entry.getValue().equals(serverID)) {
                it.remove();
                break;
            }
        }
        List<Solution.Pair<String, Integer>> keynamesToReassign = serverToKeyMappings.getOrDefault(serverID, Collections.emptyList());
        serverToKeyMappings.remove(serverID);
        for (Solution.Pair<String, Integer> keyname : keynamesToReassign) {
            assignRequest(keyname.getKey(), keyname.getValue());
        }
        return keynamesToReassign.size();
    }

    void performOperation(String A, String B, int C) {
        String operation = A;
        switch (operation) {
            case "ADD" -> {
                String serverID = B;
                answers.add(addServer(serverID, C));
            }
            case "REMOVE" -> {
                String serverID = B;
                answers.add(removeServer(serverID, C));
            }
            case "ASSIGN" -> {
                String keyname = B;
                answers.add(assignRequest(keyname, C));
            }
        }
    }

    public ArrayList<Integer> solve(ArrayList<String> A, ArrayList<String> B, ArrayList<Integer> C) {
        answers = new ArrayList<>();
        locationToServerMapping = new TreeMap<>();
        serverToKeyMappings = new HashMap<>();
        for (int i = 0; i < A.size(); i++) {
            performOperation(A.get(i), B.get(i), C.get(i));
        }
        return answers;
    }
}
