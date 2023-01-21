import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main extends JPanel {
    static int number = 100;
    static int root[][] = new int[number + 1][number + 1];
    static int rootNum[][] = new int[number + 1][number + 1];
    static int costs[][] = new int[number + 1][number + 1];
    static ArrayList<Integer> integers = new ArrayList<>();
    static ArrayList<Integer> count = new ArrayList<>();

    public static void main(String[] args) {

        int keys[] = new int[number];
        int freq[] = new int[number];
        int n = number;

        int temp;
        for (int i = 0; i < number; i++) {
            temp = (int) (Math.random() * 1000) + 1;
            while (integers.contains(temp)) {
                temp = (int) (Math.random() * 100) + 1;
            }
            integers.add(temp);
            keys[i] = temp;
            freq[i] = (int) (Math.random() * 100) + 1;
        }
        Collections.sort(integers);
        for (int i = 0; i < integers.size(); i++) {
            keys[i] = integers.get(i);
        }

//        keys = new int[]{4, 6, 9};
//        freq = new int[]{7, 2, 1};

        int optimalBSTCost = optimalSearchTree(keys, freq, n);
        System.out.println("Cost of Optimal BST is " + optimalBSTCost);
        integers = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            integers.add(keys[i]);
        }
        for (int i = 0; i < freq.length; i++) {
            count.add(freq[i]);
        }
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (root[i][j] == 0) {
                    rootNum[i][j] = -1;
                } else {
                    for (int k = 0; k < integers.size(); k++) {
                        if (root[i][j] == integers.get(k)) {
                            rootNum[i][j] = k;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < number; i++) {
            for (int j = 0; j < number; j++) {
                System.out.print(rootNum[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(integers.toString());
        System.out.println(count.toString());
        System.out.println(root[0][number - 1]);
        Node rootNode = createBST(0, number);
        JFrame jf = new JFrame("Optimal BST Cost " + optimalBSTCost);
        jf.setSize(3800, 3800);
        jf.setLocationRelativeTo(null);
        JTree jt = new JTree(translate2SwingTree(rootNode));
        jf.add(jt);
        openSubnodes(0, jt);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
        JFrame jf2 = new JFrame();
        jf2.setSize(200, 70);
        jf2.setLocationRelativeTo(null);
        String[] list = new String[100];
        for (int i = 0; i < integers.size(); i++) {
            list[i] = (" elemet " + (i + 1) + ": " + integers.get(i) + " frequncy " + count.get(i));
        }
        JComboBox cb = new JComboBox(list);
        cb.setBounds(50, 100, 90, 20);
        jf2.add(cb);
        openSubnodes(0, jt);
        jf2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jf2.setVisible(true);
    }

    static int optimalSearchTree(int keys[], int freq[], int n) {


        int cost[][] = new int[n + 1][n + 1];
        for (int i = 0; i < n; i++)
            cost[i][i] = freq[i];
        for (int L = 2; L <= n + 1; L++) {
            for (int i = 0; i <= n - L + 1; i++) {
                int j = i + L - 1;
                cost[i][j] = Integer.MAX_VALUE;
                int off_set_sum = sum(freq, i, j);
                for (int r = i; r <= j; r++) {
                    int c = ((r > i) ? cost[i][r - 1] : 0)
                            + ((r < j) ? cost[r + 1][j] : 0) + off_set_sum;
                    if (c < cost[i][j]) {
                        cost[i][j] = c;
                        root[i][j] = keys[r];
                    }
                }
            }
        }
        int[][] temp = new int[number + 1][number + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                temp[i][j] = root[i][j - 1];
            }
        }
        root = temp;
        temp = new int[number + 1][number + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                temp[i][j] = cost[i][j - 1];
            }
        }
        cost = temp;

        System.out.println("cost");
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                System.out.print(cost[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("root");
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                System.out.print(root[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("root num");
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                System.out.print(rootNum[i][j] + " ");
            }
            System.out.println();
        }
        costs = cost;
        return cost[0][n];
    }

    static int sum(int freq[], int i, int j) {
        int s = 0;
        for (int k = i; k <= j; k++) {
            if (k >= freq.length)
                continue;
            s += freq[k];
        }
        return s;
    }

    public static Node createBST(int left, int right) {
        if (right == left) {
            return null;
        }
        if (right == left + 1) {
            //if (integers.get(rootNum[left][right])==-1) {
            for (int i = 0; i < integers.size(); i++) {
                if (count.get(i) == costs[left][right]) {
                    System.out.println("count and cost " + costs[left][right]);
                    return new Node(integers.get(i));
                }
            }
            return null;
        }
        Node returnNode = new Node();
        returnNode.setInteger(integers.get(rootNum[left][right]));
        System.out.println("root is: " + integers.get(rootNum[left][right]));
        System.out.println("r[" + left + "]" + "[" + rootNum[left][right] + "]");
        returnNode.setLeft(createBST(left, rootNum[left][right]));
        System.out.println("r[" + (rootNum[left][right] + 1) + "]" + "[" + right + "]");
        returnNode.setRight(createBST((rootNum[left][right] + 1), right));
        System.out.println(returnNode.toString());
        return returnNode;

    }

    static void openSubnodes(int row, JTree jt) {
        TreePath tp = jt.getPathForRow(row);
        jt.expandRow(row);
        if (tp.isDescendant(jt.getPathForRow(row + 1)))
            openSubnodes(row + 1, jt);
    }

    static DefaultMutableTreeNode translate2SwingTree(Node ast) {
        DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode("" + ast.getInteger());
        if (ast.getRight() != null)
            dmtn.add(translate2SwingTree(ast.getRight()));
        if (ast.getLeft() != null)
            dmtn.add(translate2SwingTree(ast.getLeft()));
        return dmtn;
    }

    @Override
    public void paintComponent(Graphics g) {
        // Draw Tree Here
        g.drawOval(5, 5, 25, 25);
    }
}