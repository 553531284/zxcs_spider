package com.dz.novel.utils;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Deng Zhou
 * @Description: 字典树
 * @Date: 1:09 2020/1/20
 */
public class NovelTrie {

    private NovelTrie() {
    }

    private static class SingleTonHoler {
        private static NovelTrie INSTANCE = new NovelTrie();
    }

    public static NovelTrie getInstance() {
        return SingleTonHoler.INSTANCE;
    }

    private Node root = new Node();

    public void insert(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - '0';
            cur.addChild(word.charAt(i));
            cur = cur.getChild(idx);
        }
        cur.isEnd = true;
    }

    public Boolean find(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - '0';
            if (cur.getChild(idx) == null) {
                return false;
            }
            cur = cur.getChild(idx);
        }
        return cur.isEnd == true;
    }

    private class Node {
        boolean isEnd = false;
        Node[] children = new Node[10];
        ReentrantLock[] locks = new ReentrantLock[10];

        public Node() {
            for (int i = 0; i < 10; i++) {
                locks[i] = new ReentrantLock();
            }
        }

        public void addChild(char c) {
            int idx = c - '0';
            locks[idx].lock();
            try {
                if (children[idx] != null) {
                    return;
                }
                children[idx] = new Node();
            } finally {
                locks[idx].unlock();
            }
        }

        public Node getChild(int idx) {
            Node res;
            res = children[idx];
            return res;
        }
    }
}
