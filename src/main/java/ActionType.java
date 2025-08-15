// Enum đại diện cho loại hành động có thể được thực hiện trên sách: mượn và trả
public enum ActionType {
    BORROW,
    RETURN;

    public boolean isBorrowing() {
        return this == BORROW;
    }
}