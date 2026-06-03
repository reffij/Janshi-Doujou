package engine.datastructures;

public enum HAlign {
    LEFT {
        @Override
        public int computeLeft(int x, int containerWidth, int contentWidth) {
            return x;
        }
    },
    CENTER {
        @Override
        public int computeLeft(int x, int containerWidth, int contentWidth) {
            return x + ((containerWidth - contentWidth) / 2);
        }
    },
    RIGHT {
        @Override
        public int computeLeft(int x, int containerWidth, int contentWidth) {
            return x + (containerWidth - contentWidth);
        }
    };

    public abstract int computeLeft(int x, int containerWidth, int contentWidth);
}

