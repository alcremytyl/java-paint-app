package paint_app.layer;


public class LayerTree {
    TreeNode head;

    public void insert(Layer layer) {

    }

    // possibly make name/id-based overloads
    public void remove(Layer layer) {

    }

    private class TreeNode {
        Layer layer;
        TreeNode previous, next;

        TreeNode(Layer layer) {
            this.layer = layer;
            this.previous = this.next = null;
        }
    }
}

