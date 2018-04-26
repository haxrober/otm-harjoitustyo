

package chaosgame.domain;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@DatabaseTable(tableName = "settings")
public class Settings {
    @DatabaseField(id=true)
    private String key;
    @DatabaseField
    private int width;
    @DatabaseField
    private int height;
    @DatabaseField
    private List<Node> anchors;
    @DatabaseField
    private Node prev;
    @DatabaseField
    private double ratio;
    @DatabaseField
    private double grainSize;
    @DatabaseField
    private boolean repeatRule;
    private Random rand;
    
    public Settings() {
        
    }
    
    public Settings(double rat) {
        this.key = "default";
        this.width = 800;
        this.height = 800;
        this.anchors = new ArrayList<>();
        this.prev = new Node(0, 0, Nodetype.EMPTY);
        this.ratio = rat;
        this.repeatRule = false;
        this.rand = new Random();
    }
    
    public void addAnchor(int x, int y) {           //can create duplicate anchors
        anchors.add(new Node(x, y, Nodetype.ANCHOR));
    }
    
    public void addAnchor(Node anchor) {
        if (!anchors.contains(anchor) && anchor.getType() == Nodetype.ANCHOR) {
            anchors.add(anchor);
            prev = anchor;
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public double getRatio() {
        return this.ratio;
    }
    
    public void setRatio(double rat) {
        this.ratio = rat;
    }

    public double getGrainSize() {
        return grainSize;
    }

    public void setGrainSize(double grainSize) {
        this.grainSize = grainSize;
    }

    public boolean getRepeatRule() {
        return repeatRule;
    }

    public void toggleRepeatRule() {
        repeatRule = !repeatRule;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
    
    public void removeAnchors() {
        anchors = new ArrayList<>();
        prev = new Node(0, 0, Nodetype.EMPTY);
    }
    
    public Node getFirstAnchor() {
        if (anchors.isEmpty()) {
            return new Node(0, 0, Nodetype.EMPTY);
        }
        return anchors.get(0);
    }
    
    public List<Node> getAnchors() {
        return this.anchors;
    }
    
    public Node getRandomAnchor() {
        if (anchors.isEmpty()) {
            return new Node(0, 0, Nodetype.EMPTY);
        }
        
        if (repeatRule && anchors.size() > 1) {
            int i = anchors.indexOf(prev);
            Node temp = anchors.remove(i);
            anchors.add(temp);
            prev = anchors.get(rand.nextInt(anchors.size() - 1));
            return prev;
        }
        prev = anchors.get(rand.nextInt(anchors.size()));
        return prev;
    }
}
