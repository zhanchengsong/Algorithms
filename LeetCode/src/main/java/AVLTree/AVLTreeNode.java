package AVLTree;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;

@Setter
@Getter
public class AVLTreeNode {
    int value;
    AVLTreeNode left;
    AVLTreeNode right;
    int height;

    public AVLTreeNode (int value) {
       this.value = value;
       this.height = 1;
    }

    public int height(AVLTreeNode node) {
        return node == null ? 0 : node.height;
    }

    public static void BFSPrint(AVLTreeNode node) {
        Queue<AVLTreeNode> q = new LinkedList<>();

    }

    // Rotate a subtree at root and return the new root
    AVLTreeNode rightRotate(AVLTreeNode root) {
        AVLTreeNode left = root.left;
        AVLTreeNode lright = left.right;

        left.right = root;
        root.left = lright;
        // update height
        left.right.height = Math.max(height(root.right), height(lright)) + 1;
        left.height = Math.max(height(left.left), height(left.right)) + 1;

        return left;
    }

    AVLTreeNode leftRotate(AVLTreeNode root) {
        AVLTreeNode right = root.right;
        AVLTreeNode rleft = right.left;

        right.left = root;
        root.right = rleft;
        // update height
        right.left.height = Math.max(height(root.left), height(rleft)) + 1;
        right.height = Math.max(height(right.left), height(right.right)) + 1;

        return right;
    }

    // The insert function returns a node of the subtree where the value is inserted into
    AVLTreeNode insert(AVLTreeNode root, int value) {
        if (root == null) {
            return new AVLTreeNode(value);
        }
        if (value > root.value) {
            root.right = insert(root.right, value);
        }
        else if (value < root.value) {
            root.left = insert(root.left, value);
        }
        else {
            return root; // if already exists do nothing
        }

        // After we insert into the node, we check for balance
        int balance = height(root.left) - height(root.right);
        if (balance > 1 && value < root.left.value) { // Left heavy with subleft heavy
            return rightRotate(root);
        }
        if (balance < -1 && value > root.right.value ) { // Right heavy with subright heavy
            return leftRotate(root);
        }
        if (balance > 1 && value > root.left.value) { // left heavy with subright heavy
            // Rotate subright to make subleft heavy
            root.right = leftRotate(root.right);
            return rightRotate(root);
        }
        if (balance < -1 && value < root.right.value ) { // right heavy with subleft heavy
            root.left = rightRotate(root.left);
            return leftRotate(root);
        }
        return root; // if we finished either insert into left or right

    }
}
