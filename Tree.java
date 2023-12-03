import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

public class Tree {

private class Node {
  char ch;
  int count;
  Node left;
  Node right;

  public Node(char ch, int count){
    this.ch = ch;
    this.count = count;
    this.left = null;
    this.right = null;
  }
}

private class Path {
  List<Character> ch = new ArrayList<>();
  int count;

  public Path(){
    this.count = 0;
  }

  public Path(Path path) {
    for (int i = 0; i < path.ch.size(); i++) {
        Character c = path.ch.get(i);
        this.ch.add(c);
        this.count++;
    }        
  }
}

private Node root;
//private Path Path;
//private List<Path> paths;

public void addNode(char ch, int count) {
  Node newNode = new Node(ch, count);
  if(root == null) {
    root = newNode;
  } else {
    Node current = root;
    Queue<Node> queue = new LinkedList<Node>();
    queue.offer(current);

    while(!queue.isEmpty()){
      current = queue.poll();
      if(current.left == null){
        current.left = newNode;
        break;
      }else{
        queue.offer(current.left);
      }
      if(current.right == null){
        current.right = newNode;
        break;
      }else{
        queue.offer(current.right);
      }
    }
  }
}

public List<Path> buildPath() {
  List<Path> paths = new ArrayList<>();
  buildPath(root, new Path(), paths);
  return paths;
}

public void buildPath(Node node, Path currentPath, List<Path> paths) {
  if (node == null) {
    return;
  }

  currentPath.ch.add(node.ch);
  currentPath.count++;

  if(node.left == null && node.right == null){
    paths.add(currentPath);
    return;
  }

  buildPath(node.left, new Path(currentPath), paths);
  buildPath(node.right, new Path(currentPath), paths);
}

public List<Node> getTopDeepNodes() {
  List<Node> deepNodes = new ArrayList<Node>();
  Stack<Node> stack = new Stack<Node>();
  Node current = root;

  while(current != null || stack.size() > 0){
    while(current != null){
        deepNodes.add(current);
        stack.push(current);
        current = current.left;
    }
    current = stack.pop();
    current = current.right;
  }
  return deepNodes;
}

public void printPaths(List<Path> paths) {
    for (Path path: paths) {
        for (Character ch: path.ch) {
            System.out.print(ch + " ");
        }
        System.out.println();
    }
}

public void printDeepNodes(List<Node> nodes) {
    for (Node node: nodes) {
        System.out.println(node.ch);
    }
}


public static void main(String[] args) {
  Tree tree = new Tree();
  tree.addNode('a', 1);
  tree.addNode('b', 2);
  tree.addNode('c', 3);
  tree.addNode('d', 4);
  tree.addNode('e', 5);
  List<Path> paths = tree.buildPath();
  List<Node> deepNodes = tree.getTopDeepNodes();

  System.out.println("Printing Paths:");
  tree.printPaths(paths);
  
  System.out.println("\nPrinting Deep Nodes:");
  tree.printDeepNodes(deepNodes);
}
}

