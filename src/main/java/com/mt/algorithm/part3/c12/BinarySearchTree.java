package com.mt.algorithm.part3.c12;


import java.util.NoSuchElementException;

/**
 * @author mt
 * 二叉搜索树
 */
public class BinarySearchTree<E> {
    /**
     * 二叉搜索树根节点
     */
    private Node root;

    public void insert(int key, E item) {
        insertElement(key, item);
    }

    private void insertElement(int key, E item) {
        Node newNode = newNode(key, item);
        Node n = root;
        // the first node is root
        if (n == null) {
            // if root is null,add newNode as root
            root = newNode;
            return;
        }
        // p which is the parent of the newNode
        Node p = null;
        // find the parent of newNode
        while (n != null) {
            p = n;
            if (key > n.key) {
                n = n.right;
            } else {
                n = n.left;
            }
        }
        n = newNode;
        n.parent = p;
        // newNode is the rightChild or leftChild
        if (n.key > p.key) {
            p.right = n;
        } else {
            p.left = n;
        }
    }

    public E search(int key) {
        return searchVal(root, key).item;
    }

    private Node<E> searchVal(Node n, int key) {
        while (n != null && n.key != key) {
            if (key > n.key) {
                n = n.right;
            } else {
                n = n.left;
            }
        }
        return n;
    }

    public void inOrderWalk() {
        inOrder(root);
    }

    public E maxKeyValue() {
        Node<E> max = maxKeyVal(root);
        return max.item;
    }

    private Node<E> maxKeyVal(Node n) {
        while (n.right != null) {
            n = n.right;
        }
        return n;
    }

    public E minKeyValue() {
        Node<E> min = minKeyVal(root);
        return min.item;
    }

    private Node<E> minKeyVal(Node n) {
        while (n.left != null) {
            n = n.left;
        }
        return n;
    }

    private void inOrder(Node n) {
        if (n != null) {
            inOrder(n.left);
            System.out.println("key= " + n.key + "  value= " + n.item);
            inOrder(n.right);
        }
    }

    private Node newNode(int key, E item) {
        return new Node(key, item, null, null, null);
    }

    /**
     * 某个元素的后继
     * @param key
     * @return
     */
    public E successor(int key) {
        Node<E> succ = findSuccessor(key);
        return succ.item;
    }

    private void checkNodeExist(Node<E> node, int key) {
        if (node == null) {
            throw new NoSuchElementException("tree doesn't has a node which key is : " + key);
        }
    }

    /**
     * 给定一棵二叉搜索树中的一个节点,如果所有的关键字
     * 互不相同,则一个节点x的后继是大于x.key的最小关键
     * 字节点.
     * 如果节点x的右子树不为空,那么x的后继就是x右子树中
     * 的最左节点(x右子树的最小关键字节点)
     * 如果节点x的右子树为空,并且有一个后继,见代码描述
     * @param key 当前节点关键字
     * @return 当前节点的后继节点
     */
    private Node<E> findSuccessor(int key) {
        Node<E> curr = searchVal(root, key);
        checkNodeExist(curr, key);
        // 如果当前节点已经是最大关键字节点,那么它没有后继节点
        if (maxKeyVal(root) == curr) {
             throw new NoSuchElementException("the node which key is " + key + " is the maxKeyNode, has no successor");
        }
        // 如果当前节点有rightChild,那么它的后继节点就是
        // rightChild子树的最小关键字节点
        if (curr.right != null) {
            return minKeyVal(curr.right);
        }
        // 如果没有右子树,判断当前节点与其父节点的关系:
        //     1.如果当前节点是父节点的左孩子,那么该父节点就是后继节点
        //     2.如果当前节点是父节点的右孩子,那么迭代往上查找一个节点来满足条件1
        Node<E> p = curr.parent;
        while (p != null && curr == p.right) {
            curr = p;
            p = curr.parent;
        }
        return p;
    }

    /**
     * 某个元素的前驱
     * @param key
     * @return
     */
    public E predecessor(int key) {
        Node<E> pred = findPredecessor(key);
        return pred.item;
    }

    private Node<E> findPredecessor(int key) {
        Node<E> curr = searchVal(root, key);
        checkNodeExist(curr, key);
        // 如果当前节点已经是最小关键字节点,那么它没有前驱节点
        if (minKeyVal(root) == curr) {
            throw new NoSuchElementException("the node which key is " + key + " is the minKeyNode, has no predecessor");
        }
        // 当前节点存在左子树,那么前驱节点就是左子树中的最大关键字节点
        if (curr.left != null) {
            return maxKeyVal(curr.left);
        }
        // 当前节点不存在左子树,沿着curr往上查找,寻找一个节点p
        // p满足 p.left != curr 则p就是该节点的前驱节点
        Node<E> p = curr.parent;
        while (p != null && p.left == curr) {
            curr = p;
            p = curr.parent;
        }
        return p;
    }

    public void remove(int key) {
        Node<E> curr = searchVal(root, key);
        checkNodeExist(curr, key);
        removeNode(curr);
    }

    private void removeNode(Node<E> node) {
        if (node.left == null) {
            transplant(node,node.right);
        } else if (node.right == null) {
            transplant(node,node.left);
        } else {
            // y is the successor of node
            Node<E> y = minKeyVal(node.right);
            if (y.parent != node) {
                transplant(y, y.right);
                y.right = node.right;
                y.right.parent = y;
            }
            transplant(node, y);
            y.left = node.left;
            y.left.parent = y;
        }
    }

    /**
     * 用另一个子树来代替一个子树并成为其父节点的子节点
     * @param p 一个子树
     * @param child 另一个子树
     */
    private void transplant(Node<E> p, Node<E> child) {
        if (p.parent == null) {
            root = child;
        } else if (p == p.parent.left) {
            p.parent.left = child;
        } else {
            p.parent.right = child;
        }
        if (child != null) {
            child.parent = p.parent;
        }
    }


    class Node<E> {
        Integer key;
        E item;
        Node<E> parent;
        Node<E> left;
        Node<E> right;
        Node(Integer key, E item, Node<E> parent, Node<E> left, Node<E> right) {
            this.key = key;
            this.item = item;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(15, "j");
        bst.insert(18, "a");
        bst.insert(17, "v");
        bst.insert(20, "ax");
        bst.insert(6, "c");
        bst.insert(7, "+");
        bst.insert(13, "+");
        bst.insert(9, "p");
        bst.insert(3, "h");
        bst.insert(2, "p[][][");
        bst.insert(4, "4c");
        System.out.println("-----start search-----");
        System.out.println(bst.search(9));
        System.out.println(bst.search(15));
        System.out.println("-----start inOrderWalk-----");
        bst.inOrderWalk();
        System.out.println("-----start maxKeyValue-----");
        System.out.println(bst.maxKeyValue());
        System.out.println("-----start minKeyValue-----");
        System.out.println(bst.minKeyValue());
        System.out.println("-----start successor-----");
        System.out.println(bst.successor(13));
        System.out.println("-----start predecessor-----");
        System.out.println("15 predecessor : "+ bst.predecessor(15));
        System.out.println("17 predecessor : "+ bst.predecessor(17));
        System.out.println("6 predecessor : "+ bst.predecessor(6));
        System.out.println("-----start remove-----");
        bst.remove(18);
        System.out.println("-----start remove-----");
    }

}
