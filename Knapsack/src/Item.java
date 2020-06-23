public class Item {


    int index;
    int size;
    int value;

    public Item(int index, int size, int value) {
        this.index = index;
        this.size = size;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "Item{" +
                "index=" + index +
                ", size=" + size +
                ", value=" + value +
                '}';
    }
}
