package lew.bing.algorithm;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by 刘国兵 on 2016/11/25.
 */
public class RedBlackTree<T extends Comparable<T>> implements Iterable<RedBlackTree.Node>{

    @Override
    public Iterator<Node> iterator() {
        return null;
    }

    public  class Node {
        private T value;
        private boolean red;
        private Node father;
        private Node lChild;
        private Node rChild;
        private RedBlackTree<T> tree ;

        public Node(T value, boolean red,RedBlackTree<T> tree) {
            this.value = value;
            this.red = red;
            this.tree = tree;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public boolean isRed() {
            return red;
        }

        public void setRed(boolean red) {
            this.red = red;
        }

        public Node getFather() {
            return father;
        }

        public void setFather(Node father) {
            this.father = father;
        }

        public Node getlChild() {
            return lChild;
        }

        public void setlChild(Node lChild) {
            this.lChild = lChild;
        }

        public Node getrChild() {
            return rChild;
        }

        public void setrChild(Node rChild) {
            this.rChild = rChild;
        }

        //左旋
        public void leftRotate() {
            Node y = rChild;
            rChild = y.lChild;
            if (y.lChild != tree.nil)
                y.lChild.father = this;
            y.father = this.father;
            if (this.father == tree.nil) {
                tree.root = y;
                nil.lChild = y;
                nil.rChild = y;
            }
            else if(this == this.father.lChild){
                this.father.lChild = y;
            }else {
                this.father.rChild = y;
            }
            y.lChild = this;
            this.father = y;
        }

        public void rightRotate() {
            Node y = lChild;
            lChild = y.rChild;
            if (y.rChild != tree.nil)
                y.rChild.father = this;
            y.father = this.father;
            if (this.father == tree.nil) {
                tree.root = y;
                nil.lChild = y;
                nil.rChild = y;
            }
            else if(this == this.father.rChild){
                this.father.rChild = y;
            }else {
                this.father.lChild = y;
            }
            y.rChild = this;
            this.father = y;
        }

    }

    private Node root;
    private Node nil;

    public RedBlackTree(T rootValue){
        nil = new Node(null,false,this);
        root = new Node(rootValue,false,this);
        root.father = nil;
        nil.lChild = root;
        nil.rChild = root;
    }

    public Node getNil() {
        return nil;
    }


    public Node getRoot() {
        return root;
    }

    public void insert(T value) {
        Node z = new Node(value,true,this);
        Node x = root;
        //因为都是指针操作，所以直接这样比较
        while (x != nil) {
            if (value.compareTo(x.value) < 0) {
                x = x.lChild;
            }else {
                x = x.rChild;
            }
        }
        Node y = x.father;
        z.father = y;
        if (y == nil) {
            root = z;
        }else if (z.value.compareTo(y.value) < 0) {
            y.lChild = z;
        }else {
            y.rChild = z;
        }
        z.lChild = nil;
        z.rChild = nil;
        fixUp(z);
    }

    private void fixUp(Node z) {
        while (z.father.isRed()) {
            if (z.father == z.father.father.lChild) {
                Node y = z.father.rChild;
                if (y.isRed()){
                    z.father.setRed(false);
                    y.setRed(false);
                    z.father.father.setRed(true);
                    z = z.father.father;
                }else if(z == z.father.rChild) {
                    z = z.father;
                    z.leftRotate();
                }
                z.father.setRed(false);
                z.father.father.setRed(true);
                z.rightRotate();
            }else {
                Node y = z.father.lChild;
                if (y.isRed()){
                    z.father.setRed(false);
                    y.setRed(false);
                    z.father.father.setRed(true);
                    z = z.father.father;
                }else if(z == z.father.lChild) {
                    z = z.father;
                    z.rightRotate();
                }
                z.father.setRed(false);
                z.father.father.setRed(true);
                z.leftRotate();
            }
        }
        root.setRed(false);
    }



}
