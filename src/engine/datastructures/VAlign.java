package engine.datastructures;

public enum VAlign {
    TOP {
        @Override
        public int computeTop(int y, int containerHeight, int contentHeight) {
            return y;
        }
    },
    CENTER {
        @Override
        public int computeTop(int y, int containerHeight, int contentHeight) {
            return y + ((containerHeight - contentHeight) / 2);
        }
    },
    BOTTOM {
        @Override
        public int computeTop(int y, int containerHeight, int contentHeight) {
            return y + (containerHeight - contentHeight);
        }
    };

    public abstract int computeTop(int y, int containerHeight, int contentHeight);
}